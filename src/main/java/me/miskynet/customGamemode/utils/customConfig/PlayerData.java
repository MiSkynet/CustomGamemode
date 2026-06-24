package me.miskynet.customGamemode.utils.customConfig;

import me.miskynet.customGamemode.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {

    /**
     * Enum for all type of player files
     * */
    public enum PlayerFileType {

        SETTINGS("settings.yml"),
        BALANCE("balance.yml"),
        STATS("stats.yml");

        private final String fileName;

        PlayerFileType(String fileName) {
            this.fileName = fileName;
        }

        public String getPath(UUID uuid) {
            return "playerData/" + uuid.toString() + "/" + this.fileName;
        }
    }

    /**
     * Get the file path for the player settings file
     * @param uuid The uuid of the players file that should be got
     * @return Filepath of the settings path
     * */
    private static String getFilePath(UUID uuid) {
        return "playerData/" + uuid.toString() + ".yml";
    }

    /**
     * Set up a new player settings file
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * */
    public static void setup(PlayerFileType playerFileType, UUID uuid) {
        String fullPath = playerFileType.getPath(uuid);
        File file = new File(Main.getInstance().getDataFolder(), fullPath);

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        CustomConfig.files.put(fullPath, file);
        CustomConfig.configs.put(fullPath, YamlConfiguration.loadConfiguration(file));

        set(playerFileType, uuid, "playerUniqueData.uuid", uuid.toString());

        org.bukkit.entity.Player player = Bukkit.getPlayer(uuid);
        String name = (player != null) ? player.getName() : "Unknown";
        set(playerFileType, uuid, "playerUniqueData.lastSeenAs", name);

        save(playerFileType, uuid);
    }

    /**
     * Get an instance of a player settings file
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * @return The config as {@link FileConfiguration}
     * */
    public static FileConfiguration get(PlayerFileType playerFileType, UUID uuid) {
        return CustomConfig.get(playerFileType.getPath(uuid));
    }

    /**
     * Get an object out of a player settings file
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * @param key setting name that should be searched for
     * @return {@link Object}
     * */
    public static Object get(PlayerFileType playerFileType, UUID uuid, String key) {
        return CustomConfig.get(playerFileType.getPath(uuid), key);
    }

    /**
     * Save data into a player setting
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * */
    public static void save(PlayerFileType playerFileType, UUID uuid) {
        CustomConfig.save(playerFileType.getPath(uuid));
    }

    /**
     * Set a setting for a player
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * @param key key of the setting
     * @param value value of the setting
     * */
    public static void set(PlayerFileType playerFileType, UUID uuid, String key, Object value) {
        CustomConfig.set(playerFileType.getPath(uuid), key, value);
        save(playerFileType, uuid);
    }

    /**
     * Reload a player settings file
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * */
    public static void reload(PlayerFileType playerFileType, UUID uuid) {
        CustomConfig.reload(playerFileType.getPath(uuid));
    }

    /**
     * Checks if the settings file of a player exists
     * @param playerFileType The type of file that should be got
     * @param uuid The uuid of the players file that should be got
     * @return True if the file exists. False if the file does not exist
     * */
    public static boolean checkForExistence(PlayerFileType playerFileType, UUID uuid) {
        return CustomConfig.checkForExistence(playerFileType.getPath(uuid));
    }

}
