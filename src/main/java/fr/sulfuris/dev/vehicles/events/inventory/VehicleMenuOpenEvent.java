package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import org.bukkit.entity.Player;

public class VehicleMenuOpenEvent extends SulfuVEvent implements IsCancellable, HasInventory {

        public VehicleMenuOpenEvent(Player player) {
            setPlayer(player);
        }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public InventoryTitle getTitle() {
        return InventoryTitle.VEHICLE_MENU;
    }

}
