package me.miskynet.customGamemode.custom.item.shop;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShopItem extends Item {

    Double buyPrice;
    Double sellPrice;

    private static final NamespacedKey buyKey = new NamespacedKey(Main.getInstance(), "buy_price");
    private static final NamespacedKey sellKey = new NamespacedKey(Main.getInstance(), "sell_price");

    /**
     * {@link ShopItem} is a type of {@link Item}. It is used to create
     * items in the {@link me.miskynet.customGamemode.custom.menu.shop.Shop}. It contains
     * information like the price to buy and sell the Item
     * @param material Material of the Item
     * @param buyPrice Price to buy the Item
     * @param sellPrice Price a player will receive when selling the Item
     * */
    public ShopItem(Material material, Double buyPrice, Double sellPrice) {
        super(material, null, new ArrayList<>());

        List<Component> lore = new ArrayList<>();
        lore.add(Utils.component(false, "&7Buy Price: &6" + buyPrice));
        lore.add(Utils.component(false, "&7Sell Price: &6" + sellPrice));

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;

        super.setLore(lore);
    }

    /**
     * Create a {@link ShopItem} from an {@link ItemStack}
     * @param itemStack Item stack that should be converted into a {@link ShopItem}
     * */
    public static ShopItem fromItemStack(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return null;

        ItemMeta meta = itemStack.getItemMeta();
        var container = meta.getPersistentDataContainer();

        if (container.has(buyKey, PersistentDataType.DOUBLE) && container.has(sellKey, PersistentDataType.DOUBLE)) {

            Double buy = container.get(buyKey, PersistentDataType.DOUBLE);
            Double sell = container.get(sellKey, PersistentDataType.DOUBLE);

            return new ShopItem(itemStack.getType(), buy, sell);
        }

        return null;
    }

    /**
     * Get the price of an {@link ShopItem} to buy it
     * */
    public Double getBuyPrice() { return buyPrice; }

    /**
     * Get the price of an {@link ShopItem} the player receives when
     * selling the item
     * */
    public Double getSellPrice() { return sellPrice; }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = super.toItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(buyKey, PersistentDataType.DOUBLE, this.buyPrice);
            meta.getPersistentDataContainer().set(sellKey, PersistentDataType.DOUBLE, this.sellPrice);

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

}
