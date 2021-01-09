package fr.dzious.bukkit.proximitychat.listener;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.utils.Logger;

public class ListenerManager {
    private final ProximityChat plugin;

    public ListenerManager(ProximityChat plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Logger.instance.info("ListenerManager OnEnable");

        // plugin.getServer().getPluginManager().registerEvents(new TemplateListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChatListener(), plugin);
    }    
}
