package fr.sulfuris.dev.commands.money;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {
    private main plugin;
    public SetCommand(main plugin) {
        this.plugin = plugin;
        plugin.getCommand("setmoney").setExecutor(this);
    }

    @Override
    public boolean onCommand( CommandSender sender,  Command cmd,  String msg,  String[] args) {
        try{
            if(args[0].equalsIgnoreCase("setmoney")){
                Player player = (Player)sender;
                Player target = plugin.getServer().getPlayer(args[0]);
                int amount = Integer.parseInt(args[1]);
                if(args.length >= 2 && args[0] == target.getName() && Utils.isNumeric(args[1])){
                    target.sendMessage("Ta monnaie à changer !");
                    target.sendMessage("Elle est passé à " + amount + " $" + " par " + player.getName());
                    player.sendMessage("tu as défini la monnaie de " + target.getName() + " à " + amount + " $");
                    StoringData.setMoney(player, Integer.parseInt(args[1]));
                } else if (Utils.isNumeric(args[0]) && sender == player) {
                    player.sendMessage(Utils.chat("&aVous avez défini votre propre monnaie à " + args[1] + "&a"));
                    StoringData.setMoney(player, Integer.parseInt(args[0]));
                } else {
                    player.sendMessage(Utils.chat("&aYou need to provide a number"));
                }


            }




        }catch (NullArgumentException e) {
            sender.sendMessage(this.plugin.getConfig().getString("error-message"));

        }catch (NumberFormatException e) {
            sender.sendMessage(this.plugin.getConfig().getString("error-message"));
        }
        return true;
    }
}
