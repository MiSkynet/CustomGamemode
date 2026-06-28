package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.shop.ItemPreviewItem;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ShopListener implements Listener {

    /**
     * Gets the clicks in the {@link Shop}
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

                ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
                ItemMeta meta = clickedItem.getItemMeta();
                Integer id = meta.getPersistentDataContainer().get(ShopItem.idKey, PersistentDataType.INTEGER);
                ShopItem shopItem = Shop.getItemById(id);

                ItemPreview itemPreview = new ItemPreview(Utils.component("Buy or sell"), 45, "\uE005", shop.getCurrentPage(), shopItem);
                itemPreview.openForPlayer(player);
            }

            Utils.createClickCooldown(player);
        }
    }
}
