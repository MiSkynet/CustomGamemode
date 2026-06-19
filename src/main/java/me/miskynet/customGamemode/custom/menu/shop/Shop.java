package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop extends TextureMenu {

    private static Shop shop;
    public static int currentPage = 0;
    public static int itemsPerPage = 45;

    // caching all the items for the shop
    private static final List<ShopItem> cachedItems = new ArrayList<>();

    /**
     * The Shop is a type of {@link TextureMenu} where players can
     * buy and sell items.
     * @param page The page at which the shop should be opened
     * */
    public Shop(Integer page) {
        super(Utils.component("ShopCommand"), 54, "\uE003");
        shop = this;
        currentPage = page;
        buildShopSite(shop);
    }

    /**
     * This function is used to increase the page
     * of the {@link Shop}.
     * Please only use this function to increase the page
     * of a player!
     * */
    public void increasePage() {
        if (getShopItems().size() >= ((currentPage + 1) * itemsPerPage) + 1) {
            currentPage = currentPage + 1;
            buildShopSite(shop);
        }
    }

    /**
     * This function is used to decrease the page
     * of the {@link Shop}.
     * Please only use this function to decrease the page
     * of a player!
     * */
    public void decreasePage() {
        if (currentPage >= 1) {
            currentPage = currentPage - 1;
            buildShopSite(shop);
        }
    }

    /**
     * This function (re)-builds the gui from ground up.
     * It replaces every item and then sets it back into the inventory.
     * Don't directly call this function, use the {@link #increasePage()} or {@link #decreasePage()} function instead!
    * */
    private void buildShopSite(TextureMenu shop) {

        PlayerHead arrowLeft = new PlayerHead(Utils.component(false, "&ePrevious Page"), "528b8cf405eaf606a0210f0303b013179f8f12eaa95824129ebeef9e44b68230");
        PlayerHead arrowRight = new PlayerHead(Utils.component(false, "&eNext Page"), "5dcda6e3c6dca7e9b8b6ba3febf5cd0917f997b64b2aef18c3f773765e3a579");

        /*
        * calculate if there is at least one item to fit in a new page
        * when yes, put the button
        * when not, put a barrier instead of the button
        * */
        if (getShopItems().size() >= ((currentPage + 1) * itemsPerPage) + 1) {
            shop.getInventory().setItem(50, arrowRight.toItemStack());
        }else {
            Item item = new Item(Material.BARRIER, Utils.component(false, "&cNo next page"));
            shop.getInventory().setItem(50, item.toItemStack());
        }

        if (currentPage != 0) {
            shop.getInventory().setItem(48, arrowLeft.toItemStack());
        }else {
            Item item = new Item(Material.BARRIER, Utils.component(false, "&cNo previous page"));
            shop.getInventory().setItem(48, item.toItemStack());
        }

        // item to present the current page
        shop.getInventory().setItem(49, new Item(Material.BOOK, Utils.component(false, "&eCurrent Page: " + (currentPage + 1))).toItemStack());

        fillShopItems(shop);
        shop.fillEmptySlots();
    }

    /**
     * Fill the shop with shop items and ignore the {@link #getEmptySlots()} slots
     * @param shop The shop that the items should be put in
     * */
    public static void fillShopItems(TextureMenu shop) {
        int shopItemStart = currentPage * itemsPerPage;
        int currentShopItem = shopItemStart;

        // clear the slots
        for (int i = 0; i < 54; i++) {
            if (!getEmptySlots().contains(i)) {
                shop.getInventory().setItem(i, null);
            }
        }

        for (int i = 0; i < 54; i++) {
            if (getEmptySlots().contains(i)) continue;

            if (currentShopItem >= getShopItems().size()) break;

            shop.getInventory().setItem(i, getShopItems().get(currentShopItem).toItemStack());

            currentShopItem++;
        }
    }

    /**
     * Caches all shop items so the items are ready when the first
     * user loads the menu. If the cache is not performed, the menu
     * will take around 0.7s to open, due to the large amount of
     * items that have to be created
     * */
    public static void cacheShopItems() {

        int limit = 500;
        int current = 0;

        for (Material material : Material.values()) {

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

            cachedItems.add(new ShopItem(material, buyPriceRounded, sellPriceRounded));

            current++;
        }
    }

    /**
     * Get the Items from the {@link #cachedItems} map
     * @return ShopCommand items
     * */
    public static List<ShopItem> getShopItems() {
        return cachedItems;
    }

    /**
     * Get the inventory of the {@link Shop}
     * @return Inventory of the shop
     * */
    @Override
    public @NotNull Inventory getInventory() {
        return super.getInventory();
    }

    /**
     * Get the empty slots of the {@link Shop}
     * @return Returns an ArrayList of the empty slots
     * */
    public static ArrayList<Integer> getEmptySlots() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(45);
        list.add(46);
        list.add(47);
        list.add(48);
        list.add(49);
        list.add(50);
        list.add(51);
        list.add(52);
        list.add(53);
        return list;
    }
}
