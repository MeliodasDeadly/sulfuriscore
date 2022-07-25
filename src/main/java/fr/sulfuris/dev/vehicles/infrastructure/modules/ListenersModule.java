package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.Main;
import fr.sulfuris.dev.vehicles.listeners.*;
import fr.sulfuris.dev.vehicles.listeners.inventory.InventoryClickListener;
import fr.sulfuris.dev.vehicles.listeners.inventory.InventoryCloseListener;
import lombok.Getter;
import lombok.Setter;

/**
 * Module which registers all listeners used by the plugin
 */
public class ListenersModule {
    private static @Getter
    @Setter
    ListenersModule instance;

    public ListenersModule() {
        Main.getPlugin().registerListener(new InventoryClickListener());
        Main.getPlugin().registerListener(new VehiclePlaceListener());
        Main.getPlugin().registerListener(new VehicleClickListener());
        Main.getPlugin().registerListener(new VehicleLeaveListener());
        Main.getPlugin().registerListener(new ChatListener());
        Main.getPlugin().registerListener(new VehicleEntityListener());
        Main.getPlugin().registerListener(new JoinListener());
        Main.getPlugin().registerListener(new VehicleVoucherListener());
        Main.getPlugin().registerListener(new InventoryCloseListener());
        Main.getPlugin().registerListener(new JerryCanClickListener());
    }
}
