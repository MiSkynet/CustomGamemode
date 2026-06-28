package me.miskynet.customGamemode.custom.shop;

import me.miskynet.customGamemode.custom.shop.itemPreview.ItemPreviewMenu;
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
     * Gets the clicks in the {@link ShopMenu}
     * */
    @EventHandler
    public void click(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof ShopMenu) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            ShopMenu shopMenu = (ShopMenu) event.getClickedInventory().getHolder();

            if (!Utils.checkForAllowedClick(player)) return;

            if (event.getSlot() == 48) {
                shopMenu.decreasePage();
            }

            if (event.getSlot() == 50) {
                shopMenu.increasePage();
            }

            if (!shopMenu.getEmptySlots().contains(event.getSlot())) {

                ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
                ItemMeta meta = clickedItem.getItemMeta();
                Integer id = meta.getPersistentDataContainer().get(ShopItem.idKey, PersistentDataType.INTEGER);
                ShopItem shopItem = ShopMenu.getItemById(id);

                ItemPreviewMenu itemPreview = new ItemPreviewMenu(Utils.component("Buy or sell"), 45, "\uE005", shopMenu.getCurrentPage(), shopItem);
                itemPreview.openForPlayer(player);
            }

            Utils.createClickCooldown(player);
        }
    }
}
