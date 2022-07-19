package fr.sulfuris.dev.gui;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import fr.sulfuris.dev.itemstack.bank.loginitemstack;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

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
    public void OnClick(InventoryClickEvent event){




    }






}
