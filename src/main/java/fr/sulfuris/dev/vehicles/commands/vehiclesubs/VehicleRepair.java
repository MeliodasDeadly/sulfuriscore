package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.inventory.ItemStack;

public class VehicleRepair extends SulfuVehicleSubCommand {
    public VehicleRepair() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("svehicles.repair")) return true;

        final ItemStack item = player.getInventory().getItemInMainHand();

        if (!isHoldingVehicle()) return true;

        final String license = VehicleUtils.getLicensePlate(item);
        final int damage = ConfigModule.vehicleDataConfig.getDamage(license);
        final double maxHealth = VehicleUtils.getMaxHealthByDamage(damage);

        ConfigModule.vehicleDataConfig.setHealth(license, maxHealth);
        sendMessage(Message.REPAIR_SUCCESSFUL);
        return true;
    }
}
