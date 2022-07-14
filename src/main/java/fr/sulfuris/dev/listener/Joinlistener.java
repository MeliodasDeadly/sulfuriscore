package fr.sulfuris.dev.listener;

import fr.sulfuris.dev.data.StoringData;
import fr.sulfuris.dev.main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Joinlistener implements Listener {

    private main plugin;
    public Joinlistener(main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        event.getPlayer().sendMessage(plugin.getConfig().getString("joinlistener1") + event.getPlayer().getName() + plugin.getConfig().getString("joinlistener2"));

        if(data.get(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER) == null || !data.has(new NamespacedKey(main.getPlugin(), "money"), PersistentDataType.INTEGER)){
            StoringData.money(player);
        }
    }


}
