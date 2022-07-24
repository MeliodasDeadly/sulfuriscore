package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;

import java.util.List;
import java.util.Map;

public class VehiclesConfig extends Config {
        public VehiclesConfig() {
            super(ConfigType.VEHICLES);
        }

    public List<Map<?, ?>> getVehicles() {
        return getConfiguration().getMapList("voertuigen");
    }
}
