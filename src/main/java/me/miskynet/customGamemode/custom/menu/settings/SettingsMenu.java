package me.miskynet.customGamemode.custom.menu.settings;

import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.awt.*;
import java.util.ArrayList;


/**
 * The {@link SettingsMenu} is a type of {@link TextureMenu}. So it will have a custom texture.
 * This {@link Menu} will contain all buttons to toggle settings
 * */
public class SettingsMenu extends TextureMenu implements InventoryHolder {

    Inventory inventory;

    public SettingsMenu() {
        super(Utils.component("Settings"), 36, "\uE004");
        this.inventory = super.getInventory();
    }

    /**
     * Build the settings page
     *
     * @param player Player that has opened the inventory
     * */
    public void buildSettingsPage(Player player) {

        PlayerHead toggleScoreboard = new PlayerHead("4a2648a9e53613f0ab1266b72918f40788f84e160a52348cd67138410262f39f");
        toggleScoreboard.setDisplayName(Utils.component(false, "&6Toggle Scoreboard"));

        ArrayList<Component> toggleScoreboardLoreEnabled = new ArrayList<>();
        toggleScoreboardLoreEnabled.add(Utils.component(false, "&7Current Status: &aEnabled"));
        toggleScoreboardLoreEnabled.add(Utils.component(false, "&8Click to disable scoreboard"));
        ArrayList<Component> toggleScoreboardLoreDisabled = new ArrayList<>();
        toggleScoreboardLoreDisabled.add(Utils.component(false, "&7Current Status: &cDisabled"));
        toggleScoreboardLoreDisabled.add(Utils.component(false, "&8Click to enable scoreboard"));


        if ((Boolean) PlayerSettings.get(player, "settings.scoreboardStatus")) {
            toggleScoreboard.setLore(toggleScoreboardLoreEnabled);
        }else {
            toggleScoreboard.setLore(toggleScoreboardLoreDisabled);
        }

        super.getInventory().setItem(10, toggleScoreboard.toItemStack());
    }

}
