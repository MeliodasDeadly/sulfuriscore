package fr.sulfuris.dev.commands.admin.money;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class resetCommand implements CommandExecutor {

    public main plugin;

    public resetCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("moneyreset").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        StoringData.resetMoney(target);
        target.sendMessage(Utils.chat("&aYour money has been reset"));
        sender.sendMessage(Utils.chat("&aYou have reset " + target.getName() + "'s money"));


        return true;
    }


}
