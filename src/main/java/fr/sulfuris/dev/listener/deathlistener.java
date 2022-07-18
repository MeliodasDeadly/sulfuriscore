package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.main;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class deathlistener implements Listener {

    private final main plugin;

    public deathlistener(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public int isPlayerDead(org.bukkit.event.entity.PlayerDeathEvent event) {
        Server server = event.getPlayer().getServer();
        return server.broadcastMessage(event.getPlayer().getName() + plugin.getConfig().getString("deathlistener1") + event.getPlayer().getKiller().getName() + plugin.getConfig().getString("deathlisterner2") + event.getPlayer().getKiller().getItemInHand().getType() + ".");
    }

}


