package me.miskynet.customGamemode.custom.economy;

import me.miskynet.customGamemode.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EconomyListener implements Listener {

    /**
     * Set the player balance to 0.0 if he doesn't have a balance
     * */
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if (Main.economyManager.getBalance(event.getPlayer()) == null) {
            Main.economyManager.setBalance(event.getPlayer(), 0.0);
        }

    }

}
