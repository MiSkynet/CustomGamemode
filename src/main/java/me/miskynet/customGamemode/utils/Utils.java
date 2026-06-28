package me.miskynet.customGamemode.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The {@link Utils} class contains helper methods for
 * a verity of classes and methods in the plugin
 * */
public class Utils {

    private static final Map<UUID, Long> clickCooldownMap = new HashMap<>();
    private static final int clickCooldown = 200;

    /**
     * Creates a {@link Component} and always unsets italic by default
     * (used to use text formation in messages)
     * @param string The string that gets converted into a Component
     * @return {@link Component}
     * */
    public static Component component(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    /**
     * Creates a component and define if the message should be italic
     * (used to use text formation in messages)
     * @param italic True if the message should be italic. False if not
     * @param string The string that gets converted into a Component
     * @return {@link Component}
     * */
    public static Component component(boolean italic, String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).decoration(TextDecoration.ITALIC, italic);
    }

    /**
     * Creates a {@link String} that has text formation and italic unset
     * @param string String that should be formated
     * @return Formated {@link String}
     * */
    public static String coloredString(String string) {
        Component component = component(string);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    /**
     * Creates a {@link String} that has text formation and define if the
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
     * Converts a {@link Component} into a {@link String} with text formation
     * @param component The {@link Component} that should be converted into a string
     * @return {@link Component} as {@link String}
     * */
    public static String fromComponent(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    /**
     * Checks if a player is able to receive an {@link ItemStack}. If yes,
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
     * Creates a new click cooldown. This prevents players from
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
            if (currentTime - lastClick < clickCooldown) {
                return false;
            }
            clickCooldownMap.remove(player.getUniqueId());
        }
        return true;
    }


    public static Object getPDCOfEntity(Entity entity, NamespacedKey namespacedKey, PersistentDataType persistentDataType) {
        return entity.getPersistentDataContainer().get(namespacedKey, persistentDataType);
    }

    public static Object getPDCOfItem(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, persistentDataType);
    }

}
