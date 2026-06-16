package me.miskynet.customGamemode.custom.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.List;

/**
 * Helps create and modifying an ItemStack easier by creating an Item object.
 * */
public class Item {

    private Material material;
    private Component displayName;
    private List<Component> lore;
    private Integer amount;
    private CustomModelDataComponent customModelDataComponent;
    private List<Enchantment> enchantments;
    private boolean enchantmentGlint = false;
    private ItemMeta itemMeta = null;

    /*
    * Different constructors
    * obv reasons
    * */
    public Item(Material material) {
        this(material, null, null, 1, null);
    }

    public Item(Material material, Component displayName) {
        this(material, displayName, null, 1, null);
    }

    public Item(Material material, Component displayName, List<Component> lore) {
        this(material, displayName, lore, 1, null);
    }

    public Item(Material material, Component displayName, List<Component> lore, int amount) {
        this(material, displayName, lore, amount, null);
    }

    public Item(Material material, Component displayName, List<Component> lore, int amount, CustomModelDataComponent customModelDataComponent) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.amount = amount;
        this.customModelDataComponent = customModelDataComponent;
    }

    /*
    * setter and getter
    * */
    public Item setMaterial(Material material) {
        this.material = material;
        return this;
    }
    public Material getMaterial() {
        return this.material;
    }

    public Item setDisplayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }
    public Component getDisplayName() {
        return this.displayName;
    }

    public Item setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }
    public List<Component> getLore() {
        return this.lore;
    }

    public Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    public Integer getAmount() {
        return this.amount;
    }

    public Item setCustomModelData(CustomModelDataComponent customModelDataComponent) {
        this.customModelDataComponent = customModelDataComponent;
        return this;
    }
    public CustomModelDataComponent getCustomModelDataComponent() {
        return this.customModelDataComponent;
    }

    public Item addEnchantment(Enchantment enchantment) {
        this.enchantments.add(enchantment);
        return this;
    }
    public List<Enchantment> getEnchantments() {
        return this.enchantments;
    }

    public Item setEnchantmentGlint(Boolean enchantmentGlint) {
        this.enchantmentGlint = enchantmentGlint;
        return this;
    }

    public Item setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Builds the Item into an ItemStack
     * @return Item as a ItemStack
     */
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = this.itemMeta;

        if (this.itemMeta == null) {
            itemMeta = itemStack.getItemMeta();
        }

        if (itemMeta != null) {
            if (this.displayName != null) itemMeta.displayName(this.displayName);
            if (this.lore != null) itemMeta.lore(this.lore);
            if (this.customModelDataComponent != null) itemMeta.setCustomModelDataComponent(this.customModelDataComponent);

            itemMeta.setEnchantmentGlintOverride(this.enchantmentGlint);

            itemStack.setItemMeta(itemMeta);
        }

        if (this.amount != null) itemStack.setAmount(this.amount);

        return itemStack;
    }

    /**
     * Convert an ItemStack into an Item. It is possible that
     * data is lost by converting since an Item doesn't support
     * every Paper ItemStack attributes
     * @return Item as an Item-Type
     * */
    public Item parseData(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            if (this.displayName != null) this.displayName = itemMeta.displayName();
            if (this.lore != null) itemMeta.lore();
            if (this.customModelDataComponent != null) itemMeta.setCustomModelDataComponent(this.customModelDataComponent);
        }

        return this;
    }

}
