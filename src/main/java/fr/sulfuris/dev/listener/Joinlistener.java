package fr.sulfuris.dev.listener;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.sulfuris.dev.data.DatabaseUser;
import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.logging.Level;

public class Joinlistener implements Listener {

    private main plugin;
    public Joinlistener(main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public static ArrayList<Player> loginPlayers = new ArrayList<>();


    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        /*if(data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER) == null || !data.has(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER)){
            StoringData.money(player);
            plugin.getLogger().log(Level.INFO, Utils.chat("&aPlayer " + event.getPlayer().getName() + " has been added to the database"));
        }
        if(data.get(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.INTEGER) == null || !data.has(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.INTEGER)){
            StoringData.job(player);
            plugin.getLogger().log(Level.INFO, "&aPlayer " + player.getName() + " &ahas no job, setting to default job");
        }*/



        if(!loginPlayers.contains(player)){
            DatabaseUser user = DatabaseUser.getFromName(event.getPlayer().getName());
            System.out.println("LOGIN LAST " + user.getLastLoginIp());
            System.out.println("LOGIN IP " + player.getAddress().toString().substring(1).split(":")[0]);
            if(player.getAddress().toString().substring(1).split(":")[0].equals(user.getLastLoginIp())) {
                System.out.println("LOGIN IP MATCH");
                loginPlayers.add(player);
            }
            new Thread(){
                public void run(){
                    while (!loginPlayers.contains(player)) {
                        player.sendTitle(Utils.chat("SulfurisRP").toString(), Utils.chat("&aVeuillez Entrer vote mot de pase").toString(), 10, 40, 20);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }.start();
        }
        player.teleport(new Location(player.getWorld(), -256, 68, -282));
    }
    @EventHandler
    public static void onPlayerInterract(PlayerInteractEvent event){
        if(!loginPlayers.contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public static void onPlayerDisconnect(org.bukkit.event.player.PlayerQuitEvent event){
        if(loginPlayers.contains(event.getPlayer())){
            loginPlayers.remove(event.getPlayer());
        }
    }
    @EventHandler
    public static void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event){
        if(!loginPlayers.contains(event.getPlayer())){
            event.setCancelled(true);
            String message = event.getMessage();
            DatabaseUser user = DatabaseUser.getFromName(event.getPlayer().getName());
            BCrypt.Result result = BCrypt.verifyer().verify(message.toCharArray(), user.getPassword());
            if(result.verified) {
                loginPlayers.add(event.getPlayer());
                event.getPlayer().sendMessage(Utils.chat("&e&lSulfuris&6&lRP &8&l>> &2Vous avez été authentifié, bon jeu!"));
                event.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
                event.getPlayer().removePotionEffect(PotionEffectType.JUMP);
                user.setLastLoginIp(event.getPlayer().getAddress().toString().substring(1).split(":")[0]);
            } else {
                event.getPlayer().kickPlayer(Utils.chat("Mot de passe incorrect"));
            }
        }
    }
    @EventHandler
    public static void onPlayerCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event){
        if(!loginPlayers.contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }
}
