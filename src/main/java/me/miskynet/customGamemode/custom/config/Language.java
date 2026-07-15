package me.miskynet.customGamemode.custom.config;

import me.miskynet.customGamemode.Main;

import java.util.Collections;
import java.util.List;

public class Language {

    // getting the language from the config.yml, if it doesn't exist, default to en_US.yml
    private final String currentLang;

    public Language() {
        if (!CustomConfig.checkForExistence("lang/en_US.yml")) {
            CustomConfig.setup("lang/en_US.yml");
            CustomConfig.save("lang/en_US.yml");
        }
        String lang = Main.getInstance().getConfig().getString("lang");
        this.currentLang = (lang != null) ? lang : "en_US.yml";
    }

    /**
     * Gets a string from the language file
     *
     * @param key The key of the string to get
     * @return The {@link String} if it exists, else return a string saying the key is missing
     * */
    public String getString(String key) {
        String string = CustomConfig.get("lang/" + currentLang).getString(key);
        if (string == null) {
            return "&CMissing translation for key: " + key;
        }
        return CustomConfig.get("lang/" + currentLang).getString(key);
    }

    /**
     * Gets a list of strings from the language file
     *
     * @param key The key of the list of strings to get
     * @return The {@link List} of {@link String} if it exists, else return a string saying the key is missing
     * */
    public List<String> getStringList(String key) {
        List<String> list = CustomConfig.get("lang/" + currentLang).getStringList(key);
        if (list.isEmpty() || list == null) {
            return Collections.singletonList("&CMissing translation for key: " + key);
        }
        return CustomConfig.get("lang/" + currentLang).getStringList(key);
    }

    /**
     * Gets an object from the language file
     *
     * @param key The key of the object to get
     * @return The {@link Object} if it exists, else return a string saying the key is missing
     * */
    public Object get(String key) {
        Object value = CustomConfig.get("lang/" + currentLang).get(key);
        if (value == null) {
            return "&CMissing translation for key: " + key;
        }
        return CustomConfig.get("lang/" + currentLang).get(key);
    }

}
