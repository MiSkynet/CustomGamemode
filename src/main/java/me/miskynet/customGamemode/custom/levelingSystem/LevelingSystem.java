package me.miskynet.customGamemode.custom.levelingSystem;

import me.miskynet.customGamemode.custom.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LevelingSystem {

    /**
     * Gets the current level of a {@link Player}
     *
     * @param player {@link Player}
     * @return The current level of the {@link Player} as an {@link Integer}
     * */
    public int getPlayerLevel(Player player) {
        return PlayerData.get(PlayerData.FileType.STATS, player.getUniqueId()).getInt("currentLevel");
    }

    /**
     * Sets the current level of a {@link Player}
     *
     * @param player {@link Player}
     * @param level The level to set the {@link Player} to as an {@link Integer}
     * @return The current instance of the {@link LevelingSystem}
     * */
    public LevelingSystem setPlayerLevel(Player player, int level) {
        PlayerData.get(PlayerData.FileType.STATS, player.getUniqueId()).set("currentLevel", level);
        PlayerData.save(PlayerData.FileType.STATS, player.getUniqueId());
        return this;
    }

    /**
     * Adds a level to a {@link Player}
     *
     * @param player {@link Player}
     * @param xp The amount of levels to add to the {@link Player} as an {@link Integer}
     * @return The current instance of the {@link LevelingSystem}
     * */
    public LevelingSystem addPlayerLevel(Player player, int xp) {
        int currentLevel = getPlayerLevel(player);
        setPlayerLevel(player, currentLevel + xp);
        return this;
    }

    /**
     * Gets the current XP of a {@link Player}
     *
     * @param player {@link Player}
     * @return The current XP of the {@link Player} as an {@link Integer}
     * */
    public int getPlayerXP(Player player) {
        return PlayerData.get(PlayerData.FileType.STATS, player.getUniqueId()).getInt("currentXP");
    }

    /**
     * Sets the current XP of a {@link Player}
     *
     * @param player {@link Player}
     * @param xp The XP to set the {@link Player} to as an {@link Integer}
     * @return The current instance of the {@link LevelingSystem}
     * */
    public LevelingSystem setPlayerXP(Player player, int xp) {
        PlayerData.get(PlayerData.FileType.STATS, player.getUniqueId()).set("currentXP", xp);
        PlayerData.save(PlayerData.FileType.STATS, player.getUniqueId());
        return this;
    }

    /**
     * Adds XP to a {@link Player} and checks if the {@link Player} can level up. If the {@link Player} can level up, it will add a level to the {@link Player} and set the XP to the remaining XP after leveling up.
     *
     * @param player {@link Player}
     * @param xp The amount of XP to add to the {@link Player} as an {@link Integer}
     * @returns The current instance of the {@link LevelingSystem}
     * */
    public LevelingSystem addPlayerXP(Player player, int xp) {
        int currentLevel = getPlayerXP(player);
        setPlayerXP(player, currentLevel + xp);
        // check if the player can level up
        if (checkLevelUp(player)) {
            setPlayerXP(player, getPlayerXP(player) - getRequiredXPToLevelUp(getPlayerLevel(player)));
            addPlayerLevel(player, 1);
            Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(player, getPlayerLevel(player)));
        }
        return this;
    }

    /**
     * Check if a {@link Player} can level up
     *
     * @returns True if the {@link Player} can level up, false otherwise
     * */
    private boolean checkLevelUp(Player player) {
        int currentXP = getPlayerXP(player);
        if (currentXP >= getRequiredXPToLevelUp(this.getPlayerLevel(player))) {
            return true;
        }
        return false;
    }

    /**
     * Get the amount of XP required to level up for a specific level
     *
     * @param level The level to get the amount of XP required to level up for as an {@link Integer}
     * @returns The amount of XP as an {@link Integer}
     * */
    public int getRequiredXPToLevelUp(int level) {
        return (int) (10000 + 7 * (Math.pow(level + 1, 2)));
    }
}
