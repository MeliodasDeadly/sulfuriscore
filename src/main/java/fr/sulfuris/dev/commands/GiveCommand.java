package fr.sulfuris.dev.commands;

import fr.sulfuris.dev.Utils;
import fr.sulfuris.dev.main;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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
                Player target = plugin.getServer().getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                    if (args.length > 0){

                        if (args[0].equalsIgnoreCase("givemoney")) {
                            if(args.length >1 && args[1] == target.getName() && Utils.isNumeric(args[2])){
                                target.sendMessage("You have been given " + amount + " $" + " by " + player.getName());
                                player.sendMessage("You gave " + target.getName() + " " + amount + " $");
                                //player.setBalance(player.getBalance() + Integer.parseInt(args[1]));
                            }
                            if (Utils.isNumeric(args[1])) {
                                player.sendMessage(Utils.chat("&aYou have been given" + args[1] + "&a"));
                                //player.setBalance(player.getBalance() + Integer.parseInt(args[1]));
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

