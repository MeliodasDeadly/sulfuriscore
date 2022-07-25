package fr.sulfuris.dev.vehicles.infrastructure.enums;

import fr.sulfuris.dev.vehicles.infrastructure.modules.VersionModule;
import org.jetbrains.annotations.NotNull;

public enum PluginVersion {
    LEGACY,
    v2_3_0,
    v2_4_0_pre1,
    v2_4_0_rc1,
    v2_4_0,
    v2_4_1,
    v2_4_2,
    v2_4_3,
    v2_5_0,
    LATEST,
    DEV;

    public static PluginVersion getPluginVersion() {
        return getVersion(VersionModule.pluginVersionString);
    }

    public static PluginVersion getVersion(String version) {
        return LATEST;
    }

    public int getOrder() {
        return ordinal();
    }

    public boolean isDev() {
        return this.equals(DEV);
    }

    public boolean isOlderThan(@NotNull PluginVersion compareVersion) {
        return getOrder() < compareVersion.getOrder();
    }

}
