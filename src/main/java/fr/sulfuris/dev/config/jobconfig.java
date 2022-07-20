package fr.sulfuris.dev.config;

import fr.sulfuris.dev.Main;

public class jobconfig {
    public static void setConfig(Main plugin) {
        if (!plugin.config.contains("job")) {
            plugin.config.createSection("job");
        }

        plugin.config.addDefault("0", "chomeur");
        plugin.config.addDefault("1", "mineur");
        plugin.config.addDefault("2", "forgerons");
        plugin.config.addDefault("3", "bucherons");
        plugin.config.addDefault("4", "pecheurs");
        plugin.config.addDefault("5", "paysans");

    }
}