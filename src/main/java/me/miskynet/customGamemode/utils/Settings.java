package me.miskynet.customGamemode.utils;

import me.miskynet.customGamemode.custom.config.PlayerData;
import org.bukkit.entity.Player;

public class Settings {

    /**
     * Sets the default player settings for a specific player
     * @param player The player the settings should be set for to default
     * */
    public static void setupDefaultPlayerSettings(Player player) {
        PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", true);
    }

}
