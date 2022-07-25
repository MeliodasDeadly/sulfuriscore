package fr.sulfuris.dev.config;
import fr.sulfuris.dev.main;

public class listenerconfig {
    public static void setConfig(main plugin) {
        // create config if it doesn't exist
        if (!plugin.config.contains("listeners")) {
            plugin.config.createSection("listeners");
        }

        plugin.config.addDefault("deathlistener1", "Et mort de ");
        plugin.config.addDefault("deathlistener2", " avec un ");
        plugin.config.addDefault("joinlistener1", " Le joueur : ");
        plugin.config.addDefault("joinlistener2", " vient de rejoindre le serveur !");

    }

}
