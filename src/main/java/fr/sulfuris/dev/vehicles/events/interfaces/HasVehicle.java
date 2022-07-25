package fr.sulfuris.dev.vehicles.events.interfaces;

import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;

public interface HasVehicle {

        public String getLicensePlate();

    public void setLicensePlate(String licensePlate);

    public Vehicle getVehicle();

}
