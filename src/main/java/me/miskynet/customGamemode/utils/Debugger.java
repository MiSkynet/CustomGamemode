package me.miskynet.customGamemode.utils;

import org.bukkit.Bukkit;

public class Debugger {

    private static final boolean ENABLED = true;

    public static void log(String message) {
        if (ENABLED) {
            Bukkit.getLogger().info("[Debug] " + message);
        }
    }

}
