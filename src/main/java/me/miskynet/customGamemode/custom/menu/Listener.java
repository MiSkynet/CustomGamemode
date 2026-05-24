package me.miskynet.customGamemode.custom.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof Menu) {
            Menu menu = (Menu) event.getClickedInventory().getHolder();

            if (menu.getMenuType() == Menu.MenuType.TEXTURE) {
                TextureMenu textureMenu = (TextureMenu) event.getClickedInventory().getHolder();
                if (!textureMenu.getInteractSlots().contains(event.getSlot())) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
