package fr.sulfuris.dev.inv;

import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class bankinv {

    public static Inventory getInventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 27, player.getName() + " §7ATM");

        inv.setItem(13, infoitemstack.getItemStack(player));
        inv.setItem(11, jobitemstack.getItemStack(player));
        return inv;
    }
}
