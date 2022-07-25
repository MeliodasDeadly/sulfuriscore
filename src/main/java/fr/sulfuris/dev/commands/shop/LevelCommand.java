package fr.sulfuris.dev.commands.shop;

import fr.sulfuris.dev.Main;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {
    private Main plugin;

    public LevelCommand(final Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("givelevel").setExecutor((CommandExecutor) this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        // const target is the argument after the command
        Server server = plugin.getServer();
        Player target = server.getPlayer(args[0]);

        if (args.length >= 1) {
            if (target != null) {
                target.sendMessage("Vous avez reçu les levels suivant : " + args[2]);
                sender.sendMessage("Le joueur " + target.getName() + " a reçu " + args[1]);
                server.broadcastMessage("Merci à " + target.getName() + " d'avoir acheté le pack " + args[1] + " !");

            } else if (target == null) {
                sender.sendMessage("Le joueur " + args[0] + " n'est pas connecté");
            }

        }
        return true;
    }

}


