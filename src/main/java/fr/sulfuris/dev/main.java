package fr.sulfuris.dev;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import fr.sulfuris.dev.commands.*;
import fr.sulfuris.dev.items.*;
import java.util.logging.Level;
import fr.sulfuris.dev.config.*;


public final class main extends JavaPlugin {

    public static FileConfiguration config;
    public main() {
        this.config = this.getConfig();
    }
    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, Utils.chat("&aMade with &c&l<3&a by D3adPlays and Méliodas"));
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Config"));
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Commands"));
        createConfig();

        currencyconfig.setConfig(this);

    }

    public void createConfig() {
        this.getLogger().log(Level.INFO, Utils.chat("&aConfig not found, creating one"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
