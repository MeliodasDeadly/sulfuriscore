package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class VehicleSetOwner extends SulfuVehicleSubCommand {
    public VehicleSetOwner() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        ItemStack item = player.getInventory().getItemInMainHand();

        boolean playerSetOwner = (boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.PUT_ONESELF_AS_OWNER);

        if (!playerSetOwner && !checkPermission("svehicles.setowner")) {
            return true;
        }

        if (!isHoldingVehicle()) return true;

        if (arguments.length != 2) {
            sendMessage(Message.USE_SET_OWNER);
            return true;
        }

        String licensePlate = VehicleUtils.getLicensePlate(item);

        if (!VehicleUtils.existsByLicensePlate(licensePlate)) {
            sendMessage(Message.VEHICLE_NOT_FOUND);
            return true;
        }

        Player argPlayer = Bukkit.getPlayer(arguments[1]);
        if (argPlayer == null) {
            sendMessage(Message.PLAYER_NOT_FOUND);
            return true;
        }

        Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);
        assert vehicle != null;

        if ((playerSetOwner || !player.hasPermission("svehicles.setowner")) && !vehicle.isOwner(player)) {
            sendMessage(Message.NOT_YOUR_CAR);
            return true;
        }

        vehicle.setRiders(new ArrayList<>());
        vehicle.setMembers(new ArrayList<>());
        vehicle.setOwner(argPlayer.getUniqueId());
        vehicle.save();

        sendMessage(Message.MEMBER_CHANGE);

        return true;
    }
}
