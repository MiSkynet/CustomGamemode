package me.miskynet.customGamemode.custom.config;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Debugger;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.eclipse.sisu.inject.DefaultBeanLocator;

import java.io.File;
import java.lang.classfile.constantpool.DoubleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language {

    // getting the language from the config.yml, if it doesn't exist, default to en_US.yml
    private String currentLang;

    public ArrayList<String> availableLanguages = new ArrayList<>();

    public Language() {
        loadLanguage();
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
            return getMissingTranslationMessage(key);
        }

        return findMatch(string);
    }

    /**
     * Finds and replaces any <strong>additionalMessage:KEY</strong> in the string with the corresponding value from the language file
     *
     * @param string The string to search for matches
     * @returns The string with the matches replaced
     * */
    private String findMatch(String string) {

        Pattern pattern = Pattern.compile("<additionalMessage:(.+?)>");
        Matcher matcher = pattern.matcher(string);

        StringBuilder stringBuilder = new StringBuilder();

        while (matcher.find()) {
            String configKey = matcher.group(1);

            String additionalText = CustomConfig.get("lang/" + currentLang).getString(configKey);

            matcher.appendReplacement(stringBuilder, additionalText);
        }

        matcher.appendTail(stringBuilder);

        return stringBuilder.toString();
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
            return Collections.singletonList(getMissingTranslationMessage(key));
        }

        List<String> replacedList = new ArrayList<>();
        for (String string : list) {
            replacedList.add(findMatch(string));
        }

        return replacedList;
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
            return getMissingTranslationMessage(key);
        }
        return value;
    }

    /**
     * Reloads the language files from the lang folder
     * */
    public void reload() {
        loadLanguage();
    }

    /**
     * Try to load the language files from the lang folder, if it doesn't exist, load the default languages
     * */
    private void loadLanguage() {

        File langFolder = new File(Main.getInstance().getDataFolder(), "lang/");

        if (!langFolder.exists()) {
            loadDefaultLanguages();
            return;
        }

        File[] files = langFolder.listFiles();

        // load non-default languages
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                availableLanguages.add(file.getName());
            }
        }

        String lang = Main.getInstance().getConfig().getString("lang");
        this.currentLang = (lang != null && checkForValidLanguageFile(lang)) ? lang : "en_US.yml";
    }

    /**
     * Check if a language file exists in the lang folder
     *
     * @param filePath The path to the language file
     * @return true if the file exists, false otherwise
     * */
    private boolean checkForValidLanguageFile(String filePath) {
        File file = new File(Main.getInstance().getDataFolder(), "lang/" + filePath);
        if (file.exists()) return true;
        Bukkit.getLogger().warning("[CustomGamemode] Language file '" + filePath + "' does not exist in the lang folder! Defaulting to 'en_US.yml'");
        return false;
    }

    /**
     * Load the default languages if they don't exist
     * */
    private void loadDefaultLanguages() {
        // english
        CustomConfig.setup("lang/en_US.yml");
        CustomConfig.save("lang/en_US.yml");
        availableLanguages.add("en_US.yml");

        String lang = Main.getInstance().getConfig().getString("lang");
        this.currentLang = (lang != null && checkForValidLanguageFile(lang)) ? lang : "en_US.yml";
    }

    /**
     * Gets a message that the translation for the key is missing
     *
     * @param key The key that is missing
     * @return The message that the translation is missing
     * */
    private String getMissingTranslationMessage(String key) {
        return "&cMissing translation for key: " + key + " (lang file: " + currentLang + ")";
    }


}
