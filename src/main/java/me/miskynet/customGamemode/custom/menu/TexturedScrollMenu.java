package me.miskynet.customGamemode.custom.menu;

import com.google.gson.JsonIOException;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Debugger;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.List;

public class TexturedScrollMenu extends TextureMenu {

    private int currentPage;
    private int itemsPerPage;
    private int maxPage = 1;

    public static int nextPageButtonSlot = 4;
    public static int previousPageButtonSlot = 6;

    private final Language language = Main.getInstance().getLanguage();

    /**
     * @param title   The title of the {@link TextureMenu}
     * @param size    The size of the {@link TextureMenu} (A multiple of 9 and max 54)
     * @param unicode The Unicode of the GUI in the Resource Pack
     */
    public TexturedScrollMenu(Component title, int size, String unicode) {
        super(title, size, unicode);
        this.itemsPerPage = size - 9;
    }


    @Override
    public void buildMenu() {
        fillEmptySlots();
    }

    /**
     * Builds the menu for a specific player
     *
     * @param player The player the menu is being built for
     * */
    @Override
    public void buildMenu(Player player) {}

    /**
     * Adds the navigation bar to the {@link TextureMenu}. This includes the next and previous page buttons, as well as the current page indicator.
     * */
    public void addNavigationBar() {

        PlayerHead arrowLeft = new PlayerHead(ComponentUtils.component(false, language.getString("shop.item.previousPage")), "528b8cf405eaf606a0210f0303b013179f8f12eaa95824129ebeef9e44b68230");
        PlayerHead arrowRight = new PlayerHead(ComponentUtils.component(false, language.getString("shop.item.nextPage")), "5dcda6e3c6dca7e9b8b6ba3febf5cd0917f997b64b2aef18c3f773765e3a579");

        /*
         * calculate if there is at least one item to fit in a new page
         * when yes, put the button
         * when not, put a barrier instead of the button
         * */
        if ((maxPage - 1) > currentPage) {
            this.getInventory().setItem(this.getInventory().getSize() - nextPageButtonSlot, arrowRight.toItemStack());
        }else {
            Item item = new Item(Material.BARRIER, ComponentUtils.component(false, "&cNo next page"));
            this.getInventory().setItem(this.getInventory().getSize() - nextPageButtonSlot, item.toItemStack());
        }

        if (this.currentPage > 0) {
            this.getInventory().setItem(this.getInventory().getSize() - previousPageButtonSlot, arrowLeft.toItemStack());
        }else {
            Item item = new Item(Material.BARRIER, ComponentUtils.component(false, "&cNo previous page"));
            this.getInventory().setItem(this.getInventory().getSize() - previousPageButtonSlot, item.toItemStack());
        }

        // item to present the current page
        String currentPageDisplayName = language.getString("shop.currentPage.displayName").replace("%currentPage%", String.valueOf(this.currentPage + 1));
        this.getInventory().setItem(this.getInventory().getSize() - 5, new Item(Material.BOOK, ComponentUtils.component(false, currentPageDisplayName)).toItemStack());

        this.getInventory().setItem(this.getInventory().getSize() - 9, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 8, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 7, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 3, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 2, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 1, this.getInvisibleItem());
    }

    /**
     * Gets the current page of the {@link TexturedScrollMenu}
     *
     * @return Current page as {@link Integer}
     * */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * Sets the current page of the {@link TexturedScrollMenu}.
     * To apply this change, you need to call the {@link #buildMenu()} function afterward for the {@link Player} to see the new current page
     * */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Increases the current page of the {@link TexturedScrollMenu} by 1.
     * To apply this change, you need to call the {@link #buildMenu()} function afterward for the {@link Player} to see the new current page
     * */
    public void increasePage() {
        // format the getMaxPage into index based, since the currentPage works by index
        if (this.currentPage < (this.getMaxPage() - 1)) this.currentPage++;
    }

    /**
     * Decreases the current page of the {@link TexturedScrollMenu} by 1.
     * To apply this change, you need to call the {@link #buildMenu()} function afterward for the {@link Player} to see the new current page
     * */
    public void decreasePage() {
        if (this.currentPage > 0) this.currentPage--;
    }

    /**
     * Set the max amount of pages for the {@link TexturedScrollMenu}. This is used to determine if the next page button should be displayed or not.
     * To calculate the max page automatically, you can use the {@link #calculateMaxPage(int)} function, which will return the max page
     *
     * @param maxPage The max page as {@link Integer}
     * */
    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    /**
     * Calculates the max amount of pages for the {@link TexturedScrollMenu}
     *
     * @param itemTotal The total amount of items for the {@link TexturedScrollMenu} as {@link Integer}
     * @return The max page as {@link Integer}
     * */
    public int calculateMaxPage(int itemTotal) {
        return (itemTotal + getItemsPerPage() - 1) / getItemsPerPage();
    }

    /**
     * Gets the max page of the {@link TexturedScrollMenu}
     *
     * @return Max page as {@link Integer}
     * */
    public int getMaxPage() {
        return this.maxPage;
    }

    /**
     * Manually sets the amount of items per page for the {@link TexturedScrollMenu}. This is used to determine how many items should be displayed on each page,
     * not including the navigation bar. The default value is the size of the {@link TextureMenu} minus 9, since the last row is reserved for the navigation bar.
     * Do not set this value to a number higher than the size of the {@link TextureMenu} minus 9, as this will cause the navigation bar to be overwritten.
     *
     * @param itemsPerPage The amount of items per page as {@link Integer}
     * */
    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * Gets the amount of items per page for the {@link TexturedScrollMenu}
     *
     * @return Items per page as {@link Integer}
     * */
    public int getItemsPerPage() {
        return this.itemsPerPage;
    }

    /**
     * Gets ab invisible item that can be used to fill empty slots in the {@link TexturedScrollMenu}.
     *
     * @return Invisible item as {@link ItemStack}
     * */
    public ItemStack getInvisibleItem() {
        Item item = new Item(Material.GHAST_TEAR);
        ItemStack itemStack = item.toItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setHideTooltip(true);
        CustomModelDataComponent component = itemMeta.getCustomModelDataComponent();
        component.setStrings(List.of("invisible_item"));
        itemMeta.setCustomModelDataComponent(component);
        item.setItemMeta(itemMeta);
        return item.toItemStack();
    }


}
