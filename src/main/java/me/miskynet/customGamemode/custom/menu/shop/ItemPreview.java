package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.item.shop.ItemPreviewItem;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;

public class ItemPreview extends TextureMenu {

    private ShopItem shopItem;
    private Integer lastPage;

    /**
     * The {@link TextureMenu} is a type of {@link me.miskynet.customGamemode.custom.menu.Menu} that can have a custom GUI design.
     * The design is applied by using Unicodes of a texture pack
     *
     * @param title   The title of the {@link TextureMenu}
     * @param size    The size of the {@link TextureMenu} (A multiple of 9 and max 54)
     * @param unicode The Unicode of the GUI in the Resource Pack
     */
    public ItemPreview(Component title, int size, String unicode, int lastPage, ShopItem shopItem) {
        super(title, size, unicode);
        this.shopItem = shopItem;
        this.lastPage = lastPage;
        buildInventory();
    }

    /**
     * Build the inventory for the player
     * */
    public void buildInventory() {

        PlayerHead backItem = new PlayerHead(Utils.component("&cBack"), "158a3e5617b7fc16ff436edab5996027986c584dd8837b63260577c32421bd1c");

        this.getInventory().setItem(0, backItem.toItemStack());

        this.getInventory().setItem(4, shopItem.toItemStack());

        int maxStackSize = this.shopItem.toItemStack().getMaxStackSize();

        int[] amounts = {1, 2, 4, 8, 16, 32, 64};

        for (int i = 0; i < amounts.length; i++) {
            int amount = amounts[i];

            this.getInventory().setItem(19 + i, new ItemPreviewItem(ItemPreviewItem.ItemType.BUY, amount, shopItem.getBuyPrice(), maxStackSize).toItemStack());

            this.getInventory().setItem(28 + i, new ItemPreviewItem(ItemPreviewItem.ItemType.SELL, amount, shopItem.getSellPrice(), maxStackSize).toItemStack());
        }

        this.fillEmptySlots();
    }

    /**
     * Get the {@link #shopItem}
     * @return ShopItem
     * */
    public ShopItem getShopItem() {
        return this.shopItem;
    }

    public Integer getLastPage() {
        return this.lastPage;
    }


}
