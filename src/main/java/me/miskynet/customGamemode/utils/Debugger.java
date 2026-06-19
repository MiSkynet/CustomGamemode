package me.miskynet.customGamemode.utils;

import org.bukkit.Bukkit;

public class Debugger {

    private static final boolean ENABLED = true;

    /**
     * Create a simple debug message that gets printed in the config
     * @param message The message that should be printed
     * */
    public static void log(String message) {
        if (ENABLED) {
            Bukkit.getLogger().info("[Debug] " + message);
        }
    }

}
