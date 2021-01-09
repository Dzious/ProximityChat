package fr.dzious.bukkit.proximitychat.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.utils.Logger;
import fr.dzious.bukkit.proximitychat.utils.Utils;

public class ProximityChatCommand implements CommandExecutor, TabCompleter {
    
    private final String commandName = "ProximityChat";
    private Map<String, List<String>> tabComplete = new HashMap<>();
    
    public ProximityChatCommand() {
        tabComplete.put(commandName.toLowerCase(), Arrays.asList("reload"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) && !(sender instanceof Player)) {
            Logger.instance.warning(sender.getClass().toString() + " cannot use chat command.");
            return (true);
        }

        if (args.length < 1)
            return (false);

        switch (args[0]) {
            case "reload":
                ProximityChat.INSTANCE.reload();
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
            return null;
    }
}
