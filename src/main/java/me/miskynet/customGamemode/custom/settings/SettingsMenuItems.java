package me.miskynet.customGamemode.custom.settings;

import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SettingsMenuItems {

    /**
     * Returns the {@link ItemStack} for the toggle scoreboard button.
     *
     * @param player The player for which the scoreboard status is checked.
     * @return The {@link ItemStack} representing the toggle scoreboard button.
     * */
    public static ItemStack getScoreboardToggleItemStack(Player player) {

        PlayerHead toggleScoreboard = new PlayerHead("4a2648a9e53613f0ab1266b72918f40788f84e160a52348cd67138410262f39f");
        toggleScoreboard.setDisplayName(ComponentUtils.component(false, "&6Click to toggle Scoreboard"));

        ArrayList<Component> toggleScoreboardLoreEnabled = new ArrayList<>();
        toggleScoreboardLoreEnabled.add(ComponentUtils.component(" "));
        toggleScoreboardLoreEnabled.add(ComponentUtils.component(false, "&7Current Status: &aEnabled"));
        ArrayList<Component> toggleScoreboardLoreDisabled = new ArrayList<>();
        toggleScoreboardLoreDisabled.add(ComponentUtils.component(" "));
        toggleScoreboardLoreDisabled.add(ComponentUtils.component(false, "&7Current Status: &cDisabled"));

        if ((Boolean) PlayerData.get(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus")) {
            toggleScoreboard.setLore(toggleScoreboardLoreEnabled);
        }else {
            toggleScoreboard.setLore(toggleScoreboardLoreDisabled);
        }

        return toggleScoreboard.toItemStack();
    }

}
