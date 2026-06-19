package me.miskynet.customGamemode.utils.customConfig;

import me.miskynet.customGamemode.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerSettings {

    /**
     * Get the file path for the player settings file
     * @param uuid The uuid of the player that settings should be got
     * @return Filepath of the settings path
     * */
    private static String getFilePath(UUID uuid) {
        return "playerData/" + uuid.toString() + ".yml";
    }

    /**
     * Set up a new player settings file
     * @param player Player the setting is for
     * */
    public static void setup(Player player) {

        File file = new File(Main.getInstance().getDataFolder(), getFilePath(player.getUniqueId()));

        if (!file.exists()) {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                Main.getInstance().saveResource(getFilePath(player.getUniqueId()), false);
            }catch (IllegalArgumentException e) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Bukkit.getLogger().warning("Couldn't create settings file for: " + player.getName() + " - " + player.getUniqueId());
                    ex.printStackTrace();
                    return;
                }
            }
        }

        CustomConfig.files.put(getFilePath(player.getUniqueId()), file);

        CustomConfig.configs.put(getFilePath(player.getUniqueId()), YamlConfiguration.loadConfiguration(file));

        set(player, "playerUniqueData.uuid", player.getUniqueId().toString());
        save(player);
        set(player, "playerUniqueData.lastSeenAs", player.getName().toString());
        save(player);
    }

    /**
     * Get an instance of a player settings file
     * @return Config itself
     * @param player Player the setting is for
     * */
    public static FileConfiguration get(Player player) {
        return CustomConfig.get(getFilePath(player.getUniqueId()));
    }

    /**
     * Get an object out of a player settings file
     * @param player Player the setting is for
     * @param key setting name that should be searched for
     * @return {@link Object}
     * */
    public static Object get(Player player, String key) {
        return CustomConfig.get(getFilePath(player.getUniqueId()), key);
    }

    /**
     * Save data into a player setting
     * @param player Player the setting is for
     * */
    public static void save(Player player) {
        CustomConfig.save(getFilePath(player.getUniqueId()));
    }

    /**
     * Set a setting for a player
     * @param player Player the setting is for
     * @param key key of the setting
     * @param value value of the setting
     * */
    public static void set(Player player, String key, Object value) {
        CustomConfig.set(getFilePath(player.getUniqueId()), key, value);
        save(player);
    }

    /**
     * Reload a player settings file
     * @param player Player the setting is for
     * */
    public static void reload(Player player) {
        CustomConfig.reload(getFilePath(player.getUniqueId()));
    }

    /**
     * Checks if the settings file of a player exists
     * @param player Player the check is for
     * @return True if the file exists. False if the file does not exist
     * */
    public static boolean checkForExistence(Player player) {
        return CustomConfig.checkForExistence(getFilePath(player.getUniqueId()));
    }

}
