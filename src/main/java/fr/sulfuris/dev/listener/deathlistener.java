package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.main;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class deathlistener implements Listener {

    private final main plugin;

    public deathlistener(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public int isPlayerDead(Player player) {
        Server server = player.getServer();
        return server.broadcastMessage(player.getName() + plugin.getConfig().getString("deathlistener1") + player.getPlayer().getKiller().getName() + plugin.getConfig().getString("deathlisterner2") + player.getPlayer().getKiller().getItemInHand().getType() + ".");
    }

}


