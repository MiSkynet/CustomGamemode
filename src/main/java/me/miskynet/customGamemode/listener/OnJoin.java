package me.miskynet.customGamemode.listener;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {

        if (!PlayerSettings.checkForExistence(event.getPlayer())) {
            PlayerSettings.setup(event.getPlayer());
            Utils.setupDefaultPlayerSettings(event.getPlayer());
        }

        Main.scoreboardManager.createScoreboard(event.getPlayer());
    }

}
