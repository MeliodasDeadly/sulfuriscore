package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

public class InventoryClickEvent extends SulfuVEvent implements IsCancellable, HasInventory {
    final private InventoryTitle title;
    private int clickedSlot;

        public InventoryClickEvent(InventoryTitle title) {
            this.title = title;
        }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getClickedSlot() {
        return clickedSlot;
    }

    public void setClickedSlot(int clickedSlot) {
        this.clickedSlot = clickedSlot;
    }

    @Override
    public InventoryTitle getTitle() {
        return title;
    }

}
