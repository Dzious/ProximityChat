package fr.dzious.bukkit.proximitychat.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.chat.ChatChannel;
import fr.dzious.bukkit.proximitychat.utils.Logger;
import fr.dzious.bukkit.proximitychat.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;

public class ChatCommand implements CommandExecutor, TabCompleter {

    private final String commandName = "Chat";
    private Map<String, List<String>> tabComplete = new HashMap<>();

    public ChatCommand() {
        tabComplete.put(commandName.toLowerCase(), Arrays.asList("join"));
        tabComplete.put(commandName.toLowerCase() + ".join", ProximityChat.INSTANCE.getChatManager().getChannelsNames());
    }

    public void reload() {
        tabComplete.replace(commandName.toLowerCase() + ".join", ProximityChat.INSTANCE.getChatManager().getChannelsNames());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Logger.instance.warning(sender.getClass().toString() + " cannot use chat command.");
            return (true);
        }

        Logger.instance.debugConsole("Label : " + label);
        Logger.instance.debugConsole("Contains : " + ProximityChat.INSTANCE.getChatManager().getChannelsNames().contains(label));


        if (ProximityChat.INSTANCE.getChatManager().getChannels().containsKey(label)) {

            if (args.length > 0) {
                Bukkit.getScheduler().runTaskAsynchronously(ProximityChat.INSTANCE, new Runnable(){
                    @Override
                    public void run() {                    
                        Player senderPlayer = (Player)sender;

                        ChatChannel senderChannel = ProximityChat.INSTANCE.getChatManager().getPlayerChannel(senderPlayer.getUniqueId());
                        ChatChannel channel = ProximityChat.INSTANCE.getChatManager().getChannels().get(label);
                        
                        
                        Logger.instance.debugConsole("Channel : " + channel.getName());

                        if (!ProximityChat.INSTANCE.getChatManager().joinChannel(senderPlayer, label, true))
                            return;

                        String message =  PlaceholderAPI.setPlaceholders(senderPlayer, channel.getPrefix()); 
                        message = message + Utils.concatCommand("", args, " ");

                        ProximityChat.INSTANCE.getChatManager().joinChannel(senderPlayer, senderChannel.getName(), true);

                        for (Player p : ProximityChat.INSTANCE.getServer().getOnlinePlayers()) {
                            if (p.hasPermission("roleplayengine.chat.channel.*") ||
                                p.hasPermission("roleplayengine.chat.channel." + channel.getName())) {
                                if (channel.getRange() < 0)
                                    p.sendMessage(message);
                                else if (senderPlayer.getLocation().distance(p.getLocation()) <= channel.getRange())
                                    p.sendMessage(message);
                            }
                        }
                    }
                });

            } else
                ProximityChat.INSTANCE.getChatManager().joinChannel((Player)sender, label, false);
                
            return (true);
        }

        if (args.length < 1)
            return (false);

        switch (args[0]) {
            case "join":
                if (args[1] == null)
                    sender.sendMessage(ChatColor.RED + "Channel name is missing.");
                else
                    ProximityChat.INSTANCE.getChatManager().joinChannel((Player)sender, args[1], false);
                return (true);
            default :
                return (false);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String concatedCommand = Utils.concatCommand(commandName, args, ".");

        if (tabComplete.containsKey(concatedCommand))
            return (tabComplete.get(concatedCommand));
        else 
            return (null);
    }
}
