package fr.sulfuris.dev.commands.admin.keypad;

import fr.sulfuris.dev.itemstack.keypad.keypaditemstack;
import fr.sulfuris.dev.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class keypadCommand implements CommandExecutor {

    private main plugin;

    public keypadCommand (final main plugin){
        // same of InfoCommand
        this.plugin = plugin;
        plugin.getCommand("givekeypad").setExecutor((CommandExecutor) this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(args.length == 0){
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                target.getInventory().addItem(keypaditemstack.getItemStack(player));
                sender.sendMessage("Vous avez envoyer 1 keypad à " + target.getName() + ".");
            }else{
                sender.sendMessage("Vous devez spécifier un nom valide.");
            }

            if(sender.getName() != target.getName()){
                target.sendMessage("Vous avez reçu 1 keypad");
            }



        }else{
            sender.sendMessage("Vous devez spécifier un nom.");
        }
        return true;
    }


}
