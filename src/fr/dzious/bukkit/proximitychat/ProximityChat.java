package fr.dzious.bukkit.proximitychat;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dzious.bukkit.proximitychat.chat.ChatManager;
import fr.dzious.bukkit.proximitychat.command.CommandManager;
import fr.dzious.bukkit.proximitychat.listener.ListenerManager;
import fr.dzious.bukkit.proximitychat.utils.Logger;
import fr.dzious.bukkit.proximitychat.utils.ProximityChatPlaceholderExpansion;
import fr.dzious.bukkit.proximitychat.utils.Utils;

public class ProximityChat extends JavaPlugin {
    
    public static ProximityChat INSTANCE;
    public static YamlConfiguration configManager;
    public static YamlConfiguration languageManager;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private ChatManager chatManager;

    @Override
    public void onLoad() {
        return;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        Logger.instance.info(this.getName() + " starting...");
        try {
            createConfigManager();
            Logger.instance.init();

            chatManager = new ChatManager();

            commandManager = new CommandManager(INSTANCE);
            commandManager.onEnable();

            listenerManager = new ListenerManager(INSTANCE);
            listenerManager.onEnable();

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                new ProximityChatPlaceholderExpansion().register();
            }

            Logger.instance.info(this.getName() + " started !");
        } catch (Exception e) {
            Logger.instance.error("Something wrong happen while starting the plugin.");
            Logger.instance.exception(e);
            disablePlugin();
        }
        return;
    }

    public void reload() {
        try {
            reloadConfigManager();
            reloadLanguageManager();
            chatManager.reload();
            commandManager.reload();
        } catch (Exception e) {
            Logger.instance.warning("Unable to Load config or language file. Reload failed.");
            Logger.instance.exception(e);
        }
    }


    @Override
    public void onDisable() {
        Logger.instance.info(this.getName() + " stopping...");
        Logger.instance.info(this.getName() + " stopped !");
        return;
    }

    public void disablePlugin() {
        Logger.instance.error("This is a fatal error, disabling " + this.getName());
        setEnabled(false);
    }

    public YamlConfiguration getConfigManager() {
        return (configManager);
    }

    public ChatManager getChatManager() {
        return (chatManager);
    }

    private void createConfigManager() throws Exception {
        configManager = new YamlConfiguration();
        File configFile = new File(getDataFolder() + "/config.yml");

        Logger.instance.debugConsole("exists : " + configFile.exists());
        Logger.instance.debugConsole("Path : " + getDataFolder() + "/config.yml");

    
        if (!configFile.exists()) {
            if (!getDataFolder().exists())
                getDataFolder().mkdirs();
            Utils.copy("/config.yml", getDataFolder() + "/config.yml");
        }
        configManager.load(getDataFolder() + "/config.yml");
    }

    private void reloadConfigManager() throws Exception {
        File configFile = new File(getDataFolder() + "/config.yml");
        if (configFile.exists())
            configManager.load(getDataFolder() + "/config.yml");
    }

    private void createLanguageManager() throws Exception {
        languageManager = new YamlConfiguration();
        String languageFileString = "en_us.yml"; 
        
        if (configManager.contains("language"))
            languageFileString = configManager.getString("language");

        File languageFile  = new File(getDataFolder() + "/" + languageFileString);

        if (!languageFile.exists())
            // Utils.ExportResource("Languages/" + languageFileString);
            Utils.copy("/Languages/" + languageFileString, getDataFolder() + "/" + languageFileString);
        
        languageManager.load(getDataFolder() + "/" + languageFileString);
    }

    private void reloadLanguageManager() throws Exception {
        String languageFileString = "en_us.yml"; 
        
        if (configManager.contains("language"))
            languageFileString = configManager.getString("language");

        File languageFile  = new File(getDataFolder() + "/" + languageFileString);
        if (languageFile.exists())
            languageManager.load(getDataFolder() + "/" + languageFileString);
    }
}