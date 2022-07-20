package fr.sulfuris.dev.commands.admin.bank;

import fr.sulfuris.dev.Main;
import fr.sulfuris.dev.itemstack.bank.infoitemstack;
import fr.sulfuris.dev.itemstack.bank.jobitemstack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class bankCommand implements CommandExecutor {
    private Main plugin;

    public bankCommand(final Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("adminbank").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        Inventory inv = Bukkit.createInventory(null, 27, target.getName() + " §7ATM");

        inv.setItem(13, infoitemstack.getItemStack(target));
        inv.setItem(11, jobitemstack.getItemStack(target));


        target.openInventory(inv);
        return true;
    }




    }
