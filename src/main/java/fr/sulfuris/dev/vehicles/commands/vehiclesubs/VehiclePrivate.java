package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;

/**
 * <b>/vehicle private</b> - set the vehicle as private (only the owner can enter it).
 */
public class VehiclePrivate extends SulfuVehicleSubCommand {
    public VehiclePrivate() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {

        Vehicle vehicle = getVehicle();
        if (vehicle == null) return true;

        vehicle.setOpen(false);
        vehicle.save();

        sendMessage(Message.ACTION_SUCCESSFUL);
        return true;
    }
}
