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

        if (config.contains("chat.enable") && config.getBoolean("chat.enable")) {
            ChatCommand chatCmd = new ChatCommand();
            plugin.getCommand("Chat").setExecutor(chatCmd);
            plugin.getCommand("Chat").setTabCompleter(chatCmd);

            for (String channel : plugin.getChatManager().getChannels()) {
                if (config.contains("chat." + channel + ".enable_command") && config.getBoolean("chat." + channel + ".enable_command")) {
                    plugin.getCommand("Chat").getAliases().add(channel.toLowerCase());
                }
            }
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
    
                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
    
                commandMap.register("Chat", plugin.getCommand("Chat"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

    public void reload() {
        ((ChatCommand)plugin.getCommand("Chat").getTabCompleter()).reload();
    }
}
