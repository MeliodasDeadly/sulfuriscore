package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VehicleGiveCar extends SulfuVehicleSubCommand {
    public VehicleGiveCar() {
        this.setPlayerCommand(false);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("svehicles.givecar")) return true;

        if (arguments.length != 3) {
            sendMessage(Message.USE_GIVE_CAR);
            return true;
        }

        Player argPlayer = Bukkit.getPlayer(arguments[1]);

        String carUuid = arguments[2];

        if (argPlayer == null) {
            sendMessage(Message.PLAYER_NOT_FOUND);
            return true;
        }

        ItemStack car = VehicleUtils.getItemByUUID(argPlayer, carUuid);

        if (car == null) {
            sender.sendMessage(ConfigModule.messagesConfig.getMessage(Message.GIVE_CAR_NOT_FOUND));
            return true;
        }
        argPlayer.getInventory().addItem(car);
        sender.sendMessage(ConfigModule.messagesConfig.getMessage(Message.GIVE_CAR_SUCCESS).replace("%p%", argPlayer.getName()));

        return true;
    }
}
