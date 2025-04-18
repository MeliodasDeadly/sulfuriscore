package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.*;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfigModule {
    public static List<Config> configList = new ArrayList<>();
    public static SecretSettingsConfig secretSettings = new SecretSettingsConfig();
    public static MessagesConfig messagesConfig = new MessagesConfig();
    public static VehicleDataConfig vehicleDataConfig = new VehicleDataConfig();
    public static VehiclesConfig vehiclesConfig = new VehiclesConfig();
    public static DefaultConfig defaultConfig = new DefaultConfig();
    private static @Getter
    @Setter
    ConfigModule instance;

    public ConfigModule() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss");
        Date date = new Date();

        String configVersion = main.configVersion;
        String messagesVersion = main.messagesVersion;

        final boolean oldConfigVersion = !secretSettings.getConfigVersion().equals(configVersion) || defaultConfig.hasOldVersionChecking();
        final boolean oldMessagesVersion = !secretSettings.getMessagesVersion().equals(messagesVersion) || defaultConfig.hasOldVersionChecking();

        if (oldConfigVersion) {
            File dc = new File(main.instance.getDataFolder(), "config.yml");
            File vc = new File(main.instance.getDataFolder(), "vehicles.yml");
            File sss = new File(main.instance.getDataFolder(), "supersecretsettings.yml");
            dc.renameTo(new File(main.instance.getDataFolder(), "configOld_" + formatter.format(date) + ".yml"));
            vc.renameTo(new File(main.instance.getDataFolder(), "vehiclesOld_" + formatter.format(date) + ".yml"));
            sss.delete();
            main.instance.saveDefaultConfig();
        }

        if (oldMessagesVersion) {
            File sss = new File(main.instance.getDataFolder(), "supersecretsettings.yml");
            sss.delete();
            messagesConfig.saveNewLanguageFiles(formatter.format(date));
        }

        configList.add(secretSettings);
        configList.add(messagesConfig);
        configList.add(vehicleDataConfig);
        configList.add(vehiclesConfig);
        configList.add(defaultConfig);
        reloadConfigs();
    }

    public static void reloadConfigs() {
        configList.forEach(Config::reload);
        if (!messagesConfig.setLanguageFile(secretSettings.getMessagesLanguage())) {
            main.instance.getLogger().severe("Messages.yml for your desired language could not be found. Disabling the plugin...");
            main.disablePlugin();
        }
    }
}
