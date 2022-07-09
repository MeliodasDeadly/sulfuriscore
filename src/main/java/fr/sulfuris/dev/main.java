package fr.sulfuris.dev;


import fr.sulfuris.dev.config.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class main extends JavaPlugin {

    public static FileConfiguration config;

    public main() {
        this.config = this.getConfig();
    }

    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, Utils.chat("&aReload Config"));
        createConfig();
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Items"));
        itemconfig.setConfig(this);
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Commands"));
        mainconfig.setConfig(this);
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Balance"));
        currencyconfig.setConfig(this);
        this.getLogger().log(Level.INFO, Utils.chat("&aMade with &c&l<3&a by D3adPlays and Méliodas"));
        this.getLogger().log(Level.INFO, Utils.chat("&aPlugin loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getOnlinePlayers().size() + "&a players online"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getPluginManager().getPlugins().length + "&a plugins loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getConfig().getConfigurationSection("").getKeys(false).size() + "&a config files loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getDescription().getCommands().size() + "&a commands loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getPluginManager().getPlugins().length + "&a listeners loaded"));
    }

    public void createConfig() {
        this.getLogger().log(Level.INFO, Utils.chat("&aConfig not found, creating one"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
