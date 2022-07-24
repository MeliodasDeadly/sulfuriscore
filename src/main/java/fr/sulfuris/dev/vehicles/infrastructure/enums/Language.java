package fr.sulfuris.dev.vehicles.infrastructure.enums;

import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum Language {
    EN("English"),
    NL("Nederlands"),
    ES("Español"),
    CS("Čeština"),
    CN("中國人"),
    DE("Deutsch"),
    TR("Türk"),
    JA("日本語"),
    HE("עִברִית"),
    FR("Français"),
    CUSTOM("Custom language");

    final private String languageName;

    private Language(String languageName) {
        this.languageName = languageName;
    }

    public static String[] getAllLanguages() {
        String[] languages = {"en", "nl", "es", "cs", "cn", "de", "tr", "ja", "he", "fr"};
        return languages;
    }

    public static boolean isSupported(String languageCode) {
        List<String> languages = new ArrayList<>(Arrays.asList(getAllLanguages()));
        return languages.contains(languageCode.toLowerCase(Locale.ROOT));
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public String getLanguageCode() {
        if (this.equals(CUSTOM)) {
            return ConfigModule.secretSettings.getMessagesLanguage();
        }
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
