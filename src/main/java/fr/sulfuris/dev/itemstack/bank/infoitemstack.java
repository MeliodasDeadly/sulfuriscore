package fr.sulfuris.dev.itemstack.bank;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class infoitemstack {

    public static ItemStack getItemStack(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.has(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING)) {
            ItemStack item = new ItemStack(Material.DIAMOND);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6Money : " + data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING));
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            return item;
        }else {
            ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Aucune Data");
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            return item;
        }
    }
}
