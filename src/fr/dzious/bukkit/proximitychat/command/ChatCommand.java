package fr.dzious.bukkit.proximitychat.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.utils.Logger;
import fr.dzious.bukkit.proximitychat.utils.Utils;

public class ChatCommand implements CommandExecutor, TabCompleter {

    private final String commandName = "Chat";
    private Map<String, List<String>> tabComplete = new HashMap<>();

    public ChatCommand() {
        tabComplete.put(commandName.toLowerCase(), Arrays.asList("join"));
        tabComplete.put(commandName.toLowerCase() + ".join", ProximityChat.INSTANCE.getChatManager().getChannels());
    }

    public void reload() {
        tabComplete.replace(commandName.toLowerCase() + ".join", ProximityChat.INSTANCE.getChatManager().getChannels());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Logger.instance.warning(sender.getClass().toString() + " cannot use chat command.");
            return (true);
        }

        if (ProximityChat.INSTANCE.getChatManager().getChannels().contains(label)) {
            ProximityChat.INSTANCE.getChatManager().joinChannel((Player)sender, label);
            return (true);
        }

        if (args.length < 1)
            return (false);

        switch (args[0]) {
            case "join":
                if (args[1] == null)
                    sender.sendMessage(ChatColor.RED + "Channel name is missing.");
                else
                    ProximityChat.INSTANCE.getChatManager().joinChannel((Player)sender, args[1]);
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
