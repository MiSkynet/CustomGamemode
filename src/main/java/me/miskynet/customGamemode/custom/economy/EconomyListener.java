package me.miskynet.customGamemode.custom.economy;

import me.miskynet.customGamemode.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EconomyListener implements Listener {

    /*
    * Set the balance of a player, whose balance is null, to 0
    * */
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if (Main.economyManager.getBalance(event.getPlayer()) == null) {
            Main.economyManager.setBalance(event.getPlayer(), 0);
        }

    }

}
