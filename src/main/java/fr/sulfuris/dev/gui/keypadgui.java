package fr.sulfuris.dev.gui;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import fr.sulfuris.dev.main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;

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

            Block block = event.getClickedBlock();

            PersistentDataContainer data2 = block.getChunk().getPersistentDataContainer();
            if (event.getClickedBlock().getType() == Material.EMERALD_BLOCK ) {
                    if (data2.has(new NamespacedKey(plugin, "keypad"), PersistentDataType.STRING)) {

                    String keypad2 = data2.get(new NamespacedKey(plugin, "keypad"), PersistentDataType.STRING);
                    String owner2 = data2.get(new NamespacedKey(plugin, "owner"), PersistentDataType.STRING);
                    String owner = playeri.getName();

                    if (keypad2.equalsIgnoreCase("true")) {
                        if (owner2.equalsIgnoreCase(playeri.getName()) && !data2.has(new NamespacedKey(plugin, "password"), PersistentDataType.STRING)) {
                            new AnvilGUI.Builder()
                                    .onComplete((player, text) -> {
                                        if (text.equalsIgnoreCase(null)) {
                                            return AnvilGUI.Response.text("You must enter a password");
                                        } else if (text.length() < 4) {
                                            return AnvilGUI.Response.text("Password must be at least 4 characters");
                                        } else if (!Utils.isNumeric(text)) {
                                            return AnvilGUI.Response.text("Password must be numeric");

                                        } else if (Utils.isNumeric(text)) {
                                            data2.set(new NamespacedKey(plugin, "password"), PersistentDataType.STRING, text);
                                            data2.set(new NamespacedKey(plugin, "owner"), PersistentDataType.STRING, owner);
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
                        }else if (data2.has(new NamespacedKey(plugin, "password"), PersistentDataType.STRING)) {


                            new AnvilGUI.Builder()
                                    .onComplete((player, text) -> {
                                        if (text.equalsIgnoreCase(valueOf(data2.get(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)))) {
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

                        } else{
                            playeri.sendMessage("§cCe Keypad, ne vous appartient pas et/ou n'a aucun mot de passe défini.");
                        }
                    }

                    }


                    }
                }
        }
    }








