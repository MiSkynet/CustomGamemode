package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Material;
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

                Double itemPrice = ShopItem.fromItemStack(event.getInventory().getItem(event.getSlot())).getBuyPrice();

                if (Main.economyManager.getBalance(player) >= itemPrice) {

                    Material resultMaterial = ShopItem.fromItemStack(shop.getItem(event.getSlot())).toItemStack().getType();

                    var giveItem = player.getInventory().addItem(new Item(resultMaterial).toItemStack());

                    // cancel the buy if the item couldn't be added to the player inventory
                    if (!giveItem.isEmpty()) {
                        player.sendMessage(Utils.component("&cSorry, but the item couldn't be added to your inventory and the buy was cancelled"));
                        return;
                    }

                    Main.economyManager.addBalance(player, itemPrice * -1);
                    player.sendMessage(Utils.component("&7You bought " + event.getInventory().getItem(event.getSlot()).getType().name() +
                            " for &a" + itemPrice + Main.economyManager.getEcoSymbol() +
                            "&7 (New Balance: &a" + Main.economyManager.getBalanceDisplayFormat(player) +
                            Main.economyManager.getEcoSymbol() + "&7)"));
                }
            }

            Utils.createClickCooldown(player);
        }
    }
}
