package me.miskynet.customGamemode.custom.shop.itemPreview;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.shop.ShopMenu;
import me.miskynet.customGamemode.custom.shop.ShopItem;
import me.miskynet.customGamemode.utils.ComponentManager;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPreviewListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof ItemPreviewMenu itemPreview) {

            ShopItem shopItem = itemPreview.getShopItem();

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if (!Utils.checkForAllowedClick(player)) return;
            Utils.createClickCooldown(player);

            if (event.getSlot() == 0) {
                ShopMenu shopMenu = new ShopMenu(itemPreview.getLastPage());
                shopMenu.openForPlayer(player);
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
                    player.sendMessage(ComponentManager.component("&cYou don't have enough money to buy this!"));
                    return;
                }

                var giveItem = player.getInventory().addItem(resultItem.setAmount(amount).toItemStack());

                // cancel the buy if the item couldn't be added to the player inventory
                if (!giveItem.isEmpty()) {
                    player.sendMessage(ComponentManager.component("&cSorry, but the item couldn't be added to your inventory and the buy was cancelled"));
                    return;
                }

                Main.economyManager.addBalance(player, finalPrice * -1);

                player.sendMessage(ComponentManager.component("&7You bought " + ComponentManager.fromComponent(resultItem.toItemStack().getItemMeta().displayName()) +
                        "&r&7 for &a" + finalPrice + Main.economyManager.getEcoSymbol() + "&7 (New Balance: &a" +
                        Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(player)) + Main.economyManager.getEcoSymbol() + "&7)"));
            }

            // slots to sell items
            if (event.getSlot() >= 28 && event.getSlot() <= 34) {

                if (event.getClickedInventory().getItem(event.getSlot()).equals(ItemPreviewItem.getUnavailableItem())) return;

                Item resultItem = shopItem.getResultItem();

                ItemPreviewItem clickedItem = ItemPreviewItem.fromItemStack(event.getClickedInventory().getItem(event.getSlot()));

                Integer amount = clickedItem.getAmount();

                if (!checkForAmount(player, resultItem.toItemStack(), amount)) {
                    player.sendMessage(ComponentManager.component("&cYou don't have enough of this item to sell that much!"));
                    return;
                }

                removeItems(player, resultItem.toItemStack(), amount);

                Main.economyManager.addBalance(player, clickedItem.getPrice());

                player.sendMessage(ComponentManager.component("&7You sold " + ComponentManager.fromComponent(resultItem.toItemStack().getItemMeta().displayName()) +
                        "&r&7 for &a" + clickedItem.getPrice() + Main.economyManager.getEcoSymbol() + "&7 (New Balance: &a" +
                        Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(player)) + Main.economyManager.getEcoSymbol() + "&7)"));
            }
        }
    }

    private boolean checkForAmount(Player player, ItemStack itemToFind, int amountNeeded) {
        int count = 0;

        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.isSimilar(itemToFind)) {
                count += stack.getAmount();
            }
        }

        if (count < amountNeeded) {
            return false;
        }
        return true;
    }

    private boolean removeItems(Player player, ItemStack itemToFind, int amountNeeded) {
        int remainingToRemove = amountNeeded;
        ItemStack[] contents = player.getInventory().getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack stack = contents[i];

            if (stack != null && stack.isSimilar(itemToFind)) {
                int stackAmount = stack.getAmount();

                if (stackAmount <= remainingToRemove) {
                    remainingToRemove -= stackAmount;
                    contents[i] = null;
                } else {
                    stack.setAmount(stackAmount - remainingToRemove);
                    remainingToRemove = 0;
                }
            }

            if (remainingToRemove <= 0) {
                break;
            }
        }

        player.getInventory().setContents(contents);
        return true;
    }

}
