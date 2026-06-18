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

    public ShopItem(Material material, Double buyPrice, Double sellPrice) {
        super(material, null, new ArrayList<>());

        List<Component> lore = new ArrayList<>();
        lore.add(Utils.component(false, "&7Buy Price: &6" + buyPrice));
        lore.add(Utils.component(false, "&7Sell Price: &6" + sellPrice));

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;

        super.setLore(lore);
    }

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

    public Double getBuyPrice() { return buyPrice; }
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
