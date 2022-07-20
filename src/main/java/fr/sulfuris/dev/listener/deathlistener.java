package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.Main;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class deathlistener implements Listener {

    private final Main plugin;

    public deathlistener(final Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public int isPlayerDead(org.bukkit.event.entity.PlayerDeathEvent event) {
        Server server = event.getEntity().getServer();
        if (event instanceof Player)
            return server.broadcastMessage(event.getEntity().getPlayer().getName() + plugin.getConfig().getString("deathlistener1") + event.getEntity().getPlayer().getKiller().getName() + plugin.getConfig().getString("deathlisterner2") + event.getEntity().getPlayer().getKiller().getItemInHand().getType() + ".");
        return 0;
    }
}


