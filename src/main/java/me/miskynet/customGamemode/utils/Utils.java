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

    /**
     * Formats an enum constant to a more readable string
     *
     * @param string The enum constant to format
     * @return A formatted string
     */
    /*
    * Coded by AI c:
    * */
    public static String formatEnumToString(String string) {

        String lowerCase = string.toLowerCase().replace('_', ' ');

        String[] words = lowerCase.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }
}
