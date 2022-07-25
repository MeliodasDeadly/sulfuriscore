package fr.sulfuris.dev.commands.other;

import fr.sulfuris.dev.main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SitCommand implements CommandExecutor {

    private main plugin;

    public SitCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("sit").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 0.5, player.getLocation().getZ()), EntityType.ARMOR_STAND);
            armorStand.setBasePlate(false);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
            armorStand.setCustomName(player.getName());
            armorStand.setPassenger(player);
        }
        return true;
    }
}
