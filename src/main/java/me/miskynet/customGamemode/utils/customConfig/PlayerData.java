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

    public enum FileType {

        SETTINGS("settings.yml"),
        BALANCE("balance.yml"),
        STATS("stats.yml");

        private final String fileName;

        FileType(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return this.fileName;
        }

        public String getPath(java.util.UUID uuid) {
            return "playerData/" + uuid.toString() + "/" + this.fileName;
        }

    }

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
     * */
    public static void setup(FileType fileType, UUID uuid) {

        File file = new File(Main.getInstance().getDataFolder(), fileType.getPath(uuid));

        Player player = Bukkit.getPlayer(uuid);

        if (!file.exists()) {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                file.createNewFile();
            } catch (IOException ex) {
                Bukkit.getLogger().warning("Couldn't create settings file for: " + player.getName() + " - " + player.getUniqueId());
                ex.printStackTrace();
                return;
            }
        }

        CustomConfig.files.put(fileType.getPath(uuid), file);
        CustomConfig.configs.put(fileType.getPath(uuid), YamlConfiguration.loadConfiguration(file));
    }

    /**
     * Get an instance of a player settings file
     * @return Config itself
     * */
    public static FileConfiguration get(FileType fileType, UUID uuid) {
        return CustomConfig.get(fileType.getPath(uuid));
    }

    /**
     * Get an object out of a player settings file
     * @param key setting name that should be searched for
     * @return {@link Object}
     * */
    public static Object get(FileType fileType, UUID uuid, String key) {
        return CustomConfig.get(fileType.getPath(uuid), key);
    }

    /**
     * Save data into a player setting
     * */
    public static void save(FileType fileType, UUID uuid) {
        CustomConfig.save(fileType.getPath(uuid));
    }

    /**
     * Set a setting for a player
     * @param key key of the setting
     * @param value value of the setting
     * */
    public static void set(FileType fileType, UUID uuid, String key, Object value) {
        CustomConfig.set(fileType.getPath(uuid), key, value);
        save(fileType, uuid);
    }

    /**
     * Reload a player settings file
     * */
    public static void reload(FileType fileType, UUID uuid) {
        CustomConfig.reload(fileType.getPath(uuid));
    }

    /**
     * Checks if the settings file of a player exists
     * @return True if the file exists. False if the file does not exist
     * */
    public static boolean checkForExistence(FileType fileType, UUID uuid) {
        return CustomConfig.checkForExistence(fileType.getPath(uuid));
    }

}
