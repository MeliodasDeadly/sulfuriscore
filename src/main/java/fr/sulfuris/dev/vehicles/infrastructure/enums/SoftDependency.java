package fr.sulfuris.dev.vehicles.infrastructure.enums;

/**
 * Supported soft-dependencies
 */
public enum SoftDependency {
    WORLD_GUARD("WorldGuard"),
    VAULT("Vault"),
    PLACEHOLDER_API("PlaceholderAPI");

    final private String name;

    private SoftDependency(String name) {
        this.name = name;
    }

    /**
     * Get soft-dependency's name
     */
    public String getName() {
        return name;
    }
}
