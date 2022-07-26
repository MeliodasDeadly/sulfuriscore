package fr.sulfuris.dev.itemstack.keypad;

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

public class keypaditemstack {

    public static ItemStack getItemStack(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
            ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6Keypad" + "Owner: " + player.getName());
            meta.getPersistentDataContainer().set(new NamespacedKey(main.getPlugin(), "keypad"), PersistentDataType.STRING,"true");
            meta.getPersistentDataContainer().set(new NamespacedKey(main.getPlugin(), "owner"), PersistentDataType.STRING,player.getName());
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            return item;

    }

}
