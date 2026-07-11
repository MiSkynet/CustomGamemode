package me.miskynet.customGamemode.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentManager {

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
     * Convert a {@link Component} into a {@link String} with text formation
     * @param component The {@link Component} that should be converted into a string
     * @return {@link Component} as {@link String}
     * */
    public static String fromComponent(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

}
