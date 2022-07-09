package fr.sulfuris.dev.config;

import fr.sulfuris.dev.main;

public class itemconfig {
    public static void setConfig(main plugin) {
        // Money Item
        plugin.config.addDefault("money-item-name", "money");
        plugin.config.addDefault("money-item-lore", "&aMoney");
        plugin.config.addDefault("money-item-material", "DIAMOND");
    }
}
