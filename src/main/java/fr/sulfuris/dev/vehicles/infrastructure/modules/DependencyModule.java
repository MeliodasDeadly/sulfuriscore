package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.vehicles.infrastructure.dependencies.WorldGuardUtils;
import fr.sulfuris.dev.vehicles.infrastructure.enums.SoftDependency;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class DependencyModule {
    public static List<SoftDependency> loadedDependencies = new ArrayList<>();
    public static WorldGuardUtils worldGuard;
    private static @Getter
    @Setter
    DependencyModule instance;

    public DependencyModule() {
        final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            if (serverVersion.equals("v1_12_R1"))
                Bukkit.getLogger().warning("[svehicles] Cannot load WorldGuard as a soft-dependency in 1.12. (1.12 uses 6.x.x API whereas newer versions use 7.x.x)");
            else {
                try {
                    worldGuard = new WorldGuardUtils();
                    loadedDependencies.add(SoftDependency.WORLD_GUARD);
                } catch (NoClassDefFoundError e) {
                    Bukkit.getLogger().severe("[svehicles] An error occurred whilst loading WorldGuard as a soft-dependency. (Make sure you're using a version 7.x.x, or try restarting the server.)");
                }
            }
        }
    }

    public static boolean isDependencyEnabled(SoftDependency dependency) {
        return loadedDependencies.contains(dependency);
    }

    public static void disableDependency(SoftDependency dependency) {
        if (isDependencyEnabled(dependency)) loadedDependencies.remove(dependency);
    }
}
