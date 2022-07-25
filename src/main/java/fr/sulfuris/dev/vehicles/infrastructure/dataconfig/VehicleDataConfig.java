package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ConfigType;
import fr.sulfuris.dev.vehicles.infrastructure.enums.VehicleType;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Methods for supersecretsettings.yml.<br>
 * <b>Do not initialise this class directly. Use {@link ConfigModule#vehicleDataConfig} instead.</b>
 */
public class VehicleDataConfig extends Config {
    /**
     * Default constructor - <b>do not use this.</b><br>
     * Use {@link ConfigModule#vehicleDataConfig} instead.
     */
    public VehicleDataConfig() {
        super(ConfigType.VEHICLE_DATA);
    }

    /**
     * Get a data option of a vehicle from vehicleData
     *
     * @param licensePlate Vehicle's license plate
     * @param dataOption   Data option of a vehicle
     * @return Value of the option (as Object)
     */
    public Object get(String licensePlate, Option dataOption) {
        return this.getConfiguration().get(String.format("vehicle.%s.%s", licensePlate, dataOption.getPath()));
    }

    /**
     * Set a data option of a vehicle to vehicleData
     *
     * @param licensePlate Vehicle's license plate
     * @param dataOption   Data option of a vehicle
     * @param value        New value of the option (should be the same type!)
     */
    public void set(String licensePlate, Option dataOption, Object value) {
        this.getConfiguration().set(String.format("vehicle.%s.%s", licensePlate, dataOption.getPath()), value);
        save();
    }

    /**
     * Delete a vehicle from vehicleData
     *
     * @param licensePlate Vehicle's license plate
     * @throws IllegalStateException If vehicle is already deleted.
     */
    public void delete(String licensePlate) throws IllegalStateException {
        final String path = "vehicle." + licensePlate;
        if (!getConfiguration().isSet(path))
            throw new IllegalStateException("An error occurred while trying to delete a vehicle. Vehicle is already deleted.");
        getConfiguration().set(path, null);
        save();
    }


    /**
     * Whether the vehicleData.yml file is empty
     */
    public boolean isEmpty() {
        return getConfiguration().getConfigurationSection("vehicle") == null;
    }

    /**
     * Get (basically) the whole file.
     */
    public ConfigurationSection getVehicles() {
        return getConfiguration().getConfigurationSection("vehicle");
    }

    /**
     * Get the durability of a vehicle item.
     *
     * @param licensePlate Vehicle's license plate
     */
    public int getDamage(String licensePlate) {
        return (int) get(licensePlate, Option.SKIN_DAMAGE);
    }

    /**
     * Get the durability of a vehicle item.
     *
     * @param vehicle Vehicle
     */
    public int getDamage(Vehicle vehicle) {
        return getDamage(vehicle.getLicensePlate());
    }

    /**
     * Get UUIDs of players which may sit in the vehicle
     *
     * @param licensePlate Vehicle's license plate
     */
    public List<String> getMembers(String licensePlate) {
        if (get(licensePlate, Option.MEMBERS) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.MEMBERS);
    }

    /**
     * Get UUIDs of players which may steer the vehicle
     *
     * @param licensePlate Vehicle's license plate
     */
    public List<String> getRiders(String licensePlate) {
        if (get(licensePlate, Option.RIDERS) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.RIDERS);
    }

    /**
     * Get data of the vehicle's trunk
     *
     * @param licensePlate Vehicle's license plate
     * @return List of items in the trunk (as Strings)
     */
    public List<String> getTrunkData(String licensePlate) {
        if (get(licensePlate, Option.TRUNK_DATA) == null) return new ArrayList<>();
        return (List<String>) get(licensePlate, Option.TRUNK_DATA);
    }

    /**
     * Get the type (enum) of the vehicle.
     *
     * @param licensePlate Vehicle's license plate
     */
    public VehicleType getType(String licensePlate) {
        try {
            return VehicleType.valueOf(get(licensePlate, Option.VEHICLE_TYPE).toString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            main.logSevere("An error occurred while setting a vehicle's type. Using default (CAR)...");
            return VehicleType.CAR;
        }
    }

    /**
     * Check whether horn may be used in a vehicle
     *
     * @param license Vehicle's license plate
     */
    public boolean isHornEnabled(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        if (!isHornSet(license)) setInitialHorn(license);
        return getConfiguration().getBoolean(path);
    }

    /**
     * Check whether 'hornEnabled' data option is set (it might not be if player was using an older version of MTV before)
     *
     * @param license Vehicle's license plate
     */
    public boolean isHornSet(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        return getConfiguration().isSet(path);
    }

    /**
     * Set the 'hornEnabled' data option default value.
     *
     * @param license Vehicle's license plate
     */
    public void setInitialHorn(String license) {
        final String path = "vehicle." + license + ".hornEnabled";
        boolean state = VehicleUtils.getHornByDamage(getDamage(license));
        getConfiguration().set(path, state);
        save();
    }


    /**
     * Get health of a vehicle
     *
     * @param license Vehicle's license plate
     */
    public double getHealth(String license) {
        final String path = "vehicle." + license + ".health";
        if (!isHealthSet(license)) setInitialHealth(license);
        return getConfiguration().getDouble(path);
    }

    /**
     * Check whether 'health' data option is set (it might not be if player was using an older version of MTV before)
     *
     * @param license Vehicle's license plate
     */
    public boolean isHealthSet(String license) {
        final String path = "vehicle." + license + ".health";
        return getConfiguration().isSet(path);
    }

    /**
     * Set the 'health' data option default value.
     *
     * @param license Vehicle's license plate
     */
    public void setInitialHealth(String license) {
        final String path = "vehicle." + license + ".health";
        final int damage = getConfiguration().getInt("vehicle." + license + ".skinDamage");
        double state = VehicleUtils.getMaxHealthByDamage(damage);
        getConfiguration().set(path, state);
        save();
    }

    /**
     * Damage a vehicle.
     *
     * @param license Vehicle's license plate
     * @param damage  Amount of damage
     */
    public void damageVehicle(String license, double damage) {
        final String path = "vehicle." + license + ".health";
        double h = getHealth(license) - damage;
        final double health = (h > 0) ? h : 0.0;
        getConfiguration().set(path, health);
        save();
    }

    /**
     * Set health of a vehicle
     *
     * @param license Vehicle's license plate
     * @param health  New health
     */
    public void setHealth(String license, double health) {
        final String path = "vehicle." + license + ".health";
        getConfiguration().set(path, health);
        save();
    }

    /**
     * Get number of vehicles owned by a player
     *
     * @param p Player
     */
    public int getNumberOfOwnedVehicles(Player p) {
        final String playerUUID = p.getUniqueId().toString();
        int owned;
        Map<String, Object> vehicleData = getConfiguration().getValues(true);
        owned = (int) vehicleData.keySet().stream().filter(key -> key.contains(".owner") && String.valueOf(vehicleData.get(key)).equals(playerUUID)).count();
        return owned;
    }

    /**
     * Options available in vehicle data file
     */
    public enum Option {
        NAME("name"),
        VEHICLE_TYPE("vehicleType"),
        SKIN_ITEM("skinItem"),
        SKIN_DAMAGE("skinDamage"),
        OWNER("owner"),
        RIDERS("riders"),
        MEMBERS("members"),
        /**
         * Can be found as 'benzineEnabled' in vehicleData.yml
         */
        FUEL_ENABLED("benzineEnabled"),
        /**
         * Can be found as 'fuel' in vehicleData.yml
         */
        FUEL("benzine"),
        /**
         * Can be found as 'benzineVerbruik' in vehicleData.yml
         */
        FUEL_USAGE("benzineVerbruik"),
        BRAKING_SPEED("brakingSpeed"),
        /**
         * Can be found as 'aftrekkenSpeed' in vehicleData.yml
         */
        FRICTION_SPEED("aftrekkenSpeed"),
        ACCELARATION_SPEED("acceleratieSpeed"),
        MAX_SPEED("maxSpeed"),
        MAX_SPEED_BACKWARDS("maxSpeedBackwards"),
        ROTATION_SPEED("rotateSpeed"),
        /**
         * Can be found as 'kofferbak' in vehicleData.yml
         */
        TRUNK_ENABLED("kofferbak"),
        /**
         * Can be found as 'kofferbakRows' in vehicleData.yml
         */
        TRUNK_ROWS("kofferbakRows"),
        /**
         * Can be found as 'kofferbakData' in vehicleData.yml
         */
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

        /**
         * Get string path of option
         *
         * @return Path of option
         */
        public String getPath() {
            return path;
        }
    }
}
