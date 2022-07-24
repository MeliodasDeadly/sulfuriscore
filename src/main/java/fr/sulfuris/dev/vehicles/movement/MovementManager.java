package fr.sulfuris.dev.vehicles.movement;

import fr.sulfuris.dev.vehicles.infrastructure.annotations.VersionSpecific;
import org.bukkit.entity.Player;

import static fr.sulfuris.dev.vehicles.infrastructure.modules.VersionModule.getServerVersion;

public class MovementManager {
        @VersionSpecific
    public static void MovementSelector(Player player) {
        if (getServerVersion().is1_16()) PacketHandler.movement_1_16(player);
    }
}
