package fr.sulfuris.dev.vehicles.infrastructure.enums;

public enum SoftDependency {
    WORLD_GUARD("WorldGuard"),
    VAULT("Vault"),
    PLACEHOLDER_API("PlaceholderAPI");

    final private String name;

    private SoftDependency(String name) {
        this.name = name;
    }

        public String getName() {
            return name;
        }
}
