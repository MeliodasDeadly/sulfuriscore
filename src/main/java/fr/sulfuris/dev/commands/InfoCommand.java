package fr.sulfuris.dev.commands;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCommand implements CommandExecutor {
    private main plugin;

    public InfoCommand(final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("serverinfo").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.chat("&aServer name: &b" + plugin.getServer().getName()));
            sender.sendMessage(Utils.chat("&aServer version: &b" + plugin.getServer().getVersion()));
            sender.sendMessage(Utils.chat("&aServer port: &b" + plugin.getServer().getPort()));
            sender.sendMessage(Utils.chat("&aServer online players: &b" + plugin.getServer().getOnlinePlayers().size()));
            sender.sendMessage(Utils.chat("&aServer max players: &b" + plugin.getServer().getMaxPlayers()));
            sender.sendMessage(Utils.chat("&aServer motd: &b" + plugin.getServer().getMotd()));
            sender.sendMessage(Utils.chat("&aServer ip: &b" + plugin.getServer().getIp()));
            sender.sendMessage(Utils.chat("&aServer port: &b" + plugin.getServer().getPort()));
            sender.sendMessage(Utils.chat("&aServer online players: &b" + plugin.getServer().getOnlinePlayers().size()));
            sender.sendMessage(Utils.chat("&aServer max players: &b" + plugin.getServer().getMaxPlayers()));
            sender.sendMessage(Utils.chat("&aPlugins loaded: &b" + plugin.getServer().getPluginManager().getPlugins().length));


            return true;
        }
        return false;
    }
}
