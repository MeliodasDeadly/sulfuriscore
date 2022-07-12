package fr.sulfuris.dev.items.shop.key;

import fr.sulfuris.dev.main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class goldkey implements Listener {

    public main plugin;

    public goldkey(final main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            if (event.getPlayer().getItemInHand().getType() == Material.TRIPWIRE_HOOK && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§aGold Key")) {
                event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
            }

        }


    }
}
