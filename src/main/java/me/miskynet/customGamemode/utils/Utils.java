package me.miskynet.customGamemode.utils;

import me.miskynet.customGamemode.utils.customConfig.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {

    private static final Map<UUID, Long> clickCooldownMap = new HashMap<>();
    private static final int clickCooldown = 200;

    /**
     * Create a {@link Component} and always unsets italic by default
     * (used to use text formation in messages)
     * @param string The string that gets converted into a Component
     * @return {@link Component}
     * */
    public static Component component(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    /**
     * Create a component and define if the message should be italic
     * (used to use text formation in messages)
     * @param italic True if the message should be italic. False if not
     * @param string The string that gets converted into a Component
     * @return {@link Component}
     * */
    public static Component component(boolean italic, String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).decoration(TextDecoration.ITALIC, italic);
    }

    /**
     * Create a {@link String} that has text formation and italic unset
     * @param string String that should be formated
     * @return Formated {@link String}
     * */
    public static String coloredString(String string) {
        Component component = component(string);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    /**
     * Create a {@link String} that has text formation and define if the
     * message should be italic
     * @param italic True if the message should be italic. False if not
     * @param string String that should be formated
     * @return Formated {@link String}
     * */
    public static String coloredString(boolean italic, String string) {
        Component component = component(italic, string);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    /**
     * Check if a player is able to receive an {@link ItemStack}. If yes,
     * give it to the player. Else drop it on the players position
     * @param player Player that should be checked
     * @param itemStack The item stack that should be given or dropped
     * */
    public static void giveItemOrDrop(Player player, ItemStack itemStack) {

        var giveItem = player.getInventory().addItem(itemStack);

        if (!giveItem.isEmpty()) {
            Location location = player.getLocation();
            Bukkit.getWorld(location.getWorld().getKey()).dropItem(location, itemStack);
        }
    }

    /**
     * Set the default player settings for a specific player
     * @param player The player the settings should be set for to default
     * */
    public static void setupDefaultPlayerSettings(Player player) {
        PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", true);
    }

    /**
     * Create a new click cooldown. This prevents players from
     * clicking again in an inventory
     * @param player The player that the cooldown should be applied on
     * */
    public static void createClickCooldown(Player player) {
        long currentTime = System.currentTimeMillis();
        clickCooldownMap.put(player.getUniqueId(), currentTime);
    }

    /**
     * Checks if a player has an active cooldown
     * @param player The player that should be checked
     * @return True when the {@link Player} can click, false when not
     * */
    public static Boolean checkForAllowedClick(Player player) {
        long currentTime = System.currentTimeMillis();

        if (clickCooldownMap.containsKey(player.getUniqueId())) {
            long lastClick = clickCooldownMap.get(player.getUniqueId());
            if (currentTime - lastClick < 200) {
                return false;
            }
            clickCooldownMap.remove(player.getUniqueId());
        }
        return true;
    }



}
