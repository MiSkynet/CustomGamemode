package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.menu.shop.Shop;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Menu implements InventoryHolder {

    Inventory inventory;

    /**
     * Constructor to create simple menus. Listeners have to
     * be written all by yourself!
     * */
    public Menu(Component title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    /**
     * Set an {@link ItemStack} into a specific slot
     *
     * @param slot Slot where the item should be put
     * @param itemStack The {@link ItemStack} that should be put into the slot
     * */
    public Menu setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
        return this;
    }

    /**
     * Set an {@link Item} into a specific slot
     *
     * @param slot Slot where the item should be put
     * @param item The {@link Item} that should be put into the slot
     * */
    public Menu setItem(int slot, Item item) {
        this.inventory.setItem(slot, item.toItemStack());
        return this;
    }

    /**
     * Add an {@link Item} into a {@link Menu}
     *
     * @param itemStack The {@link ItemStack} that should be added to the {@link Menu}
     * */
    public Menu addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
        return this;
    }

    /**
     * Add an {@link Item} into a {@link Menu}
     *
     * @param item The {@link Item} that should be added to the {@link Menu}
     * */
    public Menu addItem(Item item) {
        this.inventory.addItem(item.toItemStack());
        return this;
    }

    /**
     * Get an {@link ItemStack} out of a menu {@link Menu}
     *
     * @param slot The slot the {@link ItemStack} should be got from
     * */
    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    /**
     * Get the inventory of the {@link Shop}
     *
     * @return Inventory of the shop
     * */
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Open the {@link Menu} for a player
     * */
    public void openForPlayer(Player player) {
        player.openInventory(this.inventory);
    }
}
