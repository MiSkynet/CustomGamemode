package me.miskynet.customGamemode.utils;

import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
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

    private static final Map<UUID, Long> clickCooldown = new HashMap<>();

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

    public static String coloredString(String string) {
        Component component = component(string);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static String coloredString(boolean italic, String string) {
        Component component = component(italic, string);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static void giveItemOrDrop(Player player, ItemStack itemStack) {

        var giveItem = player.getInventory().addItem(itemStack);

        if (!giveItem.isEmpty()) {
            Location location = player.getLocation();
            Bukkit.getWorld(location.getWorld().getKey()).dropItem(location, itemStack);
        }
    }

    public static void setupDefaultPlayerSettings(Player player) {
        PlayerSettings.set(player, "settings.scoreboardStatus", true);
    }

    /*
    public static Boolean createClickCooldown(Player player) {
        long currentTime = System.currentTimeMillis();

        if (clickCooldown.containsKey(player.getUniqueId())) {

            long lastClick = clickCooldown.get(player.getUniqueId());
            if (currentTime - lastClick < 200) {
                return true;
            }
        }

        clickCooldown.put(player.getUniqueId(), currentTime);
    }
    */


}
