package me.miskynet.customGamemode.custom.shop;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ShopItem extends Item {

    private final Language language = Main.getInstance().getLanguage();

    Double buyPrice;
    Double sellPrice;
    Item resultItem = null;
    Integer id = null;

    private static final NamespacedKey buyKey = new NamespacedKey(Main.getInstance(), "buyPrice");
    private static final NamespacedKey sellKey = new NamespacedKey(Main.getInstance(), "sellPrice");
    public static final NamespacedKey idKey = new NamespacedKey(Main.getInstance(), "id");

    /**
     * {@link ShopItem} is a type of {@link Item}. It is used to create
     * items in the {@link ShopMenu}. It contains
     * information like the price to buy and sell the Item
     *
     * @param material Material of the Item
     * @param buyPrice Price to buy the Item
     * @param sellPrice Price a player will receive when selling the Item
     * */
    public ShopItem(Material material, Double buyPrice, Double sellPrice) {
        super(material, null, new ArrayList<>());

        List<Component> lore = new ArrayList<>();


        if (language.getStringList("shop.item.offerItem.lore") != null) {
            for (String string : language.getStringList("shop.item.offerItem.lore")) {
                lore.add(ComponentUtils.component(false, string
                        .replaceAll("%buyPrice%", String.valueOf(buyPrice))
                        .replaceAll("%sellPrice%", String.valueOf(sellPrice))));
            }
        }

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;

        super.setLore(lore);
    }

    /**
     * Gets the price of an {@link ShopItem} to buy it
     * */
    public Double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Gets the price of an {@link ShopItem} the player receives when
     * selling the item
     * */
    public Double getSellPrice() {
        return sellPrice;
    }

    /**
     * Converts the {@link ShopItem} into an {@link ItemStack}
     *
     * @return {@link ShopItem} as {@link ItemStack}
     * */
    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = super.toItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(buyKey, PersistentDataType.DOUBLE, this.buyPrice);
            meta.getPersistentDataContainer().set(sellKey, PersistentDataType.DOUBLE, this.sellPrice);

            if (this.id != null) {
                meta.getPersistentDataContainer().set(idKey, PersistentDataType.INTEGER, this.id);
            }

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    /**
     * Sets the result item of the {@link ShopItem}. The Result item is
     *
     * {@link Item} that is later converted into an {@link ItemStack}
     * @param item The {@link Item} that should be the result item
     * */
    public void setResultItem(Item item) {
        this.resultItem = item;
    }

    /**
     * Gets the result item
     *
     * @return Result item as {@link Item} or null when none is set
     * */
    public Item getResultItem() {
        return this.resultItem;
    }

    /**
     * Sets an id for the {@link ShopItem} to help identifying
     * an item
     *
     * @param id The id of the {@link ShopItem} as {@link Integer}
     * */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the id of an {@link ShopItem}
     *
     * @return Id as {@link Integer} or null when non is set
     * */
    public Integer getId() {
        return this.id;
    }


}
