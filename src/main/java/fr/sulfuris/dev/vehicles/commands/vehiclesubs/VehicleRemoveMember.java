package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.events.VehicleRemoveMemberEvent;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class VehicleRemoveMember extends SulfuVehicleSubCommand {
    public VehicleRemoveMember() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {

        Vehicle vehicle = getVehicle();
        if (vehicle == null) return true;

        if (arguments.length != 2) {
            sendMessage(Message.USE_REMOVE_MEMBER);
            return true;
        }

        Player argPlayer = Bukkit.getPlayer(arguments[1]);

        VehicleRemoveMemberEvent api = new VehicleRemoveMemberEvent();
        api.setPlayer(player);
        api.setRemoved(argPlayer);
        api.setLicensePlate(vehicle.getLicensePlate());
        api.call();

        if (api.isCancelled()) return true;
        vehicle = api.getVehicle();
        argPlayer = api.getRemoved();

        if (vehicle == null) {
            sendMessage(Message.VEHICLE_NOT_FOUND);
            return true;
        }

        if (argPlayer == null) {
            sendMessage(Message.PLAYER_NOT_FOUND);
            return true;
        }

        List<String> members = vehicle.getMembers();
        String playerUUID = argPlayer.getUniqueId().toString();

        if (!members.contains(playerUUID)) {
            sendMessage(Message.NOT_A_MEMBER);
            return true;
        }

        members.remove(playerUUID);
        vehicle.setMembers(members);
        vehicle.save();

        sendMessage(Message.MEMBER_CHANGE);

        return true;
    }
}
