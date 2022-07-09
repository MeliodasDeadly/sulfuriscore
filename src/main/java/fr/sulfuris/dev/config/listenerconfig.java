package fr.sulfuris.dev.config;
import fr.sulfuris.dev.main;

public class listenerconfig {
    public static void setConfig(main plugin) {
        plugin.config.addDefault("deathlistener1", "Et mort de ");
        plugin.config.addDefault("deathlistener2", " avec un ");

    }

}
