package fr.sulfuris.dev.data;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.valueOf;

public class StoringData extends JavaPlugin {


    // const amount == get amount from command

    public static void money(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!data.has(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf(main.getPlugin().getConfig().getInt("default-currency-value")));
        }
    }
    public static void job(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!data.has(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.STRING, valueOf(main.getPlugin().getConfig().getInt("default-job-value")));
        }
    }

    public static void setMoney(@NotNull Player player, int amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf(amount));
    }
    public static void resetMoney(@NotNull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf(main.getPlugin().getConfig().getInt("default-currency-value")));
    }
    public static String getMoney(@NotNull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING);
    }
    public static void giveMoney(@NotNull Player player, int amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf(getMoney(player)) + amount);
    }
    public static void deleteMoney(@NotNull Player player, int amount){
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf( data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER) - amount));
    }
    public static void setJob(@NotNull Player player, int job) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.STRING, valueOf(job));
    }
    public static int getJob(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.get(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.INTEGER);
    }
    public static void resetJob(@NotNull Player player, int job){
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(main.getPlugin(), "job"), PersistentDataType.STRING, valueOf(job));
    }
    public static void removeData(@NotNull Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.remove(new NamespacedKey(main.getPlugin(), "job"));
        data.remove(new NamespacedKey(main.getPlugin(), "money"));
    }
}
