package fr.sulfuris.dev.gui;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import fr.sulfuris.dev.itemstack.bank.loginitemstack;
import fr.sulfuris.dev.main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

import static java.lang.String.valueOf;

public class atmgui implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getClickedBlock().getType() == Material.BLACK_CONCRETE && event.getClickedBlock().hasMetadata("§7ATM")) {
            Player player = event.getPlayer();
            Inventory inv = Bukkit.createInventory(null, 27, "§7ATM");
            inv.setItem(13, loginitemstack.getItemStack());

            player.openInventory(inv);
        }


    }
    @EventHandler
    public void OnClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().equals("§7SE CONNECTER")) {
            Player playeri = (Player) event.getWhoClicked();
            Plugin plugin = main.getPlugin();

            PersistentDataContainer data = playeri.getPersistentDataContainer();
            if(!data.has(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)) {
                Inventory inv = Bukkit.createInventory(null, 27, "§7ATM");
                inv.setItem(13, infoitemstack.getItemStack(playeri));
                inv.setItem(12, jobitemstack.getItemStack(playeri));


                new AnvilGUI.Builder()
                        .onClose(player -> {                                               //called when the inventory is closing
                            player.sendMessage("You closed the inventory.");
                        })
                        .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                            if(text.equalsIgnoreCase(null)) {
                                return AnvilGUI.Response.text("You must enter a password");
                            } else {
                                data.set(new NamespacedKey(plugin, "password"), PersistentDataType.STRING, text);
                                playeri.openInventory(inv);
                                event.setCancelled(true);
                                return AnvilGUI.Response.text("Mot de passe mit a jour");
                            }
                        })
                        .preventClose()
                        .text("Entrez votre mot de passe")
                        .itemLeft(new ItemStack(Material.PAPER))
                        .title("§7Connexion")
                        .plugin(plugin)
                        .open(playeri);



            }else{
                Inventory inv = Bukkit.createInventory(null, 27, "§7ATM");
                inv.setItem(13, infoitemstack.getItemStack(playeri));
                inv.setItem(12, jobitemstack.getItemStack(playeri));



                new AnvilGUI.Builder()
                        .onClose(player -> {                                               //called when the inventory is closing
                            player.sendMessage("You closed the ATM.");
                        })
                        .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                            if(text.equalsIgnoreCase(valueOf(data.get(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)))) {
                                player.openInventory(inv);
                                event.setCancelled(true);
                                return AnvilGUI.Response.close();
                            } else {
                                return AnvilGUI.Response.text("Incorrect.");
                            }
                        })
                        .preventClose()
                        .text("Entrez votre code")
                        .itemLeft(new ItemStack(Material.PAPER))
                        .title("§7Connexion")
                        .plugin(plugin)
                        .open(playeri);

            }


        }
    }
}
