package fr.dzious.bukkit.proximitychat.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.dzious.bukkit.proximitychat.utils.Logger;

public class ChatChannel {
    private String name = null;
    private String prefix = null;
    private int range = -1;
    private List<String> worlds = new ArrayList<>(); 

    public ChatChannel (String name, YamlConfiguration config) {
        this.name = name;
        
        if (config.contains("chat." + name + ".prefix"))
            this.prefix = config.getString("chat." + name + ".prefix");
        else if (config.contains("chat.default.prefix"))
            this.prefix = config.getString("chat.default.prefix");
        else
            this.prefix = "[%proximitychat_channel_name%] <%player_name%> ";

        if (config.contains("chat." + name + ".range"))        
            this.range = config.getInt("chat." + name + ".range");

        if (config.contains("chat." + name + ".worlds")) {
            List<?> yamlList = config.getList("chat.channels");
        
            if (yamlList == null) {
                Logger.instance.error("\"chat:" + name + ":worlds\" does not exists in config or is wrongly formated");
                return;
            }

            for (int i = 0; i < yamlList.size(); i++) {
                if (yamlList.get(i) instanceof String && !worlds.contains((String)yamlList.get(i))) {
                    Logger.instance.info((String)yamlList.get(i) + " added for channel " + name + ".");
                    worlds.add((String)yamlList.get(i));
                } else {
                    Logger.instance.error("\"" + yamlList.get(i).toString() + "\"" + " is not a valid world name or defined twice.");
                }
            }
        }
    }

    public String getName() {
        return (name);
    }

    public String getPrefix() {
        return (prefix);
    }

    public int getRange() {
        return (range);
    }

    public List<String> getWorlds() {
        return (worlds);
    }
}
