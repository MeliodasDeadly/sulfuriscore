package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.Main;
import fr.sulfuris.dev.vehicles.infrastructure.annotations.VersionSpecific;
import fr.sulfuris.dev.vehicles.infrastructure.enums.PluginVersion;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ServerVersion;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Logger;

/**
 * Module containing information about the plugin and server version
 */
public class VersionModule {
    /**
     * The plugin's version as String (e.g. '2.4.2')
     */
    public static String pluginVersionString;
    /**
     * The plugin's version as enum
     *
     * @see PluginVersion
     */
    public static PluginVersion pluginVersion;
    /**
     * True if the plugin is a pre-release, release candidate or a dev-version
     */
    public static boolean isPreRelease;
    /**
     * The server's software (e.g. 'Paper')
     */
    public static String serverSoftware;
    private static @Getter
    @Setter
    VersionModule instance;
    /**
     * The server's minecraft version (e.g. '1_16_R3')
     */
    private static String serverVersion;
    private Logger logger = Main.instance.getLogger();

    public VersionModule() {
        PluginDescriptionFile pdf = Main.instance.getDescription();
        pluginVersionString = pdf.getVersion();
        pluginVersion = PluginVersion.getPluginVersion();

        //Pre-releases should thus be named "vX.Y.Z-preU" etc... (Instead of pre, dev for developing and rc for release candidates are acceptable too.)
        isPreRelease = pluginVersionString.toLowerCase().contains("pre") || pluginVersionString.toLowerCase().contains("rc") || pluginVersionString.toLowerCase().contains("dev");

        serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        serverSoftware = Bukkit.getName();
    }

    /**
     * Get the server version as enum
     *
     * @return Server version
     */
    @VersionSpecific
    public static ServerVersion getServerVersion() {
        return ServerVersion.v1_16;
    }

    /**
     * Check whether the server version is supported by the plugin.
     * Otherwise, send a warning and disable the plugin.
     *
     * @return True if the server version is supported
     */
    @VersionSpecific
    public boolean isSupportedVersion() {
        if (getServerVersion() == null) {
            logger.severe("--------------------------");
            logger.severe("Your Server version is not supported. The plugin will NOT load.");
            logger.severe("Check the supported versions here: https://wiki.mtvehicles.eu/faq.html");
            logger.severe("--------------------------");
            Main.disablePlugin();
            return false;
        } else if (!Bukkit.getVersion().contains("1.12.2") && !Bukkit.getVersion().contains("1.13.2") && !Bukkit.getVersion().contains("1.15.2") && !Bukkit.getVersion().contains("1.16.5") && !Bukkit.getVersion().contains("1.17.1") && !Bukkit.getVersion().contains("1.18.2") && !Bukkit.getVersion().contains("1.19")) {
            logger.warning("--------------------------");
            logger.warning("Your Server does not run the latest patch version (e.g. you may be running 1.18 instead of 1.18.2 etc...).");
            logger.warning("The plugin WILL load but it MAY NOT work properly. UPDATE.");
            logger.warning("Check the supported versions here: https://wiki.mtvehicles.eu/faq.html");
            logger.warning("--------------------------");
        } else if (!serverSoftware.equals("Spigot") && !serverSoftware.equals("Paper") && !serverSoftware.equals("CraftBukkit")) {
            logger.warning("--------------------------");
            logger.warning("Your Server is not running Spigot, nor Paper (" + serverSoftware + " detected).");
            logger.warning("The plugin WILL load but it MAY NOT work properly. Full support is guaranteed only on Spigot/Paper.");
            logger.warning("We'll be more than happy to help you on our Discord server (https://discord.gg/vehicle).");
            logger.warning("--------------------------");
        }

        return true;
    }
}
