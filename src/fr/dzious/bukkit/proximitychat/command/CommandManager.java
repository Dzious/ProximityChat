package fr.dzious.bukkit.proximitychat.command;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.utils.Logger;

public class CommandManager {

    private ProximityChat plugin;

    public CommandManager(ProximityChat plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Logger.instance.info("CommandManager OnEnable");

        YamlConfiguration config = ProximityChat.configManager;
        ChatCommand chatCmd = new ChatCommand();

        plugin.getCommand("Chat").setExecutor(chatCmd);
        plugin.getCommand("Chat").setTabCompleter(chatCmd);

        for (String channel : plugin.getChatManager().getChannelsNames()) {
            Logger.instance.debugConsole("Processing " + channel);
            if (config.contains("chat." + channel + ".enable_command") && config.getBoolean("chat." + channel + ".enable_command")) {
                Logger.instance.debugConsole("Requested " + channel + " as a command.");
                plugin.getCommand("Chat").getAliases().add(channel.toLowerCase());
                Logger.instance.debugConsole(channel.toLowerCase() + " added.");
            }
        }
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            ((CommandMap) bukkitCommandMap.get(Bukkit.getServer())).register("Chat", plugin.getCommand("Chat"));
            bukkitCommandMap.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        ((ChatCommand)plugin.getCommand("Chat").getTabCompleter()).reload();
    }
}
