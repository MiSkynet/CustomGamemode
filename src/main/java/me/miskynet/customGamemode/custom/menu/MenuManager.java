package me.miskynet.customGamemode.custom.menu;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class MenuManager {

    private HashMap<UUID, Menu> menuMap = new HashMap<>();

    public void addMenu(UUID uuid, Menu menu) {
        menuMap.put(uuid, menu);
    }



}
