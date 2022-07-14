package fr.sulfuris.dev.commands.admin;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;

public class StoreCommand implements CommandExecutor {
    public main plugin;

    public StoreCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("store").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {

                StringBuilder message = new StringBuilder();
                for (String arg : args) {
                    message.append(arg).append(" ");
                }

                ItemStack item = player.getInventory().getItemInHand();

                ItemMeta meta = item.getItemMeta();

                PersistentDataContainer data = meta.getPersistentDataContainer();

                if (data.has(new NamespacedKey(main.getPlugin(), "message"), PersistentDataType.STRING)) {
                    player.sendMessage(Utils.chat("&aYour already a message stored in this item"));
                    player.sendMessage(Utils.chat("Message: " + data.get(new NamespacedKey(main.getPlugin(), "message"), PersistentDataType.STRING)));
                }else{
                    data.set(new NamespacedKey(main.getPlugin(), "message"), PersistentDataType.STRING, message.toString());

                    item.setItemMeta(meta);

                    player.sendMessage(Utils.chat("&aYour message has been stored in this item"));
                }

                }else{
                    player.sendMessage(Utils.chat("&aYou need to provide a message to store"));

                }



            }
        return true;

        }
    }

