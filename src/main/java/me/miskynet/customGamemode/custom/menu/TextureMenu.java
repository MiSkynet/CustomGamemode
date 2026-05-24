package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.custom.Utils;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class TextureMenu extends Menu {

    private ArrayList<Integer> interactSlots = new ArrayList<>();

    /*
    * constructor
    * */
    public TextureMenu(Component title, int size, String unicode) {
        super(Utils.component("§f\uE001" + unicode + "\uE002").append(title), size, MenuType.TEXTURE);
    }

    /*
     * setter, getter and everything else
     * */
    /**
     * Slots that the player should be able to interact with
     * every other slot will be occupied with items
     * The ArrayList has to be Index-Based
     */
    public void setInteractSlots(ArrayList<Integer> slots) {
        this.interactSlots = slots;
        super.inventory.clear();
    }

    public ArrayList<Integer> getInteractSlots() {
        return this.interactSlots;
    }

    public void openForPlayer(Player player) {

        Item item = new Item(Material.GHAST_TEAR);
        ItemStack itemStack = item.toItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setHideTooltip(true);
        CustomModelDataComponent component = itemMeta.getCustomModelDataComponent();
        component.setStrings(List.of("invisible_item"));
        itemMeta.setCustomModelDataComponent(component);
        item.setItemMeta(itemMeta);

        for (int i = 0; i < super.inventory.getSize(); i++) {
            if (interactSlots.contains(i)) continue;
            super.inventory.setItem(i, item.toItemStack());
        }

        player.openInventory(this.inventory);
    }

}
