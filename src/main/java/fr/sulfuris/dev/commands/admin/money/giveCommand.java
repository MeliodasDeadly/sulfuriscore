package fr.sulfuris.dev.commands.admin.money;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class giveCommand implements CommandExecutor {
    // same of resetCommand

    public main plugin;

        public giveCommand(final main plugin) {
            this.plugin = plugin;
            plugin.getCommand("moneygive").setExecutor((CommandExecutor) this);
        }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        StoringData.giveMoney(target, Integer.parseInt(args[1]));
        target.sendMessage(Utils.chat("&aYour money has been added to " + args[1]));
        sender.sendMessage(Utils.chat("&aYou have added " + target.getName() + "'s money to " + args[1]));


        return true;
    }
}
