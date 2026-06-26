package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.shop.ItemPreviewItem;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemPreviewListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof ItemPreview itemPreview) {

            ShopItem shopItem = itemPreview.getShopItem();

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if (!Utils.checkForAllowedClick(player)) return;

            if (event.getSlot() == 0) {
                Shop shop = new Shop(itemPreview.getLastPage());
                shop.openForPlayer(player);
            }

            // slots to buy stuff
            if (event.getSlot() >= 19 && event.getSlot() <= 25) {

                if (event.getClickedInventory().getItem(event.getSlot()).equals(ItemPreviewItem.getUnavailableItem())) return;

                Item resultItem = shopItem.getResultItem();

                ItemPreviewItem clickedItem = ItemPreviewItem.fromItemStack(event.getClickedInventory().getItem(event.getSlot()));

                Integer amount = clickedItem.getAmount();
                Double finalPrice = clickedItem.getPrice() * clickedItem.getAmount();

                if (event.getClickedInventory().getItem(event.getSlot()).equals(ItemPreviewItem.getUnavailableItem())) return;

                if (Main.economyManager.getBalance(player) < finalPrice) {
                    player.sendMessage(Utils.component("&cYou don't have enough money to buy this!"));
                    return;
                }

                var giveItem = player.getInventory().addItem(resultItem.setAmount(amount).toItemStack());

                // cancel the buy if the item couldn't be added to the player inventory
                if (!giveItem.isEmpty()) {
                    player.sendMessage(Utils.component("&cSorry, but the item couldn't be added to your inventory and the buy was cancelled"));
                    return;
                }

                Main.economyManager.addBalance(player, finalPrice * -1);
                player.sendMessage(Utils.component("&7You bought " + Utils.fromComponent(resultItem.getDisplayName()) +
                        "&r&7 for &a" + finalPrice + Main.economyManager.getEcoSymbol() +
                        "&7 (New Balance: &a" + Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(player)) +
                        Main.economyManager.getEcoSymbol() + "&7)"));



            }

            Utils.createClickCooldown(player);
        }

    }

}
