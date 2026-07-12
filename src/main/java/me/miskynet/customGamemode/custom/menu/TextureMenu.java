package me.miskynet.customGamemode.custom.menu;

import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link TextureMenu} is a type of {@link Menu} that can have a custom GUI design.
 * The design is applied by using Unicodes of a texture pack
 * */
public abstract class TextureMenu extends Menu {

    private ArrayList<Integer> interactSlots = new ArrayList<>();
    private String unicode;

    /**
     * @param title The title of the {@link TextureMenu}
     * @param size The size of the {@link TextureMenu} (A multiple of 9 and max 54)
     * @param unicode The Unicode of the GUI in the Resource Pack
     * */
    public TextureMenu(Component title, int size, String unicode) {
        super(ComponentUtils.component("§f\uE001" + unicode + "\uE002").append(title), size);
        this.unicode = unicode;
    }

    /**
     * Builds the menu
     * */
    public void buildMenu() {
        fillEmptySlots();
    }

    /**
     * Slots that the player should be able to interact with
     * every other slot will be occupied with items
     * The ArrayList has to be Index-Based
     *
     * @param slots The slots as an ArrayList
     * @return This {@link TextureMenu}
     */
    public TextureMenu setInteractSlots(ArrayList<Integer> slots) {
        this.interactSlots = slots;
        super.getInventory().clear();
        return this;
    }

    /**
     * Slots that the player should be able to interact with
     * every other slot will be occupied with items
     *
     * @param slots The slots as a variant
     * @return This {@link TextureMenu}
     */
    public TextureMenu setInteractSlots(int... slots) {
        for (int slot : slots) {
            interactSlots.add(slot);
        }
        super.getInventory().clear();
        return this;
    }

    /**
     * Defines slots that the user can interact with.
     * in this slot, the user can also put items in!
     * To define buttons, don't use this function and instead hard code the
     * buttons in the event listener
     *
     * @return Interact slots as ArrayList
     * */
    public ArrayList<Integer> getInteractSlots() {
        return this.interactSlots;
    }

    /**
     * The unicode is used to identify a specific TextureMenu
     *
     * @return Unicode as String
     * */
    public String getUnicode() {
        return this.unicode;
    }

    /**
     * This function sets every non-interact slot and every empty slot to an invisible
     * item
     * Useful for shift clicks to block them by default!
     * */
    public void fillEmptySlots() {

        Item item = new Item(Material.GHAST_TEAR);
        ItemStack itemStack = item.toItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setHideTooltip(true);
        CustomModelDataComponent component = itemMeta.getCustomModelDataComponent();
        component.setStrings(List.of("invisible_item"));
        itemMeta.setCustomModelDataComponent(component);
        item.setItemMeta(itemMeta);

        for (int i = 0; i < super.getInventory().getSize(); i++) {
            if (interactSlots.contains(i)) continue;
            if (getInventory().getItem(i) == null || getInventory().getItem(i).getType().equals(Material.AIR)) super.getInventory().setItem(i, item.toItemStack());
        }
    }

}
