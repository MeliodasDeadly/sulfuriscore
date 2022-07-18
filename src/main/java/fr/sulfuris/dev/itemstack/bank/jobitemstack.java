package fr.sulfuris.dev.itemstack.bank;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class jobitemstack {

    public static ItemStack getItemStack(Player player) {
        ItemStack jobitemstack = new ItemStack(Material.REDSTONE);
        ItemMeta jobitemstackm = jobitemstack.getItemMeta();
        jobitemstackm.setDisplayName("Job : " + StoringData.getJob(player) + "");
        jobitemstackm.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        jobitemstackm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        jobitemstack.setItemMeta(jobitemstackm);
        return jobitemstack;
    }
}
