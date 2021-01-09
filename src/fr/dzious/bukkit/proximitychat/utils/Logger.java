package fr.dzious.bukkit.proximitychat.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.dzious.bukkit.proximitychat.ProximityChat;

public class Logger {

    public final static Logger instance = new Logger(ProximityChat.INSTANCE);

    private final ProximityChat plugin;
    private boolean debugEnable = true; //ToDo Change it 
    private String debugPlayer = "Dzious";

    private Logger(ProximityChat plugin) {
        this.plugin = plugin;
    }

    public void init() {
        if (ProximityChat.configManager.contains("debug.enable"))
            debugEnable = ProximityChat.configManager.getBoolean("debug.enable");
        if (ProximityChat.configManager.contains("debug.player"))
            debugPlayer = ProximityChat.configManager.getString("debug.player");
    }


    public void info(String msg) {
        System.out.println("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.RESET + "] [" + ChatColor.GREEN + "Info" + ChatColor.RESET + "] : "  + msg);
    }

    public void warning(String msg) {
        System.out.println("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.RESET + "] [" + ChatColor.YELLOW + "Warning" + ChatColor.RESET + "] : "  + msg);
    }

    public void error(String msg) {
        System.out.println("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.RESET + "] [" + ChatColor.RED + "Error" + ChatColor.RESET + "] : "  + msg);
    }

    public void sendMessage(Player p, String msg) {
        p.sendMessage("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.WHITE + "] : "  + msg);
    }

    public void debugConsole(String msg) {
        if (isDebugEnable() == true)
            System.out.println("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.RESET + "] [" + ChatColor.BLUE + "Debug" + ChatColor.RESET + "] : "  + msg);
    }

    public void debugPlayer(String msg) {
        if (isDebugEnable() == true && getDebugPlayer() != null)
            for (Player p : plugin.getServer().getOnlinePlayers())
                if (p.getName() == getDebugPlayer())
                    p.sendMessage("[" + ChatColor.YELLOW + plugin.getName() + ChatColor.WHITE + "]" + ChatColor.BLUE + "[Debug]" + ChatColor.WHITE + " : "  + msg);
    }

    public void exception (Exception e) {
        if (isDebugEnable() == true)
            e.printStackTrace();
    }

    public boolean isDebugEnable() {
        return (debugEnable);
    }

    private String getDebugPlayer() {
        return (debugPlayer);
    }
}
