package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.stream.Collectors;

public class VehicleInfo extends SulfuVehicleSubCommand {
    public VehicleInfo() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {

        Vehicle vehicle = getVehicle();
        if (vehicle == null) return true;

        String licensePlate = vehicle.getLicensePlate();

        NumberFormat formatter = new DecimalFormat("#0.000");
        sendMessage(Message.VEHICLE_INFO_INFORMATION);
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_TYPE) + vehicle.getVehicleType().getName());
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_NAME) + vehicle.getName());
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_LICENSE) + licensePlate);
        if (player.hasPermission("svehicles.admin")) {
            sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_UUID) + VehicleUtils.getCarUUID(licensePlate));
        }
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_SPEED) + formatter.format(vehicle.getMaxSpeed() * 20).toString().replace(",", ".") + " blocks/sec");
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_ACCELERATION) + formatter.format(vehicle.getAccelerationSpeed() / 0.2 * 100).toString().replace(",", ".") + " blocks/sec^2");
        sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_OWNER) + vehicle.getOwnerName());

        if (vehicle.getRiders().size() == 0) {
            sendMessage(Message.VEHICLE_INFO_RIDERS_NONE);
        } else {
            sendMessage(String.format(
                    ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_RIDERS),
                    vehicle.getRiders().size(),
                    vehicle.getRiders().stream()
                            .map(UUID::fromString)
                            .map(Bukkit::getOfflinePlayer)
                            .map(OfflinePlayer::getName)
                            .collect(Collectors.joining(", ")))
            );
        }

        if (vehicle.getMembers().size() == 0) {
            sendMessage(Message.VEHICLE_INFO_MEMBERS_NONE);
        } else {
            sendMessage(String.format(
                    ConfigModule.messagesConfig.getMessage(Message.VEHICLE_INFO_MEMBERS),
                    vehicle.getMembers().size(),
                    vehicle.getMembers().stream()
                            .map(UUID::fromString)
                            .map(Bukkit::getOfflinePlayer)
                            .map(OfflinePlayer::getName)
                            .collect(Collectors.joining(", ")))
            );
        }

        return true;
    }
}
