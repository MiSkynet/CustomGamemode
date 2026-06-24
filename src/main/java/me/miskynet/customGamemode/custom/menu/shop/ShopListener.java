package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopListener implements Listener {

    /**
     * Get the clicks in the {@link Shop}
     * */
    @EventHandler
    public void click(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof Shop) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            Shop shop = (Shop) event.getClickedInventory().getHolder();

            if (!Utils.checkForAllowedClick(player)) return;

            if (event.getSlot() == 48) {
                shop.decreasePage();
            }

            if (event.getSlot() == 50) {
                shop.increasePage();
            }

            if (!shop.getEmptySlots().contains(event.getSlot())) {

                ShopItem shopItem = ShopItem.fromItemStack(event.getInventory().getItem(event.getSlot()));

                ItemPreviewPage itemPreviewPage = new ItemPreviewPage(Utils.component("Buy or sell"), 45, "\uE005", shopItem);
                itemPreviewPage.openForPlayer(player);

            }

            Utils.createClickCooldown(player);
        }
    }
}
