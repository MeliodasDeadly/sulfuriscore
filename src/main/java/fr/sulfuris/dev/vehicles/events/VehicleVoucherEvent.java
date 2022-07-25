package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

/**
 * On right click with a voucher
 */
public class VehicleVoucherEvent extends SulfuVEvent implements IsCancellable {
    private String voucherUUID;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get UUID of the car that will be given from the clicked voucher
     *
     * @return Car UUID
     */
    public String getVoucherUUID() {
        return voucherUUID;
    }

    /**
     * Set a new UUID of the car that will be given from the clicked voucher
     *
     * @param voucherUUID New car UUID
     */
    public void setVoucherUUID(String voucherUUID) {
        this.voucherUUID = voucherUUID;
    }
}
