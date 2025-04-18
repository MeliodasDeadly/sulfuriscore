package fr.sulfuris.dev.commands.admin.money;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.data.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setCommand implements CommandExecutor {

    public main plugin;

    public setCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("moneyset").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        StoringData.setMoney(target, Integer.parseInt(args[1]));
        if(target != sender){target.sendMessage(Utils.chat("&aYour money has been set to " + args[1]));}
        sender.sendMessage(Utils.chat("&aYou have set " + target.getName() + "'s money to " + args[1]));


        return true;
    }

}
