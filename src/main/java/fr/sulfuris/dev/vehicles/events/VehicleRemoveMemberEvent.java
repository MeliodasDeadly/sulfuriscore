package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.entity.Player;

/**
 * On vehicle remove member (/vehicle removemember command). Members are players who may sit in the vehicle.
 */
public class VehicleRemoveMemberEvent extends SulfuVEvent implements IsCancellable, HasVehicle {
    private String licensePlate;
    private Player removedPlayer;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public Vehicle getVehicle() {
        return VehicleUtils.getVehicle(licensePlate);
    }

    /**
     * Get the player who used /vehicle removemember command
     *
     * @see #getPlayer()
     */
    public Player getRemover() {
        return super.getPlayer();
    }

    /**
     * Get the player who is being removed as a member
     */
    public Player getRemoved() {
        return removedPlayer;
    }

    /**
     * Set the player who is being removed as a member
     */
    public void setRemoved(Player player) {
        this.removedPlayer = player;
    }

}
