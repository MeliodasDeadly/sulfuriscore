package fr.sulfuris.dev.itemstack.bank;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class infoitemstack {

    public static ItemStack getItemStack(Player player) {
        ItemStack infoitemstack = new ItemStack(Material.DIAMOND);
        ItemMeta infoitemstackm = infoitemstack.getItemMeta();
        infoitemstackm.setDisplayName("Argent : " + StoringData.getMoney(player) + " ");
        infoitemstackm.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        infoitemstackm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        infoitemstack.setItemMeta(infoitemstackm);

        return infoitemstack;
    }
}
