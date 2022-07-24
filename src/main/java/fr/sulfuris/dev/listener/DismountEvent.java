package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class DismountEvent implements Listener {
    private final Main plugin;

    public DismountEvent(final Main plugin) {
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

