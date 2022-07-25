package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.events.inventory.RestoreMenuOpenEvent;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.MenuUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * <b>/vehicle restore (%player%)</b> - open a GUI menu of all vehicles in database (vehicleData.yml), their owner may be specified.
 */
public class VehicleRestore extends SulfuVehicleSubCommand {
    public VehicleRestore() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("mtvehicles.restore")) return true;

        sendMessage(Message.MENU_OPEN);

        if (arguments.length != 2) {
            MenuUtils.restoreCMD(player, 1, null);
            MenuUtils.restoreUUID.put(player, null);
            return true;
        }

        OfflinePlayer argPlayer = Bukkit.getPlayer(arguments[1]);
        if (argPlayer == null || !argPlayer.hasPlayedBefore()) {
            sendMessage(Message.OFFLINE_PLAYER_NOT_FOUND);
            return true;
        }

        RestoreMenuOpenEvent api = new RestoreMenuOpenEvent(player);
        api.call();
        if (api.isCancelled()) return true;

        MenuUtils.restoreCMD(player, 1, argPlayer.getUniqueId());
        MenuUtils.restoreUUID.put(player, argPlayer.getUniqueId());
        MenuUtils.restorePage.put(player, 1);

        return true;
    }
}
