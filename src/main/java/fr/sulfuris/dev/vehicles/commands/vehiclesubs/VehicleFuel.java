package fr.sulfuris.dev.vehicles.commands.vehiclesubs;

import fr.sulfuris.dev.vehicles.events.inventory.JerryCanMenuOpen;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.ItemFactory;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVehicleSubCommand;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VehicleFuel extends SulfuVehicleSubCommand {
    public VehicleFuel() {
        this.setPlayerCommand(true);
    }

    public static ItemStack jerrycanItem(int maxFuel, int currentFuel) {
        ItemStack is = new ItemFactory(Material.getMaterial("DIAMOND_HOE")).setAmount(1).setDurability((short) 58).setNBT("svehicles.benzineval", "" + currentFuel).setNBT("svehicles.benzinesize", "" + maxFuel).toItemStack();
        ItemMeta im = is.getItemMeta();
        List<String> itemlore = new ArrayList<>();
        itemlore.add(TextUtils.colorize("&8"));
        itemlore.add(TextUtils.colorize("&7" + ConfigModule.messagesConfig.getMessage(Message.JERRYCAN) + " &e" + currentFuel + "&7/&e" + maxFuel + "&7l"));
        assert im != null;
        im.setLore(itemlore);
        im.setUnbreakable(true);
        im.setDisplayName(TextUtils.colorize("&6" + ConfigModule.messagesConfig.getMessage(Message.JERRYCAN) + " " + maxFuel + "L"));
        is.setItemMeta(im);
        return is;
    }

    @Override
    public boolean execute() {
        if (!checkPermission("svehicles.benzine")) return true;

        Inventory inv = Bukkit.createInventory(null, 9, InventoryTitle.JERRYCAN_MENU.getStringTitle());

        List<Integer> jerrycans = (List<Integer>) ConfigModule.defaultConfig.get(DefaultConfig.Option.JERRYCANS);
        assert jerrycans != null;

        for (int jerrycan : jerrycans) {
            inv.addItem(jerrycanItem(jerrycan, jerrycan));
        }

        JerryCanMenuOpen api = new JerryCanMenuOpen(player);
        api.call();
        if (api.isCancelled()) return true;

        player.openInventory(inv);

        return true;
    }
}
