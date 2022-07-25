package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

/**
 * <b>/vehicle help</b> - list of all MTV commands.
 */
public class VehicleHelp extends SulfuVehicleSubCommand {
    public VehicleHelp() {
        this.setPlayerCommand(true);
    }

    @Override
    public boolean execute() {
        sendMessage(String.format("&2&lSulfurisVehicles commandes: (%s)", main.instance.getDescription().getVersion()));
        sendMessage("");
        sendMessage(String.format("&2/svehicles &ainfo &f- &2%s", desc(Message.HELP_INFO)));
        sendMessage(String.format("&2/svehicles &apublic &f- &2%s", desc(Message.HELP_PUBLIC)));
        sendMessage(String.format("&2/svehicles &aprivate &f- &2%s", desc(Message.HELP_PRIVATE)));
        sendMessage(String.format("&2/svehicles &aaddrider &f- &2%s", desc(Message.HELP_ADD_RIDER)));
        sendMessage(String.format("&2/svehicles &aaddmember &f- &2%s", desc(Message.HELP_ADD_MEMBER)));
        sendMessage(String.format("&2/svehicles &aremoverider &f- &2%s", desc(Message.HELP_REMOVE_RIDER)));
        sendMessage(String.format("&2/svehicles &aremovemember &f- &2%s", desc(Message.HELP_REMOVE_MEMBER)));
        if (sender.hasPermission("sulfuris.admin")) {
            sendMessage("");
            sendMessage(String.format("&2/svehicles &alanguage &f- &2%s", desc(Message.ADMIN_LANGUAGE)));
            sendMessage(String.format("&2/svehicles &arefill &f- &2%s", desc(Message.ADMIN_REFILL)));
            sendMessage(String.format("&2/svehicles &arepair &f- &2%s", desc(Message.ADMIN_REPAIR)));
            sendMessage(String.format("&2/svehicles &aedit &f- &2%s", desc(Message.ADMIN_EDIT)));
            sendMessage(String.format("&2/svehicles &amenu &f- &2%s", desc(Message.ADMIN_MENU)));
            sendMessage(String.format("&2/svehicles &afuel &f- &2%s", desc(Message.ADMIN_FUEL)));
            sendMessage(String.format("&2/svehicles &arestore &f- &2%s", desc(Message.ADMIN_RESTORE)));
            sendMessage(String.format("&2/svehicles &areload &f- &2%s", desc(Message.ADMIN_RELOAD)));
            sendMessage(String.format("&2/svehicles &agivevoucher &f- &2%s", desc(Message.ADMIN_GIVEVOUCHER)));
            sendMessage(String.format("&2/svehicles &agivecar &f- &2%s", desc(Message.ADMIN_GIVECAR)));
            sendMessage(String.format("&2/svehicles &asetowner &f- &2%s", desc(Message.ADMIN_SETOWNER)));
            sendMessage(String.format("&2/svehicles &aupdate &f- &2%s", desc(Message.ADMIN_UPDATE)));
            sendMessage(String.format("&2/svehicles &adelete &f- &2%s", desc(Message.ADMIN_DELETE)));
        }
        sendMessage("");
        sendMessage("&7&oDownload it for free at mtvehicles.nl, by GamerJoep_.");
        return true;
    }

    private String desc(Message message) {
        return ConfigModule.messagesConfig.getMessage(message);
    }
}
