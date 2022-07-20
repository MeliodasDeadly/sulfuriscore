package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

/**
 * On inventory close
 */
public class InventoryCloseEvent extends SulfuVEvent implements HasInventory {
    final private InventoryTitle title;

    /**
     * Default constructor with the event-inventory's title
     *
     * @param title Event-inventory's title
     */
    public InventoryCloseEvent(InventoryTitle title) {
        this.title = title;
    }

    @Override
    public InventoryTitle getTitle() {
        return title;
    }

}
