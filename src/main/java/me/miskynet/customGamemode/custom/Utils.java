package me.miskynet.customGamemode.custom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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

}
