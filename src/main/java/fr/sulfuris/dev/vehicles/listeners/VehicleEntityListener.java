package fr.sulfuris.dev.vehicles.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.sulfuris.dev.vehicles.commands.vehiclesubs.VehicleFuel;
import fr.sulfuris.dev.vehicles.events.VehicleDamageEvent;
import fr.sulfuris.dev.vehicles.events.VehicleFuelEvent;
import fr.sulfuris.dev.vehicles.events.VehicleOpenTrunkEvent;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.BossBarUtils;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.VehicleData;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVListener;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * On vehicle left click - damaging, opening a trunk, fueling
 */
public class VehicleEntityListener extends SulfuVListener {

    public static HashMap<String, Double> speed = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(EntityDamageByEntityEvent event) {
        this.event = event;
        final Entity victim = event.getEntity();
        final Entity damager = event.getDamager();

        if (!VehicleUtils.isVehicle(victim)) return;

        String license = VehicleUtils.getLicensePlate(victim);

        if (!(damager instanceof Player)) {
            setupDamageAPI(damager, license);
            callAPI(null);
            if (isCancelled()) return;

            checkDamage(((VehicleDamageEvent) getAPI()).getLicensePlate());
            return;
        }

        player = (Player) damager;

        if (player.isSneaking() && !player.isInsideVehicle()) {
            this.setAPI(new VehicleOpenTrunkEvent());
            VehicleOpenTrunkEvent api = (VehicleOpenTrunkEvent) getAPI();
            api.setLicensePlate(license);
            callAPI();
            if (isCancelled()) return;

            VehicleUtils.openTrunk(player, api.getLicensePlate());
            event.setCancelled(true);
            return;
        }

        if (!player.isInsideVehicle()) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.hasItemMeta() || !new NBTItem(item).hasKey("mtvehicles.benzineval")) {
            setupDamageAPI(damager, license);
            callAPI();
            if (isCancelled()) return;

            checkDamage(((VehicleDamageEvent) getAPI()).getLicensePlate());
            return;
        }

        NBTItem nbt = new NBTItem(item);

        final double vehicleFuel = VehicleData.fuel.get(license);
        final String jerryCanFuel = nbt.getString("mtvehicles.benzineval");
        final String jerryCanSize = nbt.getString("mtvehicles.benzinesize");

        this.setAPI(new VehicleFuelEvent(vehicleFuel, Integer.parseInt(jerryCanFuel), Integer.parseInt(jerryCanSize)));
        VehicleFuelEvent api = (VehicleFuelEvent) getAPI();
        api.setLicensePlate(license);
        callAPI();
        if (isCancelled()) return;

        license = api.getLicensePlate();

        if (!ConfigModule.defaultConfig.canUseJerryCan(player)) {
            ConfigModule.messagesConfig.sendMessage(player, Message.NOT_IN_A_GAS_STATION);
            return;
        }

        if (Integer.parseInt(jerryCanFuel) < 1) {
            ConfigModule.messagesConfig.sendMessage(player, Message.NO_FUEL);
            return;
        }

        if (vehicleFuel > 99) {
            ConfigModule.messagesConfig.sendMessage(player, Message.VEHICLE_FULL);
            return;
        }

        if (VehicleData.fallDamage.get(license) != null && vehicleFuel > 2) VehicleData.fallDamage.remove(license);

        if (vehicleFuel + 5 > 100) {
            int rest = (int) (100 - vehicleFuel);
            player.setItemInHand(VehicleFuel.jerrycanItem(Integer.parseInt(jerryCanSize), Integer.parseInt(jerryCanFuel) - rest));
            VehicleData.fuel.put(license, VehicleData.fuel.get(license) + rest);
            BossBarUtils.setBossBarValue(vehicleFuel / 100.0D, license);
            return;
        }

        if (!(Integer.parseInt(jerryCanFuel) < 5)) {
            VehicleData.fuel.put(license, VehicleData.fuel.get(license) + 5);
            BossBarUtils.setBossBarValue(vehicleFuel / 100.0D, license);
            player.setItemInHand(VehicleFuel.jerrycanItem(Integer.parseInt(jerryCanSize), Integer.parseInt(jerryCanFuel) - 5));
        } else {
            VehicleData.fuel.put(license, Double.valueOf(VehicleData.fuel.get(license) + jerryCanFuel));
            BossBarUtils.setBossBarValue(vehicleFuel / 100.0D, license);
            player.setItemInHand(VehicleFuel.jerrycanItem(Integer.parseInt(jerryCanSize), 0));
        }
    }

    /**
     * Damage a vehicle.
     *
     * @param license The vehicle's license plate
     * @deprecated Renamed to {@link #damage(String)}.
     */
    @Deprecated
    public void checkDamage(String license) {
        damage(license);
    }

    /**
     * Damage a vehicle.
     *
     * @param license The vehicle's license plate
     */
    public void damage(String license) {
        final double damage = ((VehicleDamageEvent) getAPI()).getDamage();

        if (!(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.DAMAGE_ENABLED)) return;
        if (VehicleUtils.getVehicle(license) == null) return;

        double damageMultiplier = (double) ConfigModule.defaultConfig.get(DefaultConfig.Option.DAMAGE_MULTIPLIER);
        if (damageMultiplier < 0.1 || damageMultiplier > 5)
            damageMultiplier = 0.5; //Must be between 0.1 and 5. Default: 0.5
        ConfigModule.vehicleDataConfig.damageVehicle(license, damage * damageMultiplier);
    }

    private void setupDamageAPI(@Nullable Entity damager, String license) {
        this.setAPI(new VehicleDamageEvent());
        VehicleDamageEvent api = (VehicleDamageEvent) getAPI();
        api.setDamager(damager);
        api.setLicensePlate(license);
        api.setDamage(((EntityDamageByEntityEvent) event).getDamage());
    }
}
