package pl.urimagdui.warnings.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.urimagdui.warnings.Managers.UserManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (UserManager.getUser(player) != null) return;
        UserManager.createUser(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (UserManager.getUser(player) == null) return;
        UserManager.saveUser(player);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (UserManager.getUser(player) == null) return;
        UserManager.saveUser(player);
    }
}
