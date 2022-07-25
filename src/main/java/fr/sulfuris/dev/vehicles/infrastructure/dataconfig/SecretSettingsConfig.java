package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Language;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;

public class SecretSettingsConfig extends Config {

        public SecretSettingsConfig() {
            super(ConfigType.SUPERSECRETSETTINGS);
        }

    public String getConfigVersion() {
        return this.getConfiguration().getString("configVersion");
    }

    public String getMessagesVersion() {
        return this.getConfiguration().getString("messagesVersion");
    }

    public String getMessagesLanguage() {
        return this.getConfiguration().getString("messagesLanguage");
    }

    public void setMessagesLanguage(Language language) throws IllegalArgumentException {
        if (language == Language.CUSTOM)
            throw new IllegalArgumentException("CUSTOM language can't be used in this method.");

        String languageCode = language.getLanguageCode();
        this.getConfiguration().set("messagesLanguage", languageCode);
        this.save();
    }
}
