package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.commands.VehicleSubCommandManager;
import fr.sulfuris.dev.vehicles.commands.VehicleTabCompleterManager;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.PluginCommand;

import java.util.HashMap;

public class CommandModule {
        public static HashMap<String, SulfuVehicleSubCommand> subcommands = new HashMap<>();
    private static @Getter
    @Setter
    CommandModule instance;

    public CommandModule() {
        PluginCommand pluginCommand = main.getPlugin().getCommand("svehicles");

        if (pluginCommand != null) {
            pluginCommand.setExecutor(new VehicleSubCommandManager());
            pluginCommand.setTabCompleter(new VehicleTabCompleterManager());
        }
    }
}
