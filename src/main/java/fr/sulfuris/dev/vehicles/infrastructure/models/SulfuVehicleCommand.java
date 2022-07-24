package fr.sulfuris.dev.vehicles.infrastructure.models;

import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SulfuVehicleCommand implements CommandExecutor {
        public CommandSender commandSender;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        this.commandSender = commandSender;
        return this.execute(commandSender, command, label, strings);
    }

    public abstract boolean execute(CommandSender sender, Command cmd, String label, String[] args);

    public void sendMessage(String message) {
        this.commandSender.sendMessage(TextUtils.colorize(message));
    }
}
