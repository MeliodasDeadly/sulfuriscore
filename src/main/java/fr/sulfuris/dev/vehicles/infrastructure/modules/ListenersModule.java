package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.listeners.*;
import fr.sulfuris.dev.vehicles.listeners.inventory.InventoryClickListener;
import fr.sulfuris.dev.vehicles.listeners.inventory.InventoryCloseListener;
import lombok.Getter;
import lombok.Setter;

public class ListenersModule {
    private static @Getter
    @Setter
    ListenersModule instance;

    public ListenersModule() {
        main.getPlugin().registerListener(new InventoryClickListener());
        main.getPlugin().registerListener(new VehiclePlaceListener());
        main.getPlugin().registerListener(new VehicleClickListener());
        main.getPlugin().registerListener(new VehicleLeaveListener());
        main.getPlugin().registerListener(new ChatListener());
        main.getPlugin().registerListener(new VehicleEntityListener());
        main.getPlugin().registerListener(new JoinListener());
        main.getPlugin().registerListener(new VehicleVoucherListener());
        main.getPlugin().registerListener(new InventoryCloseListener());
        main.getPlugin().registerListener(new JerryCanClickListener());
    }
}
