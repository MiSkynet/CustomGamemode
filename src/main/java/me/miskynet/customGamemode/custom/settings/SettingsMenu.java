package me.miskynet.customGamemode.custom.settings;

import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.custom.config.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;


/**
 * The {@link SettingsMenu} is a type of {@link TextureMenu}. So it will have a custom texture.
 * This {@link Menu} will contain all buttons to toggle settings
 * */
public class SettingsMenu extends TextureMenu implements InventoryHolder {

    Inventory inventory;

    /**
     * Then settings menu is a type of {@link TextureMenu}. It contains all the settings of a player
     * */
    public SettingsMenu() {
        super(ComponentUtils.component("Settings"), 36, "\uE004");
        this.inventory = super.getInventory();
    }

    /**
     * Builds the settings page
     *
     * @param player Player that has opened the inventory
     * */
    public void buildMenu(Player player) {
        super.getInventory().setItem(10, SettingsMenuItems.getScoreboardToggleItemStack(player));
    }

}
