package me.miskynet.customGamemode.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

    /**
     * Always enables italic by default
     * */
    public static Component component(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    /**
     * Define if the message should be italic
     * */
    public static Component component(boolean italic, String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).decoration(TextDecoration.ITALIC, italic);
    }

    public static void giveItemOrDrop(Player player, ItemStack itemStack) {

        var giveItem = player.getInventory().addItem(itemStack);

        if (!giveItem.isEmpty()) {
            Location location = player.getLocation();
            Bukkit.getWorld(location.getWorld().getKey()).dropItem(location, itemStack);
        }
    }

}
