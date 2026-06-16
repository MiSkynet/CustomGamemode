package me.miskynet.customGamemode.utils.customConfig;

import me.miskynet.customGamemode.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomConfig {

    private static final Map<String, FileConfiguration> configs = new HashMap<>();
    private static final Map<String, File> files = new HashMap<>();

    public static void setup(String fileName) {

        File file = new File(Main.getInstance().getDataFolder(), fileName);

        if (!file.exists()) {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            Main.getInstance().saveResource(fileName, false);
        }

        files.put(fileName, file);

        configs.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    public static FileConfiguration get(String fileName) {
        return configs.get(fileName);
    }

    /**
     * @return object out of config
     * */
    public static Object get(String filepath, String key) {
        return CustomConfig.get(filepath).get(key);
    }

    public static void save(String fileName) {
        try {
            configs.get(fileName).save(files.get(fileName));
        } catch (IOException e) {
            Bukkit.getLogger().warning("Could not save " + fileName);
        }
    }

    /**
     * Set a key and value into a filepath (this must be an)
     * existing fileconfiguration / config
     * */
    public static void set(String filepath, String key, Object value) {
        CustomConfig.get(filepath).set(key, value);
        CustomConfig.save("economy/playerData.yml");
    }

    public static void reload(String fileName) {
        configs.put(fileName, YamlConfiguration.loadConfiguration(files.get(fileName)));
    }
}