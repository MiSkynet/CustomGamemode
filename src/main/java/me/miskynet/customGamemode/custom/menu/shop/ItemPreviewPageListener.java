package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.shop.ItemPreviewInteractItem;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemPreviewPageListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {

        if (event.getClickedInventory().getHolder() instanceof ItemPreviewPage itemPreviewPage) {

            ShopItem shopItem = itemPreviewPage.shopItem;

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            // slots to buy stuff
            if (event.getSlot() >= 19 && event.getSlot() <= 25) {

                Double itemPrice = shopItem.getBuyPrice();

                if (Main.economyManager.getBalance(player) < itemPrice) {
                    player.sendMessage("&cYou don't have enough money to buy this!");
                    return;
                }

                Item resultItem = shopItem.getResultItem();

                var giveItem = player.getInventory().addItem(resultItem.toItemStack());

                // cancel the buy if the item couldn't be added to the player inventory
                if (!giveItem.isEmpty()) {
                    player.sendMessage(Utils.component("&cSorry, but the item couldn't be added to your inventory and the buy was cancelled"));
                    return;
                }

                Main.economyManager.addBalance(player, itemPrice * -1);
                player.sendMessage(Utils.component("&7You bought " + event.getInventory().getItem(event.getSlot()).getType().name() +
                        " for &a" + itemPrice + Main.economyManager.getEcoSymbol() +
                        "&7 (New Balance: &a" + Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(player)) +
                        Main.economyManager.getEcoSymbol() + "&7)"));

            }
        }

    }

}
