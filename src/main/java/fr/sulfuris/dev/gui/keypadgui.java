package fr.sulfuris.dev.gui;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import fr.sulfuris.dev.main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static java.lang.String.valueOf;

public class keypadgui implements Listener {

    private main plugin;

    public keypadgui(main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player playeri = event.getPlayer();
            ItemStack item = playeri.getItemInHand();
            PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if (event.getClickedBlock().getType() == Material.EMERALD_BLOCK && item.getType() == Material.GOLD_NUGGET) {
                    if (data.has(new NamespacedKey(plugin, "keypad"), PersistentDataType.STRING)) {
                    String keypad = data.get(new NamespacedKey(plugin, "keypad"), PersistentDataType.STRING);
                    if (keypad.equalsIgnoreCase("true")) {
                        new AnvilGUI.Builder()
                                .onComplete((player, text) -> {
                                    if(text.equalsIgnoreCase(null)) {
                                        return AnvilGUI.Response.text("You must enter a password");
                                    } else if (text.length() < 4) {
                                        return AnvilGUI.Response.text("Password must be at least 4 characters");
                                    } else if (!Utils.isNumeric(text)) {
                                        return AnvilGUI.Response.text("Password must be numeric");

                                    } else if (Utils.isNumeric(text)) {
                                        data.set(new NamespacedKey(plugin, "password"), PersistentDataType.STRING, text);
                                        playeri.sendMessage("§aMot de passe mit a jour");
                                        return AnvilGUI.Response.close();
                                    } else {
                                        return AnvilGUI.Response.text("You must enter a password");
                                    }
                                })
                                .text("Entrez votre mot de passe")
                                .itemLeft(new ItemStack(Material.PAPER))
                                .title("§7Connexion")
                                .plugin(plugin)
                                .open(playeri);



                    }else{



                        new AnvilGUI.Builder()
                                .onComplete((player, text) -> {
                                    if(text.equalsIgnoreCase(valueOf(data.get(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)))) {
                                        event.setCancelled(true);
                                        player.sendMessage("GoodJob");
                                        return AnvilGUI.Response.close();
                                    } else {
                                        return AnvilGUI.Response.text("Incorrect.");
                                    }
                                })
                                .text("Entrez votre code")
                                .itemLeft(new ItemStack(Material.PAPER))
                                .title("§7Connexion")
                                .plugin(plugin)
                                .open(playeri);

                    }


                    }


                    }
                }
        }
    }








