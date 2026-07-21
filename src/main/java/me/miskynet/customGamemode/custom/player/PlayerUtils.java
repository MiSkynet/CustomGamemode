package me.miskynet.customGamemode.custom.player;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import org.bukkit.entity.Player;

import me.miskynet.customGamemode.custom.config.PlayerData.FileType;

import java.util.UUID;

public class PlayerUtils {

    private static final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    /**
     * Checks if a {@link Player} has a {@link FileType} file, and if not, sets it up
     *
     * @param fileType The {@link FileType} to check for
     * @param player The {@link Player} to check for
     * */
    public static void setupFileForPlayer(FileType fileType, Player player) {
        if (!PlayerData.checkForExistence(fileType, player.getUniqueId())) {
            PlayerData.setup(fileType, player.getUniqueId());
            loadDefaultsForFile(fileType, player);
        }
    }

    /**
     * Loads the default configuration for a {@link FileType}
     *
     * @param fileType The {@link FileType} to load defaults for
     * @param player The {@link Player} to load defaults for
     * */
    public static void loadDefaultsForFile(FileType fileType, Player player) {

        UUID playerUUID = player.getUniqueId();

        if (fileType == FileType.SETTINGS) {
            PlayerData.set(FileType.SETTINGS, playerUUID, "settings.scoreboardStatus", true);

            return;
        }

        if (fileType == FileType.BALANCE) {
            PlayerData.setup(FileType.BALANCE, playerUUID);

            if (economyManager.getBalance(player) == null) {
                economyManager.setBalance(player, 0.0);
            }

            return;
        }

        if (fileType == FileType.INDEX) {
            PlayerData.setup(FileType.INDEX, playerUUID);
            return;
        }

        if (fileType == FileType.STATS) {
            PlayerData.setup(FileType.STATS, playerUUID);

            PlayerData.set(FileType.STATS, playerUUID, "currentLevel", 0);
            PlayerData.set(FileType.STATS, playerUUID, "currentXP", 0);
            return;
        }
    }
}
