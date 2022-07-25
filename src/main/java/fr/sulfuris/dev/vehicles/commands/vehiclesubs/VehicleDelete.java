package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.inventory.ItemStack;

public class VehicleDelete extends SulfuVehicleSubCommand {
    public VehicleDelete() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("svehicles.delete")) return true;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!isHoldingVehicle()) return true;

        try {
            String licensePlate = VehicleUtils.getLicensePlate(item);
            VehicleUtils.getVehicle(licensePlate).delete();
            sendMessage(Message.VEHICLE_DELETED);
        } catch (Exception e) {
            sendMessage(Message.VEHICLE_ALREADY_DELETED);
        }

        player.getInventory().getItemInMainHand().setAmount(0);
        return true;
    }
}
