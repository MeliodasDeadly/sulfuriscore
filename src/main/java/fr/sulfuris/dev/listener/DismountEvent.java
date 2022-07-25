package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class DismountEvent implements Listener {
    private final main plugin;

    public DismountEvent(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onVehicleLeave(EntityDismountEvent event) {
        if (event.getDismounted() instanceof Player) {
            System.out.println("Player " + event.getDismounted().getName() + " has left the vehicle " + event.getEntity().getCustomName());
            Player player = (Player) event.getDismounted();
            if (event.getEntity().getCustomName().equals(player.getName())) {
                event.getEntity().remove();
            }
        }
    }
}

