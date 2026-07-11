package me.miskynet.customGamemode.custom.entity.npc;

import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.settings.SettingsMenu;
import me.miskynet.customGamemode.custom.shop.ShopMenu;
import me.miskynet.customGamemode.utils.ComponentManager;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class NPCInteractEvent implements Listener {

    @EventHandler
    public void interactEvent(PlayerInteractEntityEvent event) {

        Entity entity = event.getRightClicked();

        if (entity.getPersistentDataContainer().get(NPC.interactTypeKey, PersistentDataType.STRING) == null) return;

        if (!Utils.checkForAllowedClick(event.getPlayer())) return;

        Menu menu = new Menu(ComponentManager.component("&cERROR WHILE CREATING INVENTORY"), 9);

        if (Utils.getPDCOfEntity(entity, NPC.interactTypeKey, PersistentDataType.STRING).equals("SHOP")) {
            menu = new ShopMenu(0);
        }else if (Utils.getPDCOfEntity(entity, NPC.interactTypeKey, PersistentDataType.STRING).equals("SETTINGS")) {
            menu = new SettingsMenu();
        }

        menu.openForPlayer(event.getPlayer());

        Utils.createClickCooldown(event.getPlayer());
    }

}
