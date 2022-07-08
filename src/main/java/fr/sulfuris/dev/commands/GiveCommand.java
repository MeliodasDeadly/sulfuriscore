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
            plugin.getCommand("give").setExecutor((CommandExecutor)this);
        }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            try{
                Player player = (Player)sender;
                    if (args.length == 2){
                        if (Utils.isNumeric(args[2])) {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (args[1].equalsIgnoreCase("money")) {
                                    player.sendMessage(Utils.chat("&aYou have been given" + args[2] + "&a"));
                                    player.setBalance(player.getBalance() + Integer.parseInt(args[2]));
                                }

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

