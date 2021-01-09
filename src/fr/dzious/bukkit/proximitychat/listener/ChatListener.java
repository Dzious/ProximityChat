package fr.dzious.bukkit.proximitychat.listener;

import com.earth2me.essentials.Essentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.dzious.bukkit.proximitychat.ProximityChat;
import fr.dzious.bukkit.proximitychat.chat.ChatChannel;
import fr.dzious.bukkit.proximitychat.utils.Logger;
import me.clip.placeholderapi.PlaceholderAPI;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ProximityChat.INSTANCE.getChatManager().onPlayerJoin(e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        ProximityChat.INSTANCE.getChatManager().onPlayerQuit(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerSendMessage(AsyncPlayerChatEvent e) {
        Player sender = e.getPlayer();
        ChatChannel channel = ProximityChat.INSTANCE.getChatManager().getPlayerChannel(sender.getUniqueId());
        
        if (isMuted(sender))
            return;

        Logger.instance.debugConsole("Channel : " + channel.getName());

        if (!sender.hasPermission("roleplayengine.chat.channel.*") && !sender.hasPermission("roleplayengine.chat.channel." + channel.getName())) {
            Logger.instance.sendMessage(sender, ChatColor.RED + "You are not allowed to talk in this channel.");
            e.setCancelled(true);
            return;
        }

        if (!channel.getWorlds().isEmpty() && !channel.getWorlds().contains(sender.getLocation().getWorld().getName())) {
            Logger.instance.sendMessage(sender, ChatColor.RED + "The channel " + channel.getName() + " is disable in this world.");
            e.setCancelled(true);
            return;
        }

        Logger.instance.debugConsole(e.getFormat());

        e.setFormat(PlaceholderAPI.setPlaceholders(sender, channel.getPrefix()) + "%2$s");
        // e.setMessage( + e.getMessage()); 
        e.getRecipients().clear();

        for (Player p : ProximityChat.INSTANCE.getServer().getOnlinePlayers()) {
            if (p.hasPermission("roleplayengine.chat.channel.*") ||
                p.hasPermission("roleplayengine.chat.channel." + channel.getName())) {
                if (channel.getRange() < 0)
                    e.getRecipients().add(p);
                else if (sender.getLocation().distance(p.getLocation()) <= channel.getRange())
                    e.getRecipients().add(p);
            }
        }
    }

    private boolean isMuted(Player player) {
        Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        if (essentials != null) {
			try {
				if (essentials.getUser(player).isMuted()) {
                    player.sendMessage(ChatColor.RED + "You are currently mute.");
					return (true);
				}
			} catch (Exception e) {
			}
			return (false);
		}
		return false;
	}

}
