package fr.sulfuris.dev.config;

import fr.sulfuris.dev.Main;

public class mainconfig {

    public static void setConfig(Main plugin) {
        // create config if it doesn't exist
        if (!plugin.config.contains("Main")) {
            plugin.config.createSection("Main");
        }
        // Server Config
        plugin.config.addDefault("server-name", "SulfurisRP");
        plugin.config.addDefault("server-ip", "localhost");
        plugin.config.addDefault("server-port", "25565");
        plugin.config.addDefault("server-motd", "&aSulfurisRP");
        plugin.config.addDefault("server-max-players", "200");
        plugin.config.addDefault("server-allow-flight", "false");
        plugin.config.addDefault("server-allow-nether", "false");
        plugin.config.addDefault("server-allow-end", "false");
        //  Error Config
        plugin.config.addDefault("error-prefix", "&c&lError &8&l>&r ");
        plugin.config.addDefault("error-no-permission", "&cYou don't have permission to do this");
        plugin.config.addDefault("error-no-player", "&cYou must be a player to do this");
        plugin.config.addDefault("error-no-money", "&cYou don't have enough money");
        plugin.config.addDefault("error-no-item", "&cYou don't have this item");
        plugin.config.addDefault("error-no-item-in-inventory", "&cYou don't have this item in your inventory");
        plugin.config.addDefault("error-no-item-in-hand", "&cYou don't have this item in your hand");
        plugin.config.addDefault("error-no-item-in-inventory-hand", "&cYou don't have this item in your inventory or in your hand");
        plugin.config.addDefault("error-no-item-in-inventory-hand-hotbar", "&cYou don't have this item in your inventory, in your hand or in your hotbar");
        plugin.config.addDefault("error-no-item-in-inventory-hand-hotbar-armor", "&cYou don't have this item in your inventory, in your hand, in your hotbar or in your armor");
        plugin.config.addDefault("error-no-item-in-inventory-hand-hotbar-armor-offhand", "&cYou don't have this item in your inventory, in your hand, in your hotbar, in your armor or in your offhand");
        plugin.config.addDefault("error-no-item-in-inventory-hand-hotbar-armor-offhand-mainhand", "&cYou don't have this item in your inventory, in your hand, in your hotbar, in your armor, in your offhand or in your mainhand");
        plugin.config.addDefault("error-message", "&cAn error has occured");
        // Job config
        plugin.config.addDefault("job-prefix", "&c&lJob &8&l>&r ");
        plugin.config.addDefault("job-no-job", "&cYou don't have a job");
        plugin.config.addDefault("default-job-value", "Chomeur");


    }
}
