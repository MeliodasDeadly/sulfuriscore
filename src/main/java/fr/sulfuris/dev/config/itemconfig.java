package fr.sulfuris.dev.config;

import fr.sulfuris.dev.Main;

public class itemconfig {
    public static void setConfig(Main plugin) {
        if (!plugin.config.contains("items")) {
            plugin.config.createSection("items");
        }
        // Money Item
        plugin.config.addDefault("money-item-name", "money");
        plugin.config.addDefault("money-item-lore", "&aMoney");
        plugin.config.addDefault("money-item-material", "DIAMOND");


    }
}
