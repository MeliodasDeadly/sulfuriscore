package fr.sulfuris.dev.commands.shop;

import fr.sulfuris.dev.itemstack.shop.key.*;
import fr.sulfuris.dev.main;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.sulfuris.dev.itemstack.shop.*;
public class KeyCommand implements CommandExecutor {
    private main plugin;

    public KeyCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("givekey").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        // const target is the argument after the command
        Server server = plugin.getServer();
        Player target = server.getPlayer(args[0]);

        if (args.length >= 1) {
            if (target != null) {
                target.sendMessage("Vous avez reçu la clé suivante : " + args[1]);
                sender.sendMessage("Le joueur " + target.getName() + " a reçu la clé " + args[1]);
                server.broadcastMessage("Merci à " + target.getName() + " d'avoir acheté la clé " + args[1] + " !");
                if(args[1].equalsIgnoreCase("copperkey")){
                    copperkeyitemstack itemstack = new copperkeyitemstack();
                    target.getInventory().addItem(copperkeyitemstack.getItemStack());
                }
                if(args[1].equalsIgnoreCase("silverkey")){
                    silverkeyitemstack itemstack = new silverkeyitemstack();
                    target.getInventory().addItem(silverkeyitemstack.getItemStack());
                }

            } else if (target == null) {
                sender.sendMessage("Le joueur " + args[0] + " n'est pas connecté");
            }

        }
        return true;
    }

}


