package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Menu implements InventoryHolder {

    MenuManager menuManager = Main.menuManager;

    Inventory inventory;
    private int id;
    private MenuType menuType;

    public enum MenuType {
        DEFAULT,
        TEXTURE
    }

    /*
     * constructor
     * */
    public Menu(Component title, int size) {
        this(title, size, createRandomID(), MenuType.DEFAULT);
    }

    public Menu(Component title, int size, int id) {
        this(title, size, id, MenuType.DEFAULT);
    }

    public Menu(Component title, int size, MenuType menuType) {
        this(title, size, createRandomID(), menuType);
    }

    public Menu(Component title, int size, int id, MenuType menuType) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.id = id;
        this.menuType = menuType;
        menuManager.addMenu(this.id, this);
    }

    /*
     * setter, getter and everything else
     * */
    private static int createRandomID() {
        while (true) {
            Random random = new Random();
            int id = random.nextInt(1_000_000_000);

            if (!(Main.menuManager.getMenuMap().containsKey(id))) {
                return id;
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public Menu setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
        return this;
    }

    public Menu setItem(int slot, Item item) {
        this.inventory.setItem(slot, item.toItemStack());
        return this;
    }

    public Menu addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
        return this;
    }

    public Menu addItem(Item item) {
        this.inventory.addItem(item.toItemStack());
        return this;
    }


    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public MenuType getMenuType() {
        return this.menuType;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void openForPlayer(Player player) {
        player.openInventory(this.inventory);
    }
}
