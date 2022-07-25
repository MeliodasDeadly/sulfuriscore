package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.LanguageUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;

/**
 * <b>/vehicle language</b> - set the plugin's language (in a GUI).
 */
public class VehicleLanguage extends SulfuVehicleSubCommand {
    public VehicleLanguage() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (sender.hasPermission("mtvehicles.language") || sender.hasPermission("mtvehicles.admin"))
            LanguageUtils.openLanguageGUI(player);
        else sendMessage(Message.NO_PERMISSION);

        return true;
    }
}
