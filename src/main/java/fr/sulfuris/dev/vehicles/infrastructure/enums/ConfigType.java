package fr.sulfuris.dev.vehicles.infrastructure.enums;

public enum ConfigType {
    DEFAULT("config.yml"),
    VEHICLES("vehicles.yml"),
    VEHICLE_DATA("vehicleData.yml"),
    SUPERSECRETSETTINGS("supersecretsettings.yml"),
    MESSAGES;

    private String fileName = null;

    private ConfigType() {
    }

    private ConfigType(String fileName) {
        this.fileName = fileName;
    }

        public boolean isMessages() {
            return this.equals(MESSAGES);
        }

    public String getFileName() {
        return this.fileName;
    }
}
