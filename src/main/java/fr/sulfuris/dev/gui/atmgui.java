package fr.sulfuris.dev.gui;

import fr.sulfuris.dev.Main;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import fr.sulfuris.dev.itemstack.bank.loginitemstack;
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

import static java.lang.String.valueOf;

public class atmgui implements Listener {

    private final Main plugin;

    public atmgui(final Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            Player player = event.getPlayer();
            ItemStack item = player.getItemInHand();
            Inventory inv = Bukkit.createInventory(null, 27, "§7ATM");
            inv.setItem(13, loginitemstack.getItemStack());

            if(item.getType() == Material.GOLD_NUGGET){
                player.openInventory(inv);
            }


        }


    }
    @EventHandler
    public void OnClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7SE CONNECTER")) {
            Player playeri = (Player) event.getWhoClicked();
            Plugin plugin = Main.getPlugin();
            event.setCancelled(true);

            PersistentDataContainer data = playeri.getPersistentDataContainer();
            if (!data.has(new NamespacedKey(Main.getPlugin(), "password"), PersistentDataType.STRING)) {
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
                            if (text.equalsIgnoreCase(valueOf(data.get(new NamespacedKey(Main.getPlugin(), "password"), PersistentDataType.STRING)))) {
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
