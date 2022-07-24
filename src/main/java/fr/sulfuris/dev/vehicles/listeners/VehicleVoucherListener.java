package fr.sulfuris.dev.vehicles.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.sulfuris.dev.vehicles.events.VehicleVoucherEvent;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.MessagesConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.ItemUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVListener;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class VehicleVoucherListener extends SulfuVListener {
    public static HashMap<Player, String> voucher = new HashMap<>();

    public VehicleVoucherListener() {
        super(new VehicleVoucherEvent());
    }

    @EventHandler
    public void onVoucherRedeem(PlayerInteractEvent event) {
        this.event = event;
        player = event.getPlayer();
        final Action action = event.getAction();
        final ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.PAPER) return;
        NBTItem nbt = new NBTItem(item);
        if (!nbt.hasKey("svehicles.item")) return;

        String carUUID = nbt.getString("svehicles.item");

        VehicleVoucherEvent api = (VehicleVoucherEvent) getAPI();
        api.setVoucherUUID(carUUID);
        callAPI();
        if (isCancelled()) return;

        carUUID = api.getVoucherUUID();

        if (event.getHand() != EquipmentSlot.HAND) {
            event.setCancelled(true);
            player.sendMessage(ConfigModule.messagesConfig.getMessage(Message.WRONG_HAND));
            return;
        }

        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            Inventory inv = Bukkit.createInventory(null, 27, InventoryTitle.VOUCHER_REDEEM_MENU.getStringTitle());
            voucher.put(player, carUUID);
            MessagesConfig msg = ConfigModule.messagesConfig;
            inv.setItem(11, ItemUtils.getMenuItem(
                    "RED_WOOL",
                    "WOOL",
                    (short) 14,
                    1,
                    "&c" + msg.getMessage(Message.CANCEL),
                    "&7" + msg.getMessage(Message.CANCEL_ACTION), "&7" + msg.getMessage(Message.CANCEL_VOUCHER)
            ));
            inv.setItem(15, ItemUtils.getMenuItem(
                    "LIME_WOOL",
                    "WOOL",
                    (short) 5,
                    1,
                    "&a" + msg.getMessage(Message.CONFIRM),
                    "&7" + msg.getMessage(Message.CONFIRM_ACTION), "&7" + msg.getMessage(Message.CONFIRM_VOUCHER)
            ));
            player.openInventory(inv);
        }
    }
}
