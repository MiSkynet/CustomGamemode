package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Menu implements InventoryHolder {

    Inventory inventory;

    public Menu(Component title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int slot, Item item) {
        this.inventory.setItem(slot, item.getItemStack());
    }

    public void addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
    }

    public void addItem(Item item) {
        this.inventory.addItem(item.getItemStack());
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void openForPlayer(Player player) {
        player.openInventory(this.inventory);
    }
}
