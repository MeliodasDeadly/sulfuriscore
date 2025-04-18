package fr.sulfuris.dev;


import fr.sulfuris.dev.commands.admin.InfoCommand;
import fr.sulfuris.dev.commands.admin.StoreCommand;
import fr.sulfuris.dev.commands.admin.bank.bankCommand;
import fr.sulfuris.dev.commands.admin.job.setjob;
import fr.sulfuris.dev.commands.admin.keypad.keypadCommand;
import fr.sulfuris.dev.commands.admin.money.giveCommand;
import fr.sulfuris.dev.commands.admin.money.resetCommand;
import fr.sulfuris.dev.commands.admin.money.setCommand;
import fr.sulfuris.dev.commands.admin.removeCommand;
import fr.sulfuris.dev.commands.money.GiveCommand;
import fr.sulfuris.dev.commands.money.SetCommand;
import fr.sulfuris.dev.commands.other.SitCommand;
import fr.sulfuris.dev.commands.shop.KeyCommand;
import fr.sulfuris.dev.commands.shop.PackageCommand;
import fr.sulfuris.dev.config.currencyconfig;
import fr.sulfuris.dev.config.itemconfig;
import fr.sulfuris.dev.config.listenerconfig;
import fr.sulfuris.dev.config.mainconfig;
import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.gui.atmgui;
import fr.sulfuris.dev.listener.DismountEvent;
import fr.sulfuris.dev.listener.Joinlistener;
import fr.sulfuris.dev.listener.deathlistener;
import fr.sulfuris.dev.scoreboard.mainscoreboard;
import fr.sulfuris.dev.vehicles.infrastructure.modules.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

import static fr.sulfuris.dev.data.DatabaseUser.dbSetup;


public final class main extends JavaPlugin {

    public static FileConfiguration config;
    final public static String configVersion = "2.4.0";
    final public static String messagesVersion = "2.4.3-dev6";
    public static Plugin instance;
    private static main plugin;

    public main() {
        this.config = this.getConfig();
    }

    public static main getPlugin() {
        return plugin;
    }

    public static String getFileAsString() {
        return String.valueOf(plugin.getFile());
    }

    public void createConfig() {
        this.getLogger().log(Level.INFO, Utils.chat("&aConfig not found, creating one"));

        itemconfig.setConfig(this);
        mainconfig.setConfig(this);
        currencyconfig.setConfig(this);
        listenerconfig.setConfig(this);
        this.config.options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
        this.getLogger().log(Level.INFO, Utils.chat("&aConfig reloaded"));
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, Utils.chat("&aPlugin unloaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&a Goodbye &c&l<3"));
    }

    public static void disablePlugin() {
        logSevere("Disabling the plugin (a critical bug might have occurred)...");
        plugin.setEnabled(false);
    }

    public static void logInfo(String text) {
        plugin.getLogger().info(text);
    }

    public static void logWarning(String text) {
        plugin.getLogger().warning(text);
    }

    public static void logSevere(String text) {
        plugin.getLogger().severe(text);
    }

    public static void schedulerRun(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    @Override
    public void onEnable() {
        plugin = this;
        instance = this;
        this.getLogger().log(Level.INFO, Utils.chat("----------------------------------------------"));
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Items"));

        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Main Config"));

        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Balance"));

        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Listeners"));
        new deathlistener(this);
        new Joinlistener(this);
        new atmgui(this);

        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Database"));
        new Thread(){
            public void run(){
                try {
                    dbSetup();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Commands"));

        new StoreCommand(this);
        new InfoCommand(this);
        new PackageCommand(this);
        new KeyCommand(this);

        new GiveCommand(this);
        new SetCommand(this);
        new bankCommand(this);
        new removeCommand(this);
        new setjob(this);
        new giveCommand(this);
        new resetCommand(this);
        new setCommand(this);
        new Joinlistener(this);
        new atmgui(this);
        new SitCommand(this);
        new DismountEvent(this);
        new keypadCommand(this);

        new CommandModule();
        new ListenersModule();
        new MetricsModule();
        new LoopModule();
        new ConfigModule();


        this.getLogger().log(Level.INFO, Utils.chat("&aReload Config"));
        this.createConfig();
        this.getLogger().log(Level.INFO, Utils.chat("&aLoading Scoreboard"));

        this.getLogger().log(Level.INFO, Utils.chat("&aMade with &c&l<3&a by D3adPlays and Meliodas"));
        this.getLogger().log(Level.INFO, Utils.chat("&aPlugin loaded"));


        this.getLogger().log(Level.INFO, Utils.chat("----------------------------------------------"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getOnlinePlayers().size() + "&a players online"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getPluginManager().getPlugins().length + "&a plugins loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getConfig().getConfigurationSection("").getKeys(false).size() + "&a config lines loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getDescription().getCommands().size() + "&a commands loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("&aThere are &c&l" + this.getServer().getPluginManager().getPlugins().length + "&a listeners loaded"));
        this.getLogger().log(Level.INFO, Utils.chat("----------------------------------------------"));

    }

    @Override
    public void onLoad() {
        new DependencyModule();
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

}
