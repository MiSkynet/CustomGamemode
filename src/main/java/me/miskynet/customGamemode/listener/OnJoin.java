package me.miskynet.customGamemode.listener;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.player.PlayerUtils;
import me.miskynet.customGamemode.custom.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {

    private final ScoreboardManager scoreboardManager = Main.getInstance().getScoreboardManager();

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

        PlayerUtils.setupFileForPlayer(PlayerData.FileType.SETTINGS, player);
        PlayerUtils.setupFileForPlayer(PlayerData.FileType.INDEX, player);
        PlayerUtils.setupFileForPlayer(PlayerData.FileType.STATS, player);
        PlayerUtils.setupFileForPlayer(PlayerData.FileType.BALANCE, player);

        scoreboardManager.createScoreboard(event.getPlayer());
    }

}
