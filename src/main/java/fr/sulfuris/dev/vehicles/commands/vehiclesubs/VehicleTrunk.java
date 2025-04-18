package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.events.VehicleOpenTrunkEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;

public class VehicleTrunk extends SulfuVehicleSubCommand {
    public VehicleTrunk() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {

        Vehicle vehicle = getVehicle();
        if (vehicle == null) return true;

        VehicleOpenTrunkEvent api = new VehicleOpenTrunkEvent();
        api.setPlayer(player);
        api.setLicensePlate(vehicle.getLicensePlate());
        api.call();
        if (api.isCancelled()) return true;

        VehicleUtils.openTrunk(player, api.getLicensePlate());
        return true;
    }
}
