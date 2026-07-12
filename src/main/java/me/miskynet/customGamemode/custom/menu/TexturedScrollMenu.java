package me.miskynet.customGamemode.custom.menu;

import com.google.gson.JsonIOException;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Debugger;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
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

    /**
     * @param title   The title of the {@link TextureMenu}
     * @param size    The size of the {@link TextureMenu} (A multiple of 9 and max 54)
     * @param unicode The Unicode of the GUI in the Resource Pack
     */
    public TexturedScrollMenu(Component title, int size, String unicode) {
        super(title, size, unicode);
        this.itemsPerPage = size - 9;
    }

    public void addNavigationBar() {

        PlayerHead arrowLeft = new PlayerHead(ComponentUtils.component(false, "&ePrevious Page"), "528b8cf405eaf606a0210f0303b013179f8f12eaa95824129ebeef9e44b68230");
        PlayerHead arrowRight = new PlayerHead(ComponentUtils.component(false, "&eNext Page"), "5dcda6e3c6dca7e9b8b6ba3febf5cd0917f997b64b2aef18c3f773765e3a579");

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
        this.getInventory().setItem(this.getInventory().getSize() - 5, new Item(Material.BOOK, ComponentUtils.component(false, "&eCurrent Page: " + (this.currentPage + 1))).toItemStack());

        this.getInventory().setItem(this.getInventory().getSize() - 9, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 8, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 7, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 3, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 2, this.getInvisibleItem());
        this.getInventory().setItem(this.getInventory().getSize() - 1, this.getInvisibleItem());
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void increasePage() {
        // subtract one since the calculation of the max page is based on
        // the number of items and not the index of the page and the current page is index based
        if (this.currentPage < (this.getMaxPage() - 1)) this.currentPage++;
    }

    public void decreasePage() {
        if (this.currentPage > 0) this.currentPage--;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getItemsPerPage() {
        return this.itemsPerPage;
    }

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
