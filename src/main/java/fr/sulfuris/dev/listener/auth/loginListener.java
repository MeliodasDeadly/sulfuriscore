package fr.sulfuris.dev.listener.auth;

import fr.sulfuris.dev.handlers.database.database;
import fr.sulfuris.dev.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class loginListener implements Listener {
    private final main plugin;

    public loginListener(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String ip = String.valueOf(player.getAddress());
        System.out.println(database.getUserFromDatabase(player.getName()).toString());
    }
}
