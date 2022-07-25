package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.VehicleData;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.inventory.ItemStack;

/**
 * <b>/vehicle refill</b> - refill the held vehicle.
 */
public class VehicleRefill extends SulfuVehicleSubCommand {
    public VehicleRefill() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("mtvehicles.refill")) return true;

        final ItemStack item = player.getInventory().getItemInMainHand();

        if (!isHoldingVehicle()) return true;

        final String licensePlate = VehicleUtils.getLicensePlate(item);
        Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);
        vehicle.setFuel(100.0);
        vehicle.save();

        if (VehicleData.fallDamage.get(licensePlate) != null) VehicleData.fallDamage.remove(licensePlate);

        sendMessage(Message.REFILL_SUCCESSFUL);
        return true;
    }
}
