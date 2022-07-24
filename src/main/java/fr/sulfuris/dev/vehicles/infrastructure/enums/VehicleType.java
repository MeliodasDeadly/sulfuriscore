package fr.sulfuris.dev.vehicles.infrastructure.enums;

import fr.sulfuris.dev.vehicles.infrastructure.modules.DependencyModule;
import org.bukkit.Location;

import java.util.Locale;

public enum VehicleType {
    CAR,
    HOVER,
    TANK,
    HELICOPTER,
    AIRPLANE;

    public String getName() {
        return this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1).toLowerCase(Locale.ROOT);
    }

    public boolean isCar() {
        return this.equals(CAR);
    }

    public boolean isHover() {
        return this.equals(HOVER);
    }

    public boolean isTank() {
        return this.equals(TANK);
    }

    public boolean isHelicopter() {
        return this.equals(HELICOPTER);
    }

    public boolean isAirplane() {
        return this.equals(AIRPLANE);
    }

    public boolean canFly() {
        return this.equals(AIRPLANE) || this.equals(HELICOPTER);
    }

    public boolean isUsageDisabled(Location loc) {
        if (!DependencyModule.isDependencyEnabled(SoftDependency.WORLD_GUARD)) return false;
        return DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-use-" + this.toString().toLowerCase(Locale.ROOT), false);
    }
}
