package fr.sulfuris.dev.gui;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.data.Utils;
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

    private final main plugin;

    public atmgui(final main plugin) {
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
                event.setCancelled(true);
            }


        }


    }
    @EventHandler
    public void OnClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7SE CONNECTER")) {
            Player playeri = (Player) event.getWhoClicked();
            Plugin plugin = main.getPlugin();

            PersistentDataContainer data = playeri.getPersistentDataContainer();
            if(!data.has(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)) {
                Inventory inv = Bukkit.createInventory(null, 27, "§7ATM");
                inv.setItem(13, infoitemstack.getItemStack(playeri));
                inv.setItem(12, jobitemstack.getItemStack(playeri));


                new AnvilGUI.Builder()
                        .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
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
                            }else {
                                return AnvilGUI.Response.text("You must enter a password");
                            }
                        })
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
                        .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                            if(text.equalsIgnoreCase(valueOf(data.get(new NamespacedKey(main.getPlugin(), "password"), PersistentDataType.STRING)))) {
                                event.setCancelled(true);
                                player.openInventory(inv);
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
