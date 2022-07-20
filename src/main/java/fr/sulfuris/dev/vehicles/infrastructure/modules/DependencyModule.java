package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.vehicles.infrastructure.dependencies.WorldGuardUtils;
import fr.sulfuris.dev.vehicles.infrastructure.enums.SoftDependency;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Module for managing soft-dependencies.
 */
public class DependencyModule {
    /**
     * List of all enabled soft-dependencies.
     */
    public static List<SoftDependency> loadedDependencies = new ArrayList<>();
    /**
     * WorldGuard's Utils class
     */
    public static WorldGuardUtils worldGuard;
    private static @Getter
    @Setter
    DependencyModule instance;

    public DependencyModule() {
        final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            if (serverVersion.equals("v1_12_R1"))
                Bukkit.getLogger().warning("[MTVehicles] Cannot load WorldGuard as a soft-dependency in 1.12. (1.12 uses 6.x.x API whereas newer versions use 7.x.x)");
            else {
                try {
                    worldGuard = new WorldGuardUtils();
                    loadedDependencies.add(SoftDependency.WORLD_GUARD);
                } catch (NoClassDefFoundError e) {
                    Bukkit.getLogger().severe("[MTVehicles] An error occurred whilst loading WorldGuard as a soft-dependency. (Make sure you're using a version 7.x.x, or try restarting the server.)");
                }
            }
        }
    }

    /**
     * Check whether a soft-dependency is installed and enabled.
     *
     * @param dependency Soft-dependency supported by the plugin
     * @return True if the soft-dependency is enabled.
     * @see SoftDependency
     */
    public static boolean isDependencyEnabled(SoftDependency dependency) {
        return loadedDependencies.contains(dependency);
    }

    /**
     * Disable a soft-dependency (usually due to a fatal bug or incompatible server version)
     *
     * @param dependency Soft-dependency supported by the plugin
     * @see SoftDependency
     */
    public static void disableDependency(SoftDependency dependency) {
        if (isDependencyEnabled(dependency)) loadedDependencies.remove(dependency);
    }
}
