package me.miskynet.customGamemode.custom.shop;

import me.miskynet.customGamemode.custom.menu.TexturedScrollMenu;
import me.miskynet.customGamemode.custom.shop.itemPreview.ItemPreviewMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
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
            Utils.createClickCooldown(player);

            if (event.getSlot() == event.getInventory().getSize() - TexturedScrollMenu.previousPageButtonSlot) {
                shopMenu.decreasePage();
                shopMenu.buildMenu();
            }

            if (event.getSlot() == event.getInventory().getSize() - TexturedScrollMenu.nextPageButtonSlot) {
                shopMenu.increasePage();
                shopMenu.buildMenu();
            }

            if (event.getSlot() < event.getClickedInventory().getSize() - 9) {

                if (event.getClickedInventory().getItem(event.getSlot()) == null) return;

                ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
                ItemMeta meta = clickedItem.getItemMeta();
                Integer id = meta.getPersistentDataContainer().get(ShopItem.idKey, PersistentDataType.INTEGER);

                ShopItem shopItem = ShopMenu.getItemById(id);

                ItemPreviewMenu itemPreview = new ItemPreviewMenu(ComponentUtils.component("Buy or sell"), 45, "\uE005", shopMenu.getCurrentPage(), shopItem);
                itemPreview.openForPlayer(player);
            }
        }
    }
}
