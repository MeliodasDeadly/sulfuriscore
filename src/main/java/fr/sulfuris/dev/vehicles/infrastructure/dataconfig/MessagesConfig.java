package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Language;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Locale;

/**
 * Methods for message files.<br>
 * <b>Do not initialise this class directly. Use {@link ConfigModule#messagesConfig} instead.</b>
 */
public class MessagesConfig extends Config {
    private Language language;

    /**
     * Default constructor which gets an appropriate file based on the plugin's language (set in SuperSecretSettings) - <b>do not use this.</b><br>
     * Use {@link ConfigModule#messagesConfig} instead.
     */
    public MessagesConfig() {
        super(ConfigType.MESSAGES);
        for (String lang : Language.getAllLanguages()) {
            saveLanguageFile(lang);
        }
        if (!setLanguageFile(ConfigModule.secretSettings.getMessagesLanguage())) {
            main.instance.getLogger().severe("Messages.yml for your desired language could not be found. Disabling the plugin...");
            main.disablePlugin();
        }
    }

    /**
     * Get a message by its key (in plugin's set language).
     *
     * @param key Key of the message - as String
     * @deprecated This may lead to issues - use {@link #getMessage(Message)} instead.
     */
    @Deprecated
    public String getMessage(String key) {
        String msg = "";
        try {
            msg = TextUtils.colorize((String) this.getConfiguration().get(key));
        } catch (Exception e) {
            main.instance.getLogger().severe("An error occurred while retrieving a custom message from the messages.yml!");
        }
        return msg;
    }

    /**
     * Get a message by its key (in plugin's set language).
     *
     * @param message Message (enum)
     */
    public String getMessage(Message message) {
        String msg = "";
        try {
            msg = TextUtils.colorize(this.getConfiguration().getString(message.getKey()));
        } catch (Exception e) {
            main.instance.getLogger().severe("An error occurred while retrieving a custom message from the messages.yml!");
        }
        return msg;
    }

    /**
     * Send a message to a player/console by its key (in plugin's set language).
     *
     * @param receiver Player/Console
     * @param key      Key of the message - as String
     * @deprecated This may lead to issues - use {@link #sendMessage(CommandSender, Message)} instead.
     */
    @Deprecated
    public void sendMessage(CommandSender receiver, String key) {
        receiver.sendMessage(getMessage(key));
    }

    /**
     * Send a message to a player/console by its key (in plugin's set language).
     *
     * @param receiver Player/Console
     * @param message  Message (enum)
     */
    public void sendMessage(CommandSender receiver, Message message) {
        receiver.sendMessage(getMessage(message));
    }

    /**
     * Set this class to work with the appropriate language file
     *
     * @param languageCode 'xx' in 'messages_xx.yml'
     * @return True if successful
     */
    public boolean setLanguageFile(String languageCode) {
        String countryCode = (languageCode.equals("ns")) ? "en" : languageCode;
        this.language = (Language.isSupported(languageCode)) ? Language.valueOf(languageCode.toUpperCase(Locale.ROOT)) : Language.CUSTOM;
        String fileName = "messages/messages_" + countryCode + ".yml";
        File languageFile = new File(main.instance.getDataFolder(), fileName);
        if (!languageFile.exists()) return false;

        this.setFileName(fileName);
        this.setConfigFile(new File(main.instance.getDataFolder(), fileName));
        this.reload();
        return true;
    }

    /**
     * Save a messages file
     *
     * @param countryCode 'xx' in 'messages_xx.yml'
     */
    private void saveLanguageFile(String countryCode) {
        String fileName = "messages/messages_" + countryCode + ".yml";

        File languageFile = new File(main.instance.getDataFolder(), fileName);
        if (!languageFile.exists()) main.instance.saveResource(fileName, false);
    }

    /**
     * Save new language files (when updating from a lower version)
     *
     * @param time Old messages files will be renamed and will contain this time
     */
    public void saveNewLanguageFiles(String time) {
        for (String lang : Language.getAllLanguages()) {
            File messagesFile = new File(main.instance.getDataFolder(), "messages/messages_" + lang + ".yml");
            if (!messagesFile.exists()) continue;
            messagesFile.renameTo(new File(main.instance.getDataFolder(), "messages/messages_" + lang + "Old_" + time + ".yml"));
            main.instance.saveResource("messages/messages_" + lang + ".yml", true);
        }
    }
}
