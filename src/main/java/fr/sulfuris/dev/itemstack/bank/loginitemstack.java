package fr.sulfuris.dev.itemstack.bank;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class loginitemstack {

    public static ItemStack getItemStack() {
        ItemStack loginitemstack = new ItemStack(Material.ANVIL);
        ItemMeta loginitemstackm = loginitemstack.getItemMeta();
        loginitemstackm.setDisplayName("&eSe Connecter");
        loginitemstackm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        loginitemstackm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        loginitemstack.setItemMeta(loginitemstackm);
        return loginitemstack;
    }
}
