package fr.sulfuris.dev.commands.shop;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.main;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PackageCommand implements CommandExecutor {
    private main plugin;

    public PackageCommand (final main plugin) {
        this.plugin = plugin;
        plugin.getCommand("givepack").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player target = plugin.getServer().getPlayer(args[1]);
        Server server = target.getServer();
        Player player = server.getPlayer(args[1]);

        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("givepack")){
                if(args[1].equalsIgnoreCase(target.getName())){
                    target.sendMessage("Vous avez reçu le pack suivant : " + args[2]);
                    sender.sendMessage("Le joueur " + target.getName() + " a reçu le pack " + args[2]);
                    server.broadcastMessage("Merci à " + target.getName() + " d'avoir acheté le pack "  + args[2] + " !");
                }
            }
        }
        return true;
    }

}
