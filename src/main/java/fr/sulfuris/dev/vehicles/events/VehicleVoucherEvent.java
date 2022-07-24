package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;

public class VehicleVoucherEvent extends SulfuVEvent implements IsCancellable {
    private String voucherUUID;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

        public String getVoucherUUID() {
            return voucherUUID;
        }

    public void setVoucherUUID(String voucherUUID) {
        this.voucherUUID = voucherUUID;
    }
}
