package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.commands.VehicleSubCommandManager;
import fr.sulfuris.dev.vehicles.commands.VehicleTabCompleterManager;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.PluginCommand;

import java.util.HashMap;

/**
 * Module for managing /mtv commands
 */
public class CommandModule {
    /**
     * HashMap mapping all /mtv subcommands and their respective classes
     */
    public static HashMap<String, SulfuVehicleSubCommand> subcommands = new HashMap<>();
    private static @Getter
    @Setter
    CommandModule instance;

    /**
     * Constructor which registers executor and tab completer
     */
    public CommandModule() {
        PluginCommand pluginCommand = main.getPlugin().getCommand("svehicles");

        if (pluginCommand != null) {
            pluginCommand.setExecutor(new VehicleSubCommandManager());
            pluginCommand.setTabCompleter(new VehicleTabCompleterManager());
        }
    }
}
