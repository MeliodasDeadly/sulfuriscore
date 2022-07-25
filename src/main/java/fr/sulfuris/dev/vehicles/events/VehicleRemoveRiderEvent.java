package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.entity.Player;

/**
 * On vehicle remove rider (/vehicle removerider command). Riders are players who may steer the vehicle.
 */
public class VehicleRemoveRiderEvent extends SulfuVEvent implements IsCancellable, HasVehicle {
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
     * Get the player who used /vehicle removerider command
     *
     * @see #getPlayer()
     */
    public Player getRemover() {
        return super.getPlayer();
    }

    /**
     * Get the player who is being removed as a rider
     */
    public Player getRemoved() {
        return removedPlayer;
    }

    /**
     * Set the player who is being removed as a rider
     */
    public void setRemoved(Player player) {
        this.removedPlayer = player;
    }

}
