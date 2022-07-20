package fr.sulfuris.dev.listener;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.sulfuris.dev.Main;
import fr.sulfuris.dev.data.DatabaseUser;
import fr.sulfuris.dev.data.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;

public class Joinlistener implements Listener {

    private Main plugin;

    public Joinlistener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static ArrayList<Player> loginPlayers = new ArrayList<>();

    @EventHandler
    public static void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        if (!loginPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onPlayerInterract(PlayerInteractEvent event) {
        if (!loginPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onPlayerDisconnect(org.bukkit.event.player.PlayerQuitEvent event) {
        if (loginPlayers.contains(event.getPlayer())) {
            loginPlayers.remove(event.getPlayer());
        }
    }

    @EventHandler
    public static void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (!loginPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
            String message = event.getMessage();
            DatabaseUser user = DatabaseUser.getFromName(event.getPlayer().getName());
            BCrypt.Result result = BCrypt.verifyer().verify(message.toCharArray(), user.getPassword());
            if (result.verified) {
                loginPlayers.add(event.getPlayer());
                event.getPlayer().sendMessage(Utils.chat("&e&lSulfuris&6&lRP &8&l>> &2Vous avez été authentifié, bon jeu!"));
                user.setLastLoginIp(event.getPlayer().getAddress().toString().substring(1).split(":")[0]);
            } else {
                event.getPlayer().kickPlayer(Utils.chat("Mot de passe incorrect"));
            }
        }
    }

    @EventHandler
    public static void onPlayerCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        if (!loginPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), -256, 68, -282));
        new Thread() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                PersistentDataContainer data = player.getPersistentDataContainer();
                try {
                    if (!loginPlayers.contains(player)) {
                        DatabaseUser user = DatabaseUser.getFromName(event.getPlayer().getName());
                        if (player.getAddress().toString().substring(1).split(":")[0].equals(user.getLastLoginIp())) {
                            loginPlayers.add(player);
                            event.getPlayer().sendMessage(Utils.chat("&e&lSulfuris&6&lRP &8&l>> &2Vous avez été authentifié, bon jeu!"));
                        }
                        while (!loginPlayers.contains(player)) {
                            player.sendTitle(Utils.chat("SulfurisRP").toString(), Utils.chat("&aVeuillez Entrer vote mot de pase").toString(), 10, 40, 20);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    player.kickPlayer(Utils.chat("&cVous n'avez pas de compte sur le serveur \n &cVeuillez vous inscrire sur le serveur sous le nom: " + player.getName() + "\n sur notre site: sulfuris.fr"));
                }
            }
        }.start();
    }
}
