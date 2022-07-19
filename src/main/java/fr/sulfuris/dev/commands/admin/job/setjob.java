package fr.sulfuris.dev.commands.admin.job;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.data.DatabaseUser;
import fr.sulfuris.dev.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.valueOf;

public class setjob implements CommandExecutor {

    public main plugin;

    public setjob(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("setjob").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        StoringData.setJob(target, valueOf(args[1]));
        target.sendMessage(Utils.chat("&aYour job has been set to " + args[1]));
        sender.sendMessage(Utils.chat("&aYou have set " + target.getName() + "'s job to " + args[1]));
        DatabaseUser user = DatabaseUser.getFromName(target.getName());
        return true;
    }

}
