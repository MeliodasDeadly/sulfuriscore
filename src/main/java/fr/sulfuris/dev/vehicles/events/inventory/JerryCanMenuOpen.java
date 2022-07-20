package fr.sulfuris.dev.vehicles.events.inventory;

import fr.sulfuris.dev.vehicles.events.interfaces.HasInventory;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import org.bukkit.entity.Player;

/**
 * On /vehicle fuel open
 */
public class JerryCanMenuOpen extends SulfuVEvent implements IsCancellable, HasInventory {

    /**
     * Default constructor with a player (calls {@link SulfuVEvent#setPlayer(Player)}).
     *
     * @param player Player
     */
    public JerryCanMenuOpen(Player player) {
        setPlayer(player);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public InventoryTitle getTitle() {
        return InventoryTitle.JERRYCAN_MENU;
    }

}
