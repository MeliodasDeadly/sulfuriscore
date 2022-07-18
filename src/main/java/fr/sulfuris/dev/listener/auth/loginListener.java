package fr.sulfuris.dev.listener.auth;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.definers.databaseUser;
import fr.sulfuris.dev.handlers.database.database;
import fr.sulfuris.dev.main;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.graalvm.compiler.core.common.util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.EventListener;

public class loginListener implements Listener {
    private final main plugin;
    private static TimeProvider timeProvider = new SystemTimeProvider();
    private static CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private static CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

    public loginListener(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    private boolean isOnLogin(Player player) {
        return player.getPersistentDataContainer().get(new NamespacedKey(plugin, "isLogged"), PersistentDataType.STRING).equals("true");
    }

    @EventHandler
    public void onPlayerInterract(PlayerInteractEvent event) {
        event.setCancelled(isOnLogin(event.getPlayer()));
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        event.setCancelled(isOnLogin(event.getPlayer()));
    }
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(isOnLogin(event.getPlayer()));
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(isOnLogin(event.getPlayer()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws QrGenerationException {
        Player player = event.getPlayer();
        String ip = String.valueOf(player.getAddress().getAddress()).substring(1);;
        databaseUser user = databaseUser.getFromName(player.getName());
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey loginKey = new NamespacedKey(plugin, "islogged");
        if(!pdc.has(loginKey))
            pdc.set(loginKey, PersistentDataType.STRING, "false");
        if(ip.equals(user.getLastLoginIp())){
            player.getPersistentDataContainer().set(new NamespacedKey(plugin, "isLogged"), PersistentDataType.STRING, "true");
            player.sendMessage(ChatColor.GREEN + "Vous êtes connecté avec votre adresse IP");
        } else {
            player.getPersistentDataContainer().set(new NamespacedKey(plugin, "isLogged"), PersistentDataType.STRING, "false");
            player.sendMessage(ChatColor.RED + "Vous n'êtes pas connecté");
        }
    }
}
