package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.PluginUpdater;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

/**
 * <b>/vehicle update</b> - update the plugin if a newer version is available.
 */
public class VehicleUpdate extends SulfuVehicleSubCommand {
    @Override
    public boolean execute() {
        if (!checkPermission("mtvehicles.update")) return true;

        if (!(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.AUTO_UPDATE)) {
            sendMessage(Message.UPDATE_DISABLED);
            return false;
        }

        PluginUpdater.updatePlugin(sender);
        return true;
    }
}
