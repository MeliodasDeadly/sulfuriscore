package fr.sulfuris.dev.vehicles.infrastructure.enums;

import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Languages which are offered by the plugin
 */
public enum Language {
    /**
     * English
     */
    EN("English"),
    /**
     * Dutch
     */
    NL("Nederlands"),
    /**
     * Spanish
     */
    ES("Español"),
    /**
     * Czech
     */
    CS("Čeština"),
    /**
     * Chinese
     */
    CN("中國人"),
    /**
     * German
     */
    DE("Deutsch"),
    /**
     * Turkish
     */
    TR("Türk"),
    /**
     * Japanese
     */
    JA("日本語"),
    /**
     * Hebrew
     */
    HE("עִברִית"),
    /**
     * Custom language one can create and use
     */
    CUSTOM("Custom language");

    final private String languageName;

    private Language(String languageName) {
        this.languageName = languageName;
    }

    /**
     * Get an array of all languages (in their country codes)
     *
     * @return Array of all supported languages
     */
    public static String[] getAllLanguages() {
        String[] languages = {"en", "nl", "es", "cs", "cn", "de", "tr", "ja", "he"};
        return languages;
    }

    /**
     * Check whether the language is supported
     *
     * @param languageCode Language code (e.g. "en")
     * @return Whether the language is supported
     */
    public static boolean isSupported(String languageCode) {
        List<String> languages = new ArrayList<>(Arrays.asList(getAllLanguages()));
        return languages.contains(languageCode.toLowerCase(Locale.ROOT));
    }

    /**
     * Get the language name
     *
     * @return Language name (in its own language)
     */
    public String getLanguageName() {
        return this.languageName;
    }

    /**
     * Get the language code
     *
     * @return Language code (e.g. "en")
     */
    public String getLanguageCode() {
        if (this.equals(CUSTOM)) {
            return ConfigModule.secretSettings.getMessagesLanguage();
        }
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
