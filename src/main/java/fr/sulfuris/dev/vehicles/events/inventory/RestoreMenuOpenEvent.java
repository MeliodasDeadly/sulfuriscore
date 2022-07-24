package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import org.bukkit.entity.Player;

public class RestoreMenuOpenEvent extends SulfuVEvent implements IsCancellable, HasInventory {

        public RestoreMenuOpenEvent(Player player) {
            setPlayer(player);
        }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public InventoryTitle getTitle() {
        return InventoryTitle.VEHICLE_RESTORE_MENU;
    }

}
