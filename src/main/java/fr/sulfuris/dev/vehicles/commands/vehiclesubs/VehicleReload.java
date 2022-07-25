package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;

/**
 * <b>/vehicle reload</b> - reload the plugin's configuration files.
 */
public class VehicleReload extends SulfuVehicleSubCommand {
    public VehicleReload() {
        this.setPlayerCommand(false);
    }

    @Override
    public boolean execute() {
        if (!checkPermission("mtvehicles.reload")) return true;

        Bukkit.getLogger().info("Reload config files..");
        ConfigModule.reloadConfigs();
        Bukkit.getLogger().info("Files loaded!");
        sendMessage(Message.RELOAD_SUCCESSFUL);

        return true;
    }
}
