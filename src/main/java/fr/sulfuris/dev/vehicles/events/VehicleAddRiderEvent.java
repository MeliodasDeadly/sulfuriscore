package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.entity.Player;

/**
 * On vehicle add rider (/vehicle addrider command). Riders are players who may steer the vehicle.
 */
public class VehicleAddRiderEvent extends SulfuVEvent implements IsCancellable, HasVehicle {
    private String licensePlate;
    private Player addedPlayer;

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
     * Get the player who used /vehicle addrider command
     *
     * @see #getPlayer()
     */
    public Player getAdder() {
        return super.getPlayer();
    }

    /**
     * Get the player who is being added as a rider
     */
    public Player getAdded() {
        return addedPlayer;
    }

    /**
     * Set the player who is being added as a rider
     */
    public void setAdded(Player player) {
        this.addedPlayer = player;
    }

}
