package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Language;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

/**
 * Methods for supersecretsettings.yml.<br>
 * <b>Do not initialise this class directly. Use {@link ConfigModule#secretSettings} instead.</b>
 */
public class SecretSettingsConfig extends Config {

    /**
     * Default constructor - <b>do not use this.</b><br>
     * Use {@link ConfigModule#secretSettings} instead.
     */
    public SecretSettingsConfig() {
        super(ConfigType.SUPERSECRETSETTINGS);
    }

    /**
     * Get config version.
     *
     * @see main#configVersion
     */
    public String getConfigVersion() {
        return this.getConfiguration().getString("configVersion");
    }

    /**
     * Get message files version.
     *
     * @see main#messagesVersion
     */
    public String getMessagesVersion() {
        return this.getConfiguration().getString("messagesVersion");
    }

    /**
     * Get language used by the plugin.
     *
     * @see Language
     */
    public String getMessagesLanguage() {
        return this.getConfiguration().getString("messagesLanguage");
    }

    /**
     * Set language used by the plugin
     *
     * @param language New language
     * @throws IllegalArgumentException If language is specified as CUSTOM - custom language may only be set manually, not via this method.
     */
    public void setMessagesLanguage(Language language) throws IllegalArgumentException {
        if (language == Language.CUSTOM)
            throw new IllegalArgumentException("CUSTOM language can't be used in this method.");

        String languageCode = language.getLanguageCode();
        this.getConfiguration().set("messagesLanguage", languageCode);
        this.save();
    }
}
