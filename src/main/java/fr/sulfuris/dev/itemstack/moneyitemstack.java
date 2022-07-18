package fr.sulfuris.dev.itemstack;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class moneyitemstack {
    
    public static ItemStack getItemStack(Player player) {
        ItemStack moneyitemstack = new ItemStack(Material.DIAMOND);
        ItemMeta moneyitemstackm = moneyitemstack.getItemMeta();
        moneyitemstackm.setDisplayName(StoringData.getMoney(player) + " " + main.getPlugin().getConfig().getString("currency-name"));
        moneyitemstackm.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        moneyitemstackm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        moneyitemstack.setItemMeta(moneyitemstackm);

        return moneyitemstack;
    }
}
