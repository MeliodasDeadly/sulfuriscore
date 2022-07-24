package fr.sulfuris.dev.vehicles.infrastructure.models;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

public abstract class Config {
    final protected ConfigType configType;
    protected FileConfiguration config;
    private File configFile = null;
    private String fileName;

    public Config(ConfigType configType) {
        this.configType = configType;
        if (!configType.isMessages()) this.fileName = configType.getFileName();
    }

    public void reload() {
        if (configFile == null) {
            setConfigFile(new File(main.instance.getDataFolder(), fileName));
        }
        if (!configFile.exists())
            this.saveDefaultConfig();

        config = YamlConfiguration.loadConfiguration(configFile);

        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.instance.getResource(fileName)), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
    }


    @Deprecated
    public FileConfiguration getConfig() {
        if (config == null) {
            reload();
        }
        return config;
    }

    protected FileConfiguration getConfiguration() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public boolean save() {
        if (config == null || configFile == null) {
            return false;
        }
        try {
            getConfiguration().save(configFile);
        } catch (IOException ex) {
            main.instance.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
        this.reload();
        return true;
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(main.instance.getDataFolder(), fileName);
        }
        if (!configFile.exists()) {
            main.instance.saveResource(fileName, false);
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }
}
