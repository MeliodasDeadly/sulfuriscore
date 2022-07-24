package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.LanguageUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;

public class VehicleLanguage extends SulfuVehicleSubCommand {
    public VehicleLanguage() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        if (sender.hasPermission("svehicles.language") || sender.hasPermission("svehicles.admin"))
            LanguageUtils.openLanguageGUI(player);
        else sendMessage(Message.NO_PERMISSION);

        return true;
    }
}
