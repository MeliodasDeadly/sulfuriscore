package fr.sulfuris.dev.vehicles.events.interfaces;

import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;

/**
 * Interface for all inventory events
 */
public interface HasInventory {

    /**
     * Get the title of the inventory player has clicked in
     *
     * @return Inventory title
     * @see InventoryTitle#getStringTitle()
     */
    public InventoryTitle getTitle();

}
