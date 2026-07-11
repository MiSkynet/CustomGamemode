package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.custom.shop.ShopMenu;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class IndexMenuListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof IndexMenu indexMenu) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            if (!Utils.checkForAllowedClick(player)) return;
            Utils.createClickCooldown(player);

            Integer clickedSlot = event.getSlot();
        }

    }

}
