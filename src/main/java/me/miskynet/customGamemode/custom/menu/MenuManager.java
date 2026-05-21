package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.custom.Utils;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

public class MenuManager {

    private HashMap<Integer, Menu> menuMap = new HashMap<>();

    public void addMenu(Integer id, Menu menu) {
        menuMap.put(id, menu);
    }

    public HashMap<Integer, Menu> getMenuMap() {
        return this.menuMap;
    }

    public Menu searchId(int id) {
        return menuMap.get(id);
    }

    /*
    * This function only needs to be used if there are menus that are
    * not created when starting the server or executing a function (like for custom entitys)
    * */
    public void menuSetup() {

        /*
        * menu for the interact entity
        * */
        Menu interactEntity = new Menu(Utils.component("Test Menu"), 27, 82019383);
        interactEntity
                .addItem(new Item(Material.DRAGON_EGG, Utils.component("§cCooles Ei")))
                .addItem(new Item(Material.BARRIER, Utils.component("§cBack")));

    }

}
