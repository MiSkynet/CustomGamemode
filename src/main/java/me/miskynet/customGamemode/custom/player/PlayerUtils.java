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

        // load default for the SETTINGS file, if the fileType is SETTINGS
        if (fileType == FileType.SETTINGS) {
            PlayerData.set(FileType.SETTINGS, playerUUID, "settings.scoreboardStatus", true);

            return;
        }

        // load default for the BALANCE file, if the fileType is BALANCE
        if (fileType == FileType.BALANCE) {
            PlayerData.setup(FileType.BALANCE, playerUUID);

            if (economyManager.getBalance(player) == null) {
                economyManager.setBalance(player, 0.0);
            }

            return;
        }

        // load default for the INDEX file, if the fileType is INDEX
        if (fileType == FileType.INDEX) {
            PlayerData.setup(FileType.INDEX, playerUUID);
            return;
        }

        // load default for the STATS file, if the fileType is STATS
        if (fileType == FileType.STATS) {
            PlayerData.setup(FileType.STATS, playerUUID);

            PlayerData.set(FileType.STATS, playerUUID, "currentLevel", 0);
            PlayerData.set(FileType.STATS, playerUUID, "currentXP", 0);
            return;
        }
    }
}
