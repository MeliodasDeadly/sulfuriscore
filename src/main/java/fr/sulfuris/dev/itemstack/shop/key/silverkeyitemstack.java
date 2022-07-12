package fr.sulfuris.dev.itemstack.shop.key;

import fr.sulfuris.dev.items.shop.key.copperkey;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class silverkeyitemstack {

    public static ItemStack getItemStack() {
        ItemStack copperkeyitemstack = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta copperkeyitemstackmeta = copperkeyitemstack.getItemMeta();
        copperkeyitemstackmeta.setDisplayName("§aSilver Key");
        copperkeyitemstackmeta.setLore(java.util.Arrays.asList("§aUse this key to open a box", "§aYou can have items here"));
        copperkeyitemstackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        copperkeyitemstackmeta.addEnchant(Enchantment.DURABILITY, 1, true);
        copperkeyitemstack.setItemMeta(copperkeyitemstackmeta);
        return copperkeyitemstack;
    }
}
