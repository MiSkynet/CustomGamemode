package me.miskynet.customGamemode.custom.shop;

import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.custom.menu.TexturedScrollMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ShopMenu extends TexturedScrollMenu {

    // caching all the items for the shopMenu
    public static final List<ShopItem> cachedItems = new ArrayList<>();

    /**
     * The ShopMenu is a type of {@link TextureMenu} where players can
     * buy and sell items.
     *
     * @param page The page at which the shopMenu should be opened
     * */
    public ShopMenu(Integer page) {
        super(ComponentUtils.component("ShopCommand"), 54, "\uE003");
        this.setCurrentPage(page);
        this.setMaxPage((cachedItems.size() + this.getItemsPerPage() - 1) / this.getItemsPerPage());
        buildMenu();
    }

    /**
     * This function (re)-builds the gui from ground up.
     * It replaces every item and then sets it back into the inventory.
     * Don't directly call this function, use the {@link #increasePage()} or {@link #decreasePage()} function instead!
    * */
    @Override
    public void buildMenu() {
        this.getInventory().clear();
        this.addNavigationBar();
        this.fillShopItems();
    }

    public void fillShopItems() {
        int shopItemStart = this.getCurrentPage() * this.getItemsPerPage();
        int currentShopItem = shopItemStart;

        for (int i = 0; i < this.getInventory().getSize(); i++) {
            if (currentShopItem >= getShopItems().size()) break;
            this.getInventory().addItem(getShopItems().get(currentShopItem).toItemStack());
            currentShopItem++;
        }
    }

    /**
     * <strong>This list is only Temporary for testing!!</strong> <br>
     *
     * Caches all shopMenu items so the items are ready when the first
     * user loads the menu. If the cache is not performed, the menu
     * will take around 0.7s to open, due to the large amount of
     * items that have to be created
     * */
    public static void cacheShopItems() {

        int limit = 500;
        int current = 0;

        for (Material material : Material.values()) {

            int id = current;

            if (current >= limit) break;

            if (material.isAir() || material.name().startsWith("LEGACY_") || !material.isItem()) {
                continue;
            }

            Random random = new Random();

            Double buyPrice = random.nextDouble(1, 2000);

            // round the buy price to two decimals
            BigDecimal buyPriceBigDecimal = new BigDecimal(Double.toString(buyPrice));
            buyPriceBigDecimal = buyPriceBigDecimal.setScale(2, RoundingMode.HALF_UP);
            double buyPriceRounded = buyPriceBigDecimal.doubleValue();

            Double sellPrice = random.nextDouble(1, 2000);

            // round the sell price to two decimals
            BigDecimal sellPriceBigDecimal = new BigDecimal(Double.toString(sellPrice));
            sellPriceBigDecimal = sellPriceBigDecimal.setScale(2, RoundingMode.HALF_UP);
            double sellPriceRounded = sellPriceBigDecimal.doubleValue();

            ShopItem shopItem = new ShopItem(material, buyPriceRounded, sellPriceRounded);

            Item resultItem = new Item(material);

            resultItem.setDisplayName(ComponentUtils.component(false, "&d" + Utils.formatEnumToString(material.toString())));

            shopItem.setResultItem(resultItem);

            shopItem.setId(id);

            cachedItems.add(shopItem);

            current++;
        }
    }

    /**
     * Get the Items from the {@link #cachedItems} map
     *
     * @return ShopCommand items
     * */
    public static List<ShopItem> getShopItems() {
        return cachedItems;
    }

    /**
     * Get the inventory of the {@link ShopMenu}
     *
     * @return Inventory of the shopMenu
     * */
    @Override
    public @NotNull Inventory getInventory() {
        return super.getInventory();
    }

    /**
     * Gets a {@link List} of all cached {@link ShopItem}
     *
     * @return {@link List} with all {@link ShopItem}
     * */
    public static List<ShopItem> getCachedItems() {
        return cachedItems;
    }

    /**
     * Gets an {@link ShopItem} by the id
     *
     * @param id The id of the item
     * @return {@link ShopItem}
     * */
    public static ShopItem getItemById(Integer id) {
        for (ShopItem currentShopItem : getCachedItems()) {
            if (currentShopItem.getId().equals(id)) {
                return currentShopItem;
            }
        }
        return null;
    }
}
