package fr.sulfuris.dev.data;

import fr.sulfuris.dev.main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class StoringData extends JavaPlugin {
    // const amount == get amount from command

    public static void money(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!data.has(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER)) {
            data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER, main.getPlugin().getConfig().getInt("default-currency-value"));
        }
    }

    public static void setMoney(@NotNull Player player, int amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER, amount);
    }
    public static void DeleteMoney(@NotNull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER, main.getPlugin().getConfig().getInt("default-currency-value"));
    }
    public static int getMoney(@NotNull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER);
    }
    public static void giveMoney(@NotNull Player player, int amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER, getMoney(player) + amount);
    }
}
