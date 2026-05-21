package me.miskynet.customGamemode.custom.entitys;

import me.miskynet.customGamemode.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void entityClick(PlayerInteractAtEntityEvent event) {

        PersistentDataContainer pdc = event.getRightClicked().getPersistentDataContainer();
        NamespacedKey menuLink = new NamespacedKey(Main.getInstance(), "entity_menu_link");
        int entityMenuId = pdc.get(menuLink, PersistentDataType.INTEGER);

        if (Main.menuManager.searchId(entityMenuId) != null) {
            Main.menuManager.searchId(entityMenuId).openForPlayer(event.getPlayer());
        }
    }
}
