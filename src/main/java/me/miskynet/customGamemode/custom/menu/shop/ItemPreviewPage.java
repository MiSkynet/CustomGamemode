package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.shop.ItemPreviewInteractItem;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import net.kyori.adventure.text.Component;

public class ItemPreviewPage extends TextureMenu {

    ShopItem shopItem;

    /**
     * The {@link TextureMenu} is a type of {@link me.miskynet.customGamemode.custom.menu.Menu} that can have a custom GUI design.
     * The design is applied by using Unicodes of a texture pack
     *
     * @param title   The title of the {@link TextureMenu}
     * @param size    The size of the {@link TextureMenu} (A multiple of 9 and max 54)
     * @param unicode The Unicode of the GUI in the Resource Pack
     */
    public ItemPreviewPage(Component title, int size, String unicode, ShopItem shopItem) {
        super(title, size, unicode);
        this.shopItem = shopItem;
        buildInventory();
    }

    /**
     * Build the inventory for the player
     * */
    public void buildInventory() {

        this.getInventory().setItem(4, shopItem.toItemStack());

        this.getInventory().setItem(19, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 1, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(20, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 2, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(21, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 4, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(22, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 8, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(23, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 16, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(24, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 32, shopItem.getBuyPrice()).toItemStack());
        this.getInventory().setItem(25, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.BUY, 64, shopItem.getBuyPrice()).toItemStack());

        this.getInventory().setItem(28, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 1, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(29, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 2, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(30, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 4, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(31, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 8, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(32, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 16, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(33, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 32, shopItem.getSellPrice()).toItemStack());
        this.getInventory().setItem(34, new ItemPreviewInteractItem(ItemPreviewInteractItem.ItemType.SELL, 64, shopItem.getSellPrice()).toItemStack());

        this.fillEmptySlots();
    }

    /**
     * Get the {@link #shopItem}
     * @return ShopItem
     * */
    public ShopItem getShopItem() {
        return this.shopItem;
    }

}
