package fr.sulfuris.dev.commands.money;

import fr.sulfuris.dev.data.Utils;
import fr.sulfuris.dev.main;
import fr.sulfuris.dev.data.*;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GiveCommand implements CommandExecutor {

    private main plugin;

    public GiveCommand(final main plugin){
            this.plugin = plugin;
            plugin.getCommand("givemoney").setExecutor((CommandExecutor)this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            try{
                Player player = (Player)sender;
                Player target = plugin.getServer().getPlayer(args[0]);
                int amount = Integer.parseInt(args[1]);
                    if (args.length > 0){

                        if (args[0].equalsIgnoreCase("givemoney")) {
                            if(args.length >1 && args[0] == target.getName() && Utils.isNumeric(args[1])){
                                target.sendMessage("You have been given " + amount + " $" + " by " + player.getName());
                                sender.sendMessage("You gave " + target.getName() + " " + amount + " $");
                                StoringData.giveMoney(player, Integer.parseInt(args[1]));
                            } else if (Utils.isNumeric(args[0]) && sender == player) {
                                player.sendMessage(Utils.chat("&aYou have been given" + args[0] + "&a"));
                                StoringData.giveMoney(player, Integer.parseInt(args[0]));
                            } else {
                                sender.sendMessage(Utils.chat("&aYou need to provide a number"));
                            }

                        }

                    }
            }catch (NullArgumentException e){
                sender.sendMessage(this.plugin.getConfig().getString("error-message"));
            }catch (NumberFormatException e){
                sender.sendMessage(this.plugin.getConfig().getString("error-message"));
            }
            return true;
    }
}

