package fr.sulfuris.dev.itemstack.shop.key;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class goldkeyitemstack {

    public static ItemStack getItemStack() {
        ItemStack goldkeyitemstack = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta goldkeyitemstackmeta = goldkeyitemstack.getItemMeta();
        goldkeyitemstackmeta.setDisplayName("§aGold Key");
        goldkeyitemstackmeta.setLore(java.util.Arrays.asList("§aUse this key to open a box", "§aYou can buy have here"));
        goldkeyitemstackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        goldkeyitemstackmeta.addEnchant(Enchantment.DURABILITY, 1, true);
        goldkeyitemstack.setItemMeta(goldkeyitemstackmeta);
        return goldkeyitemstack;
    }
}

