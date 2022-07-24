package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.Location;

public class VehiclePlaceEvent extends SulfuVEvent implements IsCancellable, HasVehicle {
    private Location location;
    private String licensePlate;

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

        public Location getLocation() {
            return location;
        }

    public void setLocation(Location location) {
        this.location = location;
    }

}
