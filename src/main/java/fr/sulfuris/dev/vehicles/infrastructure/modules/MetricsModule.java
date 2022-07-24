package fr.sulfuris.dev.vehicles.infrastructure.modules;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.Metrics;
import lombok.Getter;
import lombok.Setter;

public class MetricsModule {
    private static @Getter
    @Setter
    MetricsModule instance;

    public MetricsModule() {
        Metrics metrics = new Metrics(main.instance, 5932);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> {
            return ConfigModule.secretSettings.getMessagesLanguage();
        }));
        metrics.addCustomChart(new Metrics.SimplePie("used_driveUp", () -> {
            String returns;
            switch (ConfigModule.defaultConfig.driveUpSlabs()) {
                case SLABS:
                    returns = "slabs";
                    break;
                case BLOCKS:
                    returns = "blocks";
                    break;
                case BOTH:
                default:
                    returns = "both";
                    break;
            }
            return returns;
        }));
    }
}
