package fr.sulfuris.dev.vehicles.infrastructure.dataconfig;

import fr.sulfuris.dev.vehicles.infrastructure.enums.*;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.Config;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import fr.sulfuris.dev.vehicles.infrastructure.modules.DependencyModule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultConfig extends Config {
    private GasStationConfig gasStations = new GasStationConfig();

        public DefaultConfig() {
            super(ConfigType.DEFAULT);
        }

    @Deprecated
    public String getMessage(String key) {
        return TextUtils.colorize(this.getConfiguration().getString(key));
    }

    public Object get(Option configOption) {
        return this.getConfiguration().get(configOption.getPath());
    }


    public boolean hasOldVersionChecking() {
        return this.getConfiguration().get("Config-Versie") != null;
    }


    public DriveUp driveUpSlabs() {
        DriveUp returns = DriveUp.BOTH;
        try {
            switch (Objects.requireNonNull(get(Option.DRIVE_UP).toString().toLowerCase())) {
                case "blocks":
                case "block":
                    returns = DriveUp.BLOCKS;
                    break;
                case "slabs":
                case "slab":
                    returns = DriveUp.SLABS;
                    break;
            }
        } catch (NullPointerException e) {
        }
        return returns;
    }

    public boolean canUseJerryCan(Location loc) {
        if (!gasStations.areGasStationsEnabled()) return true;

        if (DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-gasstation", false)) return false;

        if (gasStations.canUseJerryCanOutsideOfGasStation()) return true;

        return gasStations.isInsideGasStation(loc);
    }

    public boolean canUseJerryCan(Player p) {
        return canUseJerryCan(p.getLocation());
    }

    public boolean canFillJerryCans(Player p, Location loc) {
        if (!gasStations.areGasStationsEnabled()) return false;
        if (!gasStations.isFillJerryCansEnabled()) return false;
        if (!gasStations.isInsideGasStation(loc)) return false;

        if (!gasStations.hasFillJerryCansPermission(p)) {
            p.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.NO_PERMISSION)));
            return false;
        }
        return true;
    }

    public boolean jerryCanPlaySound() {
        return (boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_PLAY_SOUND);
    }

    public boolean isFillJerryCansLeverEnabled() {
        return (boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_LEVER);
    }

    public boolean isFillJerryCansTripwireHookEnabled() {
        return (boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_TRIPWIRE_HOOK);
    }

    public boolean isFillJerryCanPriceEnabled() {
        if (!gasStations.areGasStationsEnabled()) return false;
        if (!gasStations.isFillJerryCansEnabled()) return false;
        if (!DependencyModule.isDependencyEnabled(SoftDependency.VAULT))
            return false;
        return (boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_PRICE_ENABLED);
    }

    public double getFillJerryCanPrice() {
        if ((double) get(Option.GAS_STATIONS_FILL_JERRYCANS_PRICE_PER_LITRE) <= 0)
            return 30.0;
        else return (double) get(Option.GAS_STATIONS_FILL_JERRYCANS_PRICE_PER_LITRE);
    }

    public boolean isWorldDisabled(String worldName) {
        if (getDisabledWorlds().isEmpty()) return false;
        return getDisabledWorlds().contains(worldName);
    }


    private List<String> getDisabledWorlds() {
        return (List<String>) get(Option.DISABLED_WORLDS);
    }

    public boolean isBlockWhitelistEnabled() {
        return (boolean) get(Option.BLOCK_WHITELIST_ENABLED);
    }


    public List<Material> blockWhiteList() {
        return ((List<String>) get(Option.BLOCK_WHITELIST_LIST)).stream().map(Material::getMaterial).collect(Collectors.toList());
    }

    private RegionAction.ListType getRegionActionListType(RegionAction action) {
        String configOption = "disabled";
        switch (action) {
            case PLACE:
                configOption = get(Option.REGION_ACTIONS_PLACE).toString().toLowerCase(Locale.ROOT);
                break;
            case PICKUP:
                configOption = get(Option.REGION_ACTIONS_PICKUP).toString().toLowerCase(Locale.ROOT);
                break;
            case ENTER:
                configOption = get(Option.REGION_ACTIONS_ENTER).toString().toLowerCase(Locale.ROOT);
                break;
        }
        if (configOption.equalsIgnoreCase("whitelist")) return RegionAction.ListType.WHITELIST;
        else if (configOption.equalsIgnoreCase("blacklist")) return RegionAction.ListType.BLACKLIST;
        else return RegionAction.ListType.DISABLED;
    }


    public boolean canProceedWithAction(RegionAction action, VehicleType vehicleType, Location loc) {
        if (isWorldDisabled(loc.getWorld().getName())) return false;

        if (!DependencyModule.isDependencyEnabled(SoftDependency.WORLD_GUARD)) return true;

        if (vehicleType.isUsageDisabled(loc)) return false;

        boolean returns = true;
        RegionAction.ListType listType = getRegionActionListType(action);
        if (!listType.isEnabled()) return true;

        boolean isWhitelist = listType.isWhitelist();
        boolean isBlacklist = listType.isBlacklist();
        switch (action) {
            case PLACE:
                if (isWhitelist)
                    returns = DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-place", true);
                else if (isBlacklist)
                    returns = !DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-place", false);
                break;
            case PICKUP:
                if (isWhitelist)
                    returns = DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-pickup", true);
                else if (isBlacklist)
                    returns = !DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-pickup", false);
                break;
            case ENTER:
                if (isWhitelist)
                    returns = DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-enter", true);
                else if (isBlacklist)
                    returns = !DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-enter", false);
                break;
        }
        return returns;
    }

    public boolean usePlayerFacingDriving() {
        return (boolean) get(Option.USE_PLAYER_FACING);
    }


    public enum Option {
        AUTO_UPDATE("autoUpdate", true),
        VEHICLE_MENU_SIZE("vehicleMenuSize", 3),
        HELICOPTER_BLADES_ALWAYS_ON("helicopterBladesAlwaysOn", true),
        DISABLE_PICKUP_FROM_WATER("disablePickupFromWater", false),
        TRUNK_ENABLED("trunkEnabled", true),
        PUT_ONESELF_AS_OWNER("putOneselfAsOwner", false),
        MAX_FLYING_HEIGHT("maxFlyingHeight", 150),
        TAKE_OFF_SPEED("takeOffSpeed", 0.4),
        CAR_PICKUP("carPickup", false),
        FUEL_ENABLED("fuelEnabled", true),
        FUEL_MULTIPLIER("fuelMultiplier", 1),
        JERRYCANS("jerrycans", new ArrayList<>(Arrays.asList(25, 50, 75))),
        DAMAGE_ENABLED("damageEnabled", false),
        DAMAGE_MULTIPLIER("damageMultiplier", 0.5),
        HORN_COOLDOWN("hornCooldown", 5),
        HORN_TYPE("hornType", "minetopiaclassic.horn1"),
        TANK_TNT("tankTNT", false),
        TANK_COOLDOWN("tankCooldown", 10),
        DRIVE_UP("driveUp", "both"),
        EXTREME_HELICOPTER_FALL("extremeHelicopterFall", false),
        HELICOPTER_FALL_DAMAGE("helicopterFallDamage", 40.0),
        DRIVE_ON_CARPETS("driveOnCarpets", true),
        BLOCK_WHITELIST_ENABLED("blockWhitelist.enabled", false),
        BLOCK_WHITELIST_LIST("blockWhitelist.list", new ArrayList<>().add("GRAY_CONCRETE")),
        DISABLED_WORLDS("disabledWorlds", new ArrayList<>()),
        GAS_STATIONS_ENABLED("gasStations.enabled", false),
        GAS_STATIONS_CAN_USE_JERRYCAN_OUTSIDE_OF_GAS_STATION("gasStations.canUseJerryCanOutsideOfGasStation", true),
        GAS_STATIONS_FILL_JERRYCANS_ENABLED("gasStations.fillJerryCans.enabled", true),
        GAS_STATIONS_FILL_JERRYCANS_NEED_PERMISSION("gasStations.fillJerryCans.needPermission", false),
        GAS_STATIONS_FILL_JERRYCANS_PLAY_SOUND("gasStations.fillJerryCans.playSound", true),
        GAS_STATIONS_FILL_JERRYCANS_LEVER("gasStations.fillJerryCans.lever", true),
        GAS_STATIONS_FILL_JERRYCANS_TRIPWIRE_HOOK("gasStations.fillJerryCans.tripwireHook", false),
        GAS_STATIONS_FILL_JERRYCANS_PRICE_ENABLED("gasStations.fillJerryCans.price.enabled", true),
        GAS_STATIONS_FILL_JERRYCANS_PRICE_PER_LITRE("gasStations.fillJerryCans.price.pricePerLitre", 30.0),
        REGION_ACTIONS_PLACE("regionActions.place", "disabled"),
        REGION_ACTIONS_ENTER("regionActions.enter", "disabled"),
        REGION_ACTIONS_PICKUP("regionActions.pickup", "disabled"),
        USE_PLAYER_FACING("usePlayerFacing", false);

        final private String path;
        final private Object defaultValue;

        private Option(String path, Object defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public String getPath() {
            return path;
        }

        public Type getValueType() {
            return this.getDefaultValue().getClass();
        }
    }

    private class GasStationConfig {

        private boolean areGasStationsEnabled() {
            if (!DependencyModule.isDependencyEnabled(SoftDependency.WORLD_GUARD))
                return false;
            return (boolean) get(Option.GAS_STATIONS_ENABLED);
        }

        private boolean canUseJerryCanOutsideOfGasStation() {
            return (boolean) get(Option.GAS_STATIONS_CAN_USE_JERRYCAN_OUTSIDE_OF_GAS_STATION);
        }

        private boolean isInsideGasStation(Location loc) {
            return DependencyModule.worldGuard.isInRegionWithFlag(loc, "mtv-gasstation", true);
        }

        private boolean isFillJerryCansEnabled() {
            return (boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_ENABLED);
        }

        private boolean hasFillJerryCansPermission(Player p) {
            if (!(boolean) get(Option.GAS_STATIONS_FILL_JERRYCANS_NEED_PERMISSION))
                return true;
            return p.hasPermission("svehicles.filljerrycans");
        }

    }

}
