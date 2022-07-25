package fr.sulfuris.dev.commands.admin;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.data.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class removeCommand implements CommandExecutor {
    // same of resetCommand
    private main plugin;

    public removeCommand(main plugin) {
        this.plugin = plugin;
        plugin.getCommand("removedata").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        StoringData.removeData(target);
        target.sendMessage(Utils.chat("&aYour data has been removed"));
        sender.sendMessage(Utils.chat("&aYou have removed " + target.getName() + "'s data"));
        return true;
    }
}
