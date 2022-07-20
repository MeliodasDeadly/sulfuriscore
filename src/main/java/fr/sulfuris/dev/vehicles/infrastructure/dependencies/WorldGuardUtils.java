package fr.sulfuris.dev.vehicles.infrastructure.dependencies;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.sulfuris.dev.vehicles.infrastructure.enums.SoftDependency;
import fr.sulfuris.dev.vehicles.infrastructure.modules.DependencyModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

/**
 * Methods for WorldGuard soft-dependency.<br>
 * <b>Do not initialise this class directly. Use {@link DependencyModule#worldGuard} instead.</b>
 */
public class WorldGuardUtils {
    //This must only be called if DependencyModule made sure that WorldGuard is installed.

    /**
     * List of all custom WorldGuard flags used by the plugin
     */
    public final List<String> flagList = Arrays.asList(
            "mtv-gasstation",
            "mtv-place",
            "mtv-enter",
            "mtv-pickup",
            "mtv-use-car",
            "mtv-use-hover",
            "mtv-user-tank",
            "mtv-use-helicopter",
            "mtv-use-airplane"
    );
    /**
     * WorldGuard instance
     */
    public WorldGuard instance = WorldGuard.getInstance();
    /**
     * WorldGuard flag registry
     */
    public FlagRegistry registry = instance.getFlagRegistry();

    /**
     * Default constructor which registers flags - <b>do not use this.</b><br>
     * Use {@link DependencyModule#worldGuard} instead.
     */
    public WorldGuardUtils() {
        registerFlags();
    }

    /**
     * Check whether a location is in a region with a (custom) flag of a specified state.
     *
     * @param loc       - Location which may be inside a region
     * @param flagName  - Name of the flag
     * @param flagState - State of the flag - specified by WorldGuard State enum (ALLOW/DENY)
     * @return True if location is in at least 1 region with the flag with the specified state.
     */
    public boolean isInRegionWithFlag(Location loc, String flagName, StateFlag.State flagState) {
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(loc.getWorld())).getApplicableRegions(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()));
        StateFlag flag = (StateFlag) registry.get(flagName);
        if (flag == null || regions.size() == 0) return false;

        boolean returns = false;
        for (ProtectedRegion region : regions) {
            StateFlag.State regionFlagState = region.getFlag(flag);
            if (regionFlagState != null)
                if (regionFlagState.equals(flagState)) returns = true;
        }
        return returns;
    }

    /**
     * Check whether a location is in a region with a (custom) flag of a specified state.
     *
     * @param loc       - Location which may be inside a region
     * @param flagName  - Name of the flag
     * @param flagState - State of the flag - specified by a boolean (true = ALLOW / false = DENY)
     * @return True if location is in at least 1 region with the flag with the specified state.
     */
    public boolean isInRegionWithFlag(Location loc, String flagName, boolean flagState) {
        StateFlag.State state = (flagState) ? StateFlag.State.ALLOW : StateFlag.State.DENY;
        return isInRegionWithFlag(loc, flagName, state);
    }

    private void registerFlags() {
        try {
            for (String flagName : flagList) {
                StateFlag flag = new StateFlag(flagName, true);
                registry.register(flag);
            }
        } catch (Exception e) {
            if (e instanceof FlagConflictException) { //The flags are already registered
                if (areFlagsOkay())
                    Bukkit.getLogger().warning("[MTVehicles] Another plugin has already registered MTVehicles' custom WorldGuard flags. They might interfere with each other!");
                else disableDependency();
            } else if (e instanceof IllegalStateException) { //New flags cannot be registered at this time
                if (areFlagsOkay())
                    Bukkit.getLogger().warning("[MTVehicles] MTVehicles' custom WorldGuard flags have already been registered and/or are also used by another plugin. This might happen because you've just reloaded the plugin with PlugMan.");
                else disableDependency();
            } else disableDependency();
        }
    }

    private void disableDependency() {
        Bukkit.getLogger().severe("[MTVehicles] Custom WorldGuard flags could not be created for MTVehicles. Disabling as a soft-dependcy... (If you've just reloaded the plugin with PlugMan, try restarting the server.)");
        DependencyModule.disableDependency(SoftDependency.WORLD_GUARD);
    }

    /**
     * Check whether all custom flags are set up correctly.
     */
    private boolean areFlagsOkay() {
        boolean allAreOkay = true;
        for (String flagName : flagList) {
            Flag<?> existing = registry.get(flagName);
            if (!(existing instanceof StateFlag)) allAreOkay = false;
        }
        return allAreOkay;
    }
}
