package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasJerryCan;
import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;

public class VehicleFuelEvent extends SulfuVEvent implements IsCancellable, HasVehicle, HasJerryCan {
    final private double vehicleFuel;
    final private int jerryCanFuel;
    final private int jerryCanSize;

    private String licensePlate;

    public VehicleFuelEvent(double vehicleFuel, int jerryCanFuel, int jerryCanSize) {
        this.vehicleFuel = vehicleFuel;
        this.jerryCanFuel = jerryCanFuel;
        this.jerryCanSize = jerryCanSize;
    }

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

    @Override
    public int getJerryCanFuel() {
        return jerryCanFuel;
    }

    @Override
    public int getJerryCanSize() {
        return jerryCanSize;
    }

        public double getVehicleFuel() {
            return vehicleFuel;
        }
}
