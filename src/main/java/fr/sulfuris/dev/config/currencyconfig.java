package fr.sulfuris.dev.config;

import fr.sulfuris.dev.main;
public class currencyconfig {
    public static void setConfig(main plugin) {
        if (!plugin.config.contains("currency")) {
            plugin.config.createSection("currency");
        }
        plugin.config.addDefault("default-currency", "&1000&l$");
        plugin.config.addDefault("default-currency-value", "1000");
        plugin.config.addDefault("default-currency-name", "Dollar");
        plugin.config.addDefault("default-currency-symbol", "$");
        plugin.config.addDefault("default-currency-decimal", ".");
        plugin.config.addDefault("default-currency-separator", ",");
        plugin.config.addDefault("default-currency-grouping", "3");
        plugin.config.addDefault("default-currency-grouping-separator", " ");
        plugin.config.addDefault("default-currency-grouping-symbol", ",");
        plugin.config.addDefault("default-currency-grouping-symbol-position", "before");


    }
}