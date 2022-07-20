package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasJerryCan;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

/**
 * On right click with a jerry can - its refuelling (the gas stations feature)
 */
public class JerryCanClickEvent extends SulfuVEvent implements IsCancellable, HasJerryCan {
    final private int jerryCanFuel;
    final private int jerryCanSize;

    public JerryCanClickEvent(int jerryCanFuel, int jerryCanSize) {
        this.jerryCanFuel = jerryCanFuel;
        this.jerryCanSize = jerryCanSize;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public int getJerryCanFuel() {
        return jerryCanFuel;
    }

    @Override
    public int getJerryCanSize() {
        return jerryCanSize;
    }

}
