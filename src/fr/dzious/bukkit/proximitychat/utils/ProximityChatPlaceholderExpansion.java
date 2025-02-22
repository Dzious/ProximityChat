package fr.dzious.bukkit.proximitychat.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.chat.ChatChannel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * This class will automatically register as a placeholder expansion 
 * when a jar including this class is added to the directory 
 * {@code /plugins/PlaceholderAPI/expansions} on your server.
 * <br>
 * <br>If you create such a class inside your own plugin, you have to
 * register it manually in your plugins {@code onEnable()} by using 
 * {@code new YourExpansionClass().register();}
 */
public class ProximityChatPlaceholderExpansion extends PlaceholderExpansion {

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "Dzious";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "proximitychat";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return ProximityChat.INSTANCE.getDescription().getVersion();
    }
  
    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return Possibly-null String of the requested identifier.
     */
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        if (player == null)
            return ("");

        ChatChannel channel =  ProximityChat.INSTANCE.getChatManager().getPlayerChannel(player.getUniqueId());

        String placeholder = "";

        switch (identifier) {
            case "channel_name":
                return (channel.getName());
            case "channel_range":
                if (channel.getRange() < 0)
                    return ("infinite");
                else
                    return (Integer.toString(channel.getRange()));
            case "channel_worlds":
                if (channel.getWorlds().isEmpty()) {
                    for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
                        placeholder = placeholder + Bukkit.getWorlds().get(i).getName();
                        if (i < (channel.getWorlds().size() - 1))
                            placeholder = placeholder + ", ";
                    }
                } else {
                    for (int i = 0; i < channel.getWorlds().size(); i++) {
                        placeholder = placeholder + channel.getWorlds().get(i);
                        if (i < (channel.getWorlds().size() - 1))
                        placeholder = placeholder + ", ";
                    }
                }
                return (placeholder);
            // case "channel_worlds_formated":
            //     placeholder = "[";
            //     for (int i = 0; i < channel.getWorlds().size(); i++) {
            //         placeholder = placeholder + channel.getWorlds().get(i);
            //         if (i < (channel.getWorlds().size() - 1))
            //             placeholder = placeholder + ", ";
            //     }
            //     placeholder = placeholder + "]";
            //     return (placeholder);
            default:
                return (null);
        }
    }
}
