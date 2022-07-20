package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;

/**
 * <b>/vehicle public</b> - set the vehicle as public (anyone can enter it).
 */
public class VehiclePublic extends SulfuVehicleSubCommand {
    public VehiclePublic() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {

        Vehicle vehicle = getVehicle();
        if (vehicle == null) return true;

        vehicle.setOpen(true);
        vehicle.save();

        sendMessage(Message.ACTION_SUCCESSFUL);
        return true;
    }
}
