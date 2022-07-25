package fr.sulfuris.dev.vehicles.commands;

import fr.sulfuris.dev.vehicles.commands.vehiclesubs.*;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleCommand;
import fr.sulfuris.dev.vehicles.infrastructure.modules.CommandModule;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class VehicleSubCommandManager extends SulfuVehicleCommand {

    public static String CMD_NAME = "svehicles";

    public VehicleSubCommandManager() {
        CommandModule.subcommands.put("info", new VehicleInfo());
        CommandModule.subcommands.put("help", new VehicleHelp());
        CommandModule.subcommands.put("admin", new VehicleHelp());
        CommandModule.subcommands.put("reload", new VehicleReload());
        CommandModule.subcommands.put("menu", new VehicleMenu());
        CommandModule.subcommands.put("restore", new VehicleRestore());
        CommandModule.subcommands.put("edit", new VehicleEdit());
        CommandModule.subcommands.put("fuel", new VehicleFuel());
        CommandModule.subcommands.put("benzine", new VehicleFuel());
        CommandModule.subcommands.put("setowner", new VehicleSetOwner());
        CommandModule.subcommands.put("public", new VehiclePublic());
        CommandModule.subcommands.put("private", new VehiclePrivate());
        CommandModule.subcommands.put("addmember", new VehicleAddMember());
        CommandModule.subcommands.put("addrider", new VehicleAddRider());
        CommandModule.subcommands.put("removemember", new VehicleRemoveMember());
        CommandModule.subcommands.put("removerider", new VehicleRemoveRider());
        CommandModule.subcommands.put("givecar", new VehicleGiveCar());
        CommandModule.subcommands.put("givevoucher", new VehicleGiveVoucher());
        CommandModule.subcommands.put("update", new VehicleUpdate());
        CommandModule.subcommands.put("delete", new VehicleDelete());
        CommandModule.subcommands.put("language", new VehicleLanguage());
        CommandModule.subcommands.put("about", new VehicleVersion());
        CommandModule.subcommands.put("version", new VehicleVersion());
        CommandModule.subcommands.put("repair", new VehicleRepair());
        CommandModule.subcommands.put("refill", new VehicleRefill());
        CommandModule.subcommands.put("refuel", new VehicleRefill());
        CommandModule.subcommands.put("trunk", new VehicleTrunk());
        CommandModule.subcommands.put("baggage", new VehicleTrunk());
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            CommandModule.subcommands.get("help").onExecute(sender, cmd, label, args);
            return true;
        }

        final String subcommand = args[0].toLowerCase();
        if (CommandModule.subcommands.get(subcommand) == null) {
            sendMessage(ConfigModule.messagesConfig.getMessage(Message.COMMAND_DOES_NOT_EXIST));
            return true;
        }

        CommandModule.subcommands.get(subcommand).onExecute(sender, cmd, label, args);
        return true;
    }
}
