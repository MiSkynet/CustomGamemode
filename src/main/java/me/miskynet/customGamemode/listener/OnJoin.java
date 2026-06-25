package me.miskynet.customGamemode.listener;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {

    /**
     * The general player join listener that handles stuff like:
     * <ul>
     *     <li>Setup default player settings
     *     <li>Create the scoreboard for the player
     * </ul>
     * */
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();

        if (!PlayerData.checkForExistence(PlayerData.FileType.SETTINGS, playerUUID)) {
            PlayerData.setup(PlayerData.FileType.SETTINGS, playerUUID);
            Utils.setupDefaultPlayerSettings(event.getPlayer());
        }

        if (!PlayerData.checkForExistence(PlayerData.FileType.BALANCE, playerUUID)) {
            PlayerData.setup(PlayerData.FileType.BALANCE, playerUUID);
        }

        if (Main.economyManager.getBalance(player) == null) {
            Main.economyManager.setBalance(player, 0.0);
        }

        Main.scoreboardManager.createScoreboard(event.getPlayer());
    }

}
