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

public class WorldGuardUtils {

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
    public WorldGuard instance = WorldGuard.getInstance();
    public FlagRegistry registry = instance.getFlagRegistry();

    public WorldGuardUtils() {
        registerFlags();
    }

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
            if (e instanceof FlagConflictException) {
                if (areFlagsOkay())
                    Bukkit.getLogger().warning("[svehicles] Another plugin has already registered svehicles' custom WorldGuard flags. They might interfere with each other!");
                else disableDependency();
            } else if (e instanceof IllegalStateException) {
                if (areFlagsOkay())
                    Bukkit.getLogger().warning("[svehicles] svehicles' custom WorldGuard flags have already been registered and/or are also used by another plugin. This might happen because you've just reloaded the plugin with PlugMan.");
                else disableDependency();
            } else disableDependency();
        }
    }

    private void disableDependency() {
        Bukkit.getLogger().severe("[svehicles] Custom WorldGuard flags could not be created for svehicles. Disabling as a soft-dependcy... (If you've just reloaded the plugin with PlugMan, try restarting the server.)");
        DependencyModule.disableDependency(SoftDependency.WORLD_GUARD);
    }

    private boolean areFlagsOkay() {
        boolean allAreOkay = true;
        for (String flagName : flagList) {
            Flag<?> existing = registry.get(flagName);
            if (!(existing instanceof StateFlag)) allAreOkay = false;
        }
        return allAreOkay;
    }
}
