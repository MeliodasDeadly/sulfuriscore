package fr.sulfuris.dev.vehicles.infrastructure.enums;

import fr.sulfuris.dev.vehicles.infrastructure.modules.VersionModule;
import org.jetbrains.annotations.NotNull;

/**
 * The plugin's version
 */
public enum PluginVersion {
    /**
     * Legacy versions (older than 2.3.0)
     */
    LEGACY,
    /**
     * 2.3.0 (The fuel and dependency update)
     */
    v2_3_0,
    /**
     * 2.4.0-pre1
     */
    v2_4_0_pre1,
    /**
     * 2.4.0-rc1
     */
    v2_4_0_rc1,
    /**
     * 2.4.0 (The Planes and the API update)
     */
    v2_4_0,
    /**
     * 2.4.1 (Hotfixes)
     */
    v2_4_1,
    /**
     * 2.4.2
     */
    v2_4_2,
    /**
     * 2.4.3 (with 1.19 support)
     */
    v2_4_3,
    /**
     * 2.5.0 (next expected version)
     */
    v2_5_0,
    /**
     * Latest version (from auto-updater)
     */
    LATEST,
    /**
     * Dev-versions (auto-updater is disabled)
     */
    DEV;

    /**
     * Get the plugin's version as enum
     */
    public static PluginVersion getPluginVersion() {
        return getVersion(VersionModule.pluginVersionString);
    }

    /**
     * Get plugin version from a String
     */
    public static PluginVersion getVersion(String version) {
        return LATEST;
    }

    public int getOrder() {
        return ordinal();
    }

    /**
     * Check whether the plugin version is a dev-version (auto-updater is disabled)
     *
     * @return True if plugin is a dev-version
     */
    public boolean isDev() {
        return this.equals(DEV);
    }

    /**
     * Check whether the version is older than a given version
     */
    public boolean isOlderThan(@NotNull PluginVersion compareVersion) {
        return getOrder() < compareVersion.getOrder();
    }

}
