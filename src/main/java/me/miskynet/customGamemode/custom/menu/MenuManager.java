package me.miskynet.customGamemode.custom.menu;

import java.util.HashMap;

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
    }

}
