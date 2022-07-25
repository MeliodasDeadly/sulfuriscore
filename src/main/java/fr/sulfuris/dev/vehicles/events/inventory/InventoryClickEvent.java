package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

/**
 * On inventory click
 */
public class InventoryClickEvent extends SulfuVEvent implements IsCancellable, HasInventory {
    final private InventoryTitle title;
    private int clickedSlot;

    /**
     * Default constructor with the event-inventory's title
     *
     * @param title Event-inventory's title
     */
    public InventoryClickEvent(InventoryTitle title) {
        this.title = title;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get the number of slot player has clicked (starts with 0)
     *
     * @return Number of clicked slot
     */
    public int getClickedSlot() {
        return clickedSlot;
    }

    /**
     * Set the number of slot which will be used by the plugin (slots start with 0)
     *
     * @param clickedSlot New number of clicked slot
     */
    public void setClickedSlot(int clickedSlot) {
        this.clickedSlot = clickedSlot;
    }

    @Override
    public InventoryTitle getTitle() {
        return title;
    }

}
