package fr.sulfuris.dev.vehicles.infrastructure.models;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import static fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils.isInsideVehicle;

public abstract class SulfuVehicleSubCommand {
    protected CommandSender sender;
    protected @Nullable Player player;
    protected boolean isPlayer;
    protected String[] arguments;
    private boolean isPlayerCommand;

    public boolean onExecute(CommandSender sender, Command cmd, String s, String[] args) {
        this.sender = sender;
        this.isPlayer = sender instanceof Player;
        this.player = isPlayer ? (Player) sender : null;
        this.arguments = args;

        if (isPlayerCommand && !isPlayer) {
            sendMessage(ConfigModule.messagesConfig.getMessage(Message.NOT_FOR_CONSOLE));
            return true;
        }

        return this.execute();
    }

    public abstract boolean execute();

    public void sendMessage(String message) {
        this.sender.sendMessage(TextUtils.colorize(message));
    }

    public void sendMessage(Message message) {
        this.sender.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(message)));
    }

    public boolean checkPermission(String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }

        ConfigModule.messagesConfig.sendMessage(sender, Message.NO_PERMISSION);

        return false;
    }

    public boolean isPlayerCommand() {
        return isPlayerCommand;
    }

    public void setPlayerCommand(boolean playerCommand) {
        isPlayerCommand = playerCommand;
    }

    protected Vehicle getVehicle() {
        if (player == null) return null;

        if (isInsideVehicle(player) && VehicleUtils.getVehicle(VehicleUtils.getLicensePlate(player.getVehicle())).isOwner(player))
            return VehicleUtils.getVehicle(VehicleUtils.getLicensePlate(player.getVehicle()));


        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.hasItemMeta() && (new NBTItem(item)).hasKey("svehicles.kenteken"))
            return VehicleUtils.getVehicle(VehicleUtils.getLicensePlate(item));

        sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.COMMAND_NO_VEHICLE)));
        return null;
    }

    protected boolean isHoldingVehicle() {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta() || !(new NBTItem(item)).hasKey("svehicles.kenteken")) {
            sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.NO_VEHICLE_IN_HAND)));
            return false;
        }
        return true;
    }
}
