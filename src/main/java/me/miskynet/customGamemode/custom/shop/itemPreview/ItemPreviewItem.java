package me.miskynet.customGamemode.custom.shop.itemPreview;

import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.shop.ShopItem;
import me.miskynet.customGamemode.utils.ComponentManager;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

/**
 * This is a helper method for the {@link ItemPreviewMenu}. It creates
 * the buttons to buy and sell a {@link ShopItem}. This creates an {@link PlayerHead}
 * and later can be turned into a {@link ItemStack} by using {@link #toItemStack()}
 * */
public class ItemPreviewItem {

    /**
     * The type of item the head is for (either buying or selling)
     * */
    public enum ItemType {
        BUY,
        SELL
    }

    private ItemType itemType;
    private int amount;
    private double price;
    private Integer maxStackSize;

    public static NamespacedKey itemTypeKey = new NamespacedKey(Main.getInstance(), "itemtype");
    public static NamespacedKey amountKey = new NamespacedKey(Main.getInstance(), "amount");
    public static NamespacedKey priceKey = new NamespacedKey(Main.getInstance(), "price");

    /**
     * @param itemType The type of item it is
     * @param amount The quantity of the item
     * @param price The price of the item that will be given or removed from the player
     * */
    public ItemPreviewItem(ItemType itemType, int amount, double price, Integer maxStackSize) {
        this.itemType = itemType;
        this.amount = amount;
        this.price = price;
        this.maxStackSize = maxStackSize;
    }

    /**
     * Converts the {@link ItemPreviewItem} into an {@link ItemStack}
     *
     * @return {@link ItemStack}
     * */
    public ItemStack toItemStack() {

        // return an unavailable item stack
        // if the max stack size is greater than the amount
        if (this.maxStackSize < this.amount) return getUnavailableItem();

        // else simple build and return the item stack
        PlayerHead item = new PlayerHead(ComponentManager.component(false, "&cError while creating item!"), "5c8817ee8e9c2c2bf767487737e0a60a5e09b725138e14daa57480a03f1766d8");
        ArrayList<Component> lore = new ArrayList<>();
        if (itemType.toString().toLowerCase().equals("buy")) {
            item = new PlayerHead(ComponentManager.component(false, "&aBuy " + amount),"23a45195193b5d6de5c522171d6a75abdaa78aacd901556dd0d8817c0ed810f3");
            lore.add(ComponentManager.component(false, "&7Buy " + amount + " for " + Main.economyManager.getDisplayFormat(amount * price) + Main.economyManager.getEcoSymbol()));
        }else if (itemType.toString().toLowerCase().equals("sell")) {
            item = new PlayerHead(ComponentManager.component(false, "&aSell " + amount),"4bf360ee0b5578f10189900a21d631e6a88c296cfa3a00f1e2c2dc73588a3a8d");
            lore.add(ComponentManager.component(false, "&7Sell " + amount + " for " + Main.economyManager.getDisplayFormat(amount * price) + Main.economyManager.getEcoSymbol()));
        }

        item.setLore(lore);
        ItemStack itemStack = item.toItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.getPersistentDataContainer().set(itemTypeKey, PersistentDataType.STRING, this.itemType.toString());
        itemMeta.getPersistentDataContainer().set(amountKey, PersistentDataType.INTEGER, this.amount);
        itemMeta.getPersistentDataContainer().set(priceKey, PersistentDataType.DOUBLE, this.price);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    /**
     * Creates an {@link ItemPreviewItem} from an {@link ItemStack}
     *
     * @return {@link ItemPreviewItem}
     * */
    public static ItemPreviewItem fromItemStack(ItemStack itemStack) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        ItemType itemType = ItemType.valueOf(itemMeta.getPersistentDataContainer().get(itemTypeKey, PersistentDataType.STRING));
        Integer amount = null;
        if (itemMeta.getPersistentDataContainer().get(amountKey, PersistentDataType.INTEGER) != null) {
            amount = itemMeta.getPersistentDataContainer().get(amountKey, PersistentDataType.INTEGER);
        }
        Double price = null;
        if (itemMeta.getPersistentDataContainer().get(priceKey, PersistentDataType.DOUBLE) != null) {
            price = itemMeta.getPersistentDataContainer().get(priceKey, PersistentDataType.DOUBLE);
        }

        if (amount == null || price == null) return null;

        Integer maxStackSize = itemStack.getMaxStackSize();

        return new ItemPreviewItem(itemType, amount, price, maxStackSize);
    }

    /**
     * Gets the price it will cost to buy the {@link ShopItem} at a specific quantity
     *
     * @return Price as {@link Double}
     * */
    public Double getPrice() {
        return this.price;
    }

    /**
     * Gets the amount that the item will be returned/required
     *
     @return Amount as {@link Integer}
     * */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * Gets the unavailable item, that will be set into the
     * menu instead of the {@link ItemPreviewMenu}
     *
     * @return Unavailable item as {@link ItemStack}
     * */
    public static ItemStack getUnavailableItem() {
        return new Item(Material.BARRIER, ComponentManager.component(false, "&cNot available")).toItemStack();
    }
}
