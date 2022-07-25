package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;

import java.util.List;
import java.util.Map;

/**
 * Methods for vehicles.yml.<br>
 * <b>Do not initialise this class directly. Use {@link ConfigModule#vehiclesConfig} instead.</b>
 */
public class VehiclesConfig extends Config {
    /**
     * Default constructor - <b>do not use this.</b><br>
     * Use {@link ConfigModule#vehiclesConfig} instead.
     */
    public VehiclesConfig() {
        super(ConfigType.VEHICLES);
    }

    /**
     * Get Map of the whole file.
     */
    public List<Map<?, ?>> getVehicles() {
        return getConfiguration().getMapList("voertuigen");
    }
}
