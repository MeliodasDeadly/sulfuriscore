package fr.sulfuris.dev.vehicles.events;

import fr.sulfuris.dev.vehicles.events.interfaces.HasVehicle;
import fr.sulfuris.dev.vehicles.events.interfaces.IsCancellable;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVEvent;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.entity.Entity;

/**
 * On vehicle damage
 */
public class VehicleDamageEvent extends SulfuVEvent implements IsCancellable, HasVehicle {
    private Entity damager;
    private double damage;
    private String licensePlate;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public Vehicle getVehicle() {
        return VehicleUtils.getVehicle(licensePlate);
    }

    /**
     * Get the entity that has damaged the vehicle
     *
     * @return Damager
     */
    public Entity getDamager() {
        return damager;
    }

    /**
     * Set the entity that has damaged the vehicle
     *
     * @param damager New damager
     */
    public void setDamager(Entity damager) {
        this.damager = damager;
    }

    /**
     * Get the damage dealt to the vehicle
     *
     * @return Damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Set new damage dealt to the vehicle
     *
     * @param damage New damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }
}
