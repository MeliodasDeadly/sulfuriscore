package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.enums.VehicleType;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VehicleDataConfig extends Config {
        public VehicleDataConfig() {
            super(ConfigType.VEHICLE_DATA);
        }

    public Object get(String licensePlate, Option dataOption) {
        return this.getConfiguration().get(String.format("vehicle.%s.%s", licensePlate, dataOption.getPath()));
    }

    public void set(String licensePlate, Option dataOption, Object value) {
        this.getConfiguration().set(String.format("vehicle.%s.%s", licensePlate, dataOption.getPath()), value);
        save();
    }

    public void delete(String licensePlate) throws IllegalStateException {
        final String path = "vehicle." + licensePlate;
        if (!getConfiguration().isSet(path))
            throw new IllegalStateException("An error occurred while trying to delete a vehicle. Vehicle is already deleted.");
        getConfiguration().set(path, null);
        save();
    }


    public boolean isEmpty() {
        return getConfiguration().getConfigurationSection("vehicle") == null;
    }

    public ConfigurationSection getVehicles() {
        return getConfiguration().getConfigurationSection("vehicle");
    }

    public int getDamage(String licensePlate) {
        return (int) get(licensePlate, Option.SKIN_DAMAGE);
    }

    public int getDamage(Vehicle vehicle) {
        return getDamage(vehicle.getLicensePlate());
    }

    public List<String> getMembers(String licensePlate) {
        if (get(licensePlate, Option.MEMBERS) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.MEMBERS);
    }

    public List<String> getRiders(String licensePlate) {
        if (get(licensePlate, Option.RIDERS) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.RIDERS);
    }

    public List<String> getTrunkData(String licensePlate) {
        if (get(licensePlate, Option.TRUNK_DATA) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.TRUNK_DATA);
    }

    public VehicleType getType(String licensePlate) {
        try {
            return VehicleType.valueOf(get(licensePlate, Option.VEHICLE_TYPE).toString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            main.logSevere("An error occurred while setting a vehicle's type. Using default (CAR)...");
            return VehicleType.CAR;
        }
    }

    public boolean isHornEnabled(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        if (!isHornSet(license)) setInitialHorn(license);
        return getConfiguration().getBoolean(path);
    }

    public boolean isHornSet(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        return getConfiguration().isSet(path);
    }

    public void setInitialHorn(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        boolean state = VehicleUtils.getHornByDamage(getDamage(license));
        getConfiguration().set(path, state);
        save();
    }


    public double getHealth(String license) {
        final String path = "vehicle." + license + ".health";
        if (!isHealthSet(license)) setInitialHealth(license);
        return getConfiguration().getDouble(path);
    }

    public boolean isHealthSet(String license) {
        final String path = "vehicle." + license + ".health";
        return getConfiguration().isSet(path);
    }

    public void setInitialHealth(String license) {
        final String path = "vehicle." + license + ".health";
        final int damage = getConfiguration().getInt("vehicle." + license + ".skinDamage");
        double state = VehicleUtils.getMaxHealthByDamage(damage);
        getConfiguration().set(path, state);
        save();
    }

    public void damageVehicle(String license, double damage) {
        final String path = "vehicle." + license + ".health";
        double h = getHealth(license) - damage;
        final double health = (h > 0) ? h : 0.0;
        getConfiguration().set(path, health);
        save();
    }

    public void setHealth(String license, double health) {
        final String path = "vehicle." + license + ".health";
        getConfiguration().set(path, health);
        save();
    }

    public int getNumberOfOwnedVehicles(Player p) {
        final String playerUUID = p.getUniqueId().toString();
        int owned;
        Map<String, Object> vehicleData = getConfiguration().getValues(true);
        owned = (int) vehicleData.keySet().stream().filter(key -> key.contains(".owner") && String.valueOf(vehicleData.get(key)).equals(playerUUID)).count();
        return owned;
    }

    public enum Option {
        NAME("name"),
        VEHICLE_TYPE("vehicleType"),
        SKIN_ITEM("skinItem"),
        SKIN_DAMAGE("skinDamage"),
        OWNER("owner"),
        RIDERS("riders"),
        MEMBERS("members"),
        FUEL_ENABLED("benzineEnabled"),
        FUEL("benzine"),
        FUEL_USAGE("benzineVerbruik"),
        BRAKING_SPEED("brakingSpeed"),
        FRICTION_SPEED("aftrekkenSpeed"),
        ACCELARATION_SPEED("acceleratieSpeed"),
        MAX_SPEED("maxSpeed"),
        MAX_SPEED_BACKWARDS("maxSpeedBackwards"),
        ROTATION_SPEED("rotateSpeed"),
        TRUNK_ENABLED("kofferbak"),
        TRUNK_ROWS("kofferbakRows"),
        TRUNK_DATA("kofferbakData"),
        IS_OPEN("isOpen"),
        IS_GLOWING("isGlow"),
        HORN_ENABLED("hornEnabled"),
        HEALTH("health"),
        NBT_VALUE("nbtValue");

        final private String path;

        private Option(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
