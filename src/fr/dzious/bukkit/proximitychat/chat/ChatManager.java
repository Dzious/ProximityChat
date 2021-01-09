package fr.dzious.bukkit.proximitychat.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.utils.Logger;

public class ChatManager {

    private Map<String, ChatChannel> channels = new HashMap<>();
    private Map<UUID, ChatChannel> players = new HashMap<>();
    private ChatChannel defaultChannel = null;

    public ChatManager() {
        reload();
    }
  
    public void reload() {
        YamlConfiguration config = ProximityChat.configManager;

        if (!config.contains("chat.default.channel") || config.getString("chat.default.channel") == null) {
            Logger.instance.error("Missing default channel in config.");
            return;
        }

        List<?> yamlList = config.getList("chat.channels");
        
        if (yamlList == null) {
            Logger.instance.error("\"channels\" does not exists in config or is wrongly formated");
            return;
        }

        Map<String, ChatChannel> newChannels = new HashMap<>();

        for (int i = 0; i < yamlList.size(); i++) {
            if (!(yamlList.get(i) instanceof String) ) {
                Logger.instance.error(yamlList.get(i).toString() + " is not a valid channel name.");
            } else if (newChannels.containsKey(yamlList.get(i).toString().toLowerCase())) {
                Logger.instance.error(yamlList.get(i).toString() + " is already defined as " + newChannels.get(yamlList.get(i).toString().toLowerCase()).getName());
            } else {
                Logger.instance.debugConsole("Channel " + (String)yamlList.get(i) + " added.");
                newChannels.put(yamlList.get(i).toString().toLowerCase(), new ChatChannel(yamlList.get(i).toString(), config));
            }
        }

        ChatChannel newDefaultChannel = null;
        for (Map.Entry<String, ChatChannel> channel : newChannels.entrySet()) {
            if (channel.getKey().equalsIgnoreCase(config.getString("chat.default.channel"))) {
                Logger.instance.debugConsole("Default channel set to " + channel.getValue().getName() + ".");
                newDefaultChannel = channel.getValue();
                break;
            } else {
                Logger.instance.debugConsole(channel.getValue().getName() + "is not the defined default channel.");
            }
        }
        if (newDefaultChannel == null) {
            Logger.instance.error("Channel " + config.getString("chat.default.channel") + " is not present in config's channel list.");
            return;
        }

        channels = newChannels;
        defaultChannel = newDefaultChannel;

        for (Map.Entry<UUID, ChatChannel> player : players.entrySet()) {
            if (!channels.containsValue(player.getValue()))
                Logger.instance.sendMessage(ProximityChat.INSTANCE.getServer().getPlayer(player.getKey()), 
                    "The channel " + player.getValue().getName() + " has been removed. " + 
                    "You've been moved to " + defaultChannel.getName() + ".");
        }
    }

    public boolean joinChannel(Player p, String channelName, boolean silent)  {
        
        channelName = channelName.toLowerCase();

        if (!channels.containsKey(channelName)) {
            Logger.instance.sendMessage(p, ChatColor.RED + "This channel does not exists.");
            return (false);
        }

        if (!p.hasPermission("roleplayengine.chat.channel.*") && !p.hasPermission("roleplayengine.chat.channel." + channelName)) {
            Logger.instance.sendMessage(p, ChatColor.RED + "You can't access this channel.");
            return (false);
        }   
        
        if (players.get(p.getUniqueId()).getName().equals(channelName)) {
            if (silent == false)
                p.sendMessage(ChatColor.GREEN + "You're already in " + ChatColor.BLUE + channelName + ChatColor.GREEN + ".");
        } else {
            if (silent == false)
                p.sendMessage(ChatColor.GREEN + "You joined the " + ChatColor.BLUE + channelName + ChatColor.GREEN + " channel.");
            players.replace(p.getUniqueId(), channels.get(channelName));

        }
        return (true);
    }

    public List<String> getChannelsNames() {
        List<String> channelNames = new ArrayList<>();
        for (Map.Entry<String, ChatChannel> channel : channels.entrySet())
            channelNames.add(channel.getValue().getName());
        return (channelNames);
    }

    public Map<String, ChatChannel> getChannels() {
        return (channels);
    }

    public ChatChannel getPlayerChannel(UUID uuid) {
        return (players.get(uuid));
    }

    public void onPlayerJoin(Player p) {
        players.put(p.getUniqueId(), defaultChannel);
        Logger.instance.sendMessage(p, "You joined channel " + defaultChannel.getName() + ".");

    }

    public void onPlayerQuit(UUID uuid) {
        players.remove(uuid);
    }

}
