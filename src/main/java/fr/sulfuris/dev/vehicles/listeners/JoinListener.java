package fr.sulfuris.dev.vehicles.listeners;

import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.PluginVersion;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.PluginUpdater;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVListener;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import fr.sulfuris.dev.vehicles.movement.MovementManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * On player join (a packet handler is assigned + update and language message if OP / with permission)
 */
public class JoinListener extends SulfuVListener {

    @EventHandler
    public void onJoinEventPlayer(PlayerJoinEvent event) {
        this.event = event;
        player = event.getPlayer();

        MovementManager.MovementSelector(player);

        if (ConfigModule.secretSettings.getMessagesLanguage().contains("ns")) {
            if (player.hasPermission("mtvehicles.language") || player.hasPermission("mtvehicles.admin")) {
                player.sendMessage(TextUtils.colorize("&cHey! You have not changed the language of the plugin yet. Do this by executing &4/vehicle language&c!"));
            }
        }

        if (!player.hasPermission("mtvehicles.update") || !(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.AUTO_UPDATE))
            return;

        if (!PluginVersion.getPluginVersion().isDev()) PluginUpdater.checkNewVersion(player);
    }
}
