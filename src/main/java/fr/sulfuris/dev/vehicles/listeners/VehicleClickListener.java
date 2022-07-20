package fr.sulfuris.dev.vehicles.listeners;

import fr.sulfuris.dev.vehicles.events.VehicleEnterEvent;
import fr.sulfuris.dev.vehicles.events.VehiclePickUpEvent;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.enums.RegionAction;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.TextUtils;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.VehicleData;
import fr.sulfuris.dev.vehicles.infrastructure.models.SulfuVListener;
import fr.sulfuris.dev.vehicles.infrastructure.models.Vehicle;
import fr.sulfuris.dev.vehicles.infrastructure.models.VehicleUtils;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * On vehicle right click - entering and picking up
 */
public class VehicleClickListener extends SulfuVListener {
    private Map<String, Long> lastUsage = new HashMap<>();

    private Entity entity;
    private String license;

    /**
     * Create {@link VehicleData} (necessary for driving to work) and make player enter a vehicle.
     *
     * @param licensePlate Vehicle's license plate
     * @param p            Player who is entering the vehicle
     * @deprecated This method has been refactored to {@link VehicleUtils#enterVehicle(String, Player)} because it simply has nothing to do with placing the vehicle (see {@link VehicleUtils#spawnVehicle(String, Location)} for that).
     */
    public static void placeVehicle(String licensePlate, Player p) {
        VehicleUtils.enterVehicle(licensePlate, p);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        this.event = event;
        player = event.getPlayer();
        entity = event.getRightClicked();
        long lastUsed = 0L;

        if (!VehicleUtils.isVehicle(entity)) return;
        event.setCancelled(true); //Prevents skin item from getting grabbed by a player

        if (entity.getCustomName().startsWith("VEHICLE")) return;

        if (lastUsage.containsKey(player.getName())) lastUsed = (lastUsage.get(player.getName())).longValue();
        if (System.currentTimeMillis() - lastUsed >= 500)
            lastUsage.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        else return;

        license = VehicleUtils.getLicensePlate(entity);

        if (player.isSneaking()) {
            pickup();
            return;
        }
        enter();
    }

    private void pickup() {
        this.setAPI(new VehiclePickUpEvent());
        VehiclePickUpEvent api = (VehiclePickUpEvent) getAPI();
        api.setLicensePlate(license);
        callAPI();
        if (isCancelled()) return;

        license = api.getLicensePlate();
        Vehicle vehicle = VehicleUtils.getVehicle(license);
        if (vehicle == null) return;

        if (!player.hasPermission("mtvehicles.anwb") && (boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.DISABLE_PICKUP_FROM_WATER)) {
            if (entity.getLocation().clone().add(0, 1, 0).getBlock().isLiquid()) {
                ConfigModule.messagesConfig.sendMessage(player, Message.VEHICLE_IN_WATER);
                return;
            }
        }

        if (!ConfigModule.defaultConfig.canProceedWithAction(RegionAction.PICKUP, vehicle.getVehicleType(), ((PlayerInteractAtEntityEvent) event).getRightClicked().getLocation())) {
            ConfigModule.messagesConfig.sendMessage(player, Message.CANNOT_DO_THAT_HERE);
            return;
        }

        VehicleUtils.pickupVehicle(license, player);
    }

    private void enter() {
        this.setAPI(new VehicleEnterEvent());
        VehicleEnterEvent api = (VehicleEnterEvent) getAPI();
        api.setLicensePlate(license);
        callAPI();
        if (isCancelled()) return;

        license = api.getLicensePlate();
        Vehicle vehicle = VehicleUtils.getVehicle(license);
        if (vehicle == null) return;

        if (entity.getCustomName().contains("MTVEHICLES_SEAT")) {

            if (!vehicle.isOpen() && !vehicle.isOwner(player) && !vehicle.canSit(player) && !player.hasPermission("mtvehicles.ride")) {
                player.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_NO_RIDER_ENTER).replace("%p%", vehicle.getOwnerName())));
                return;
            }

            if (entity.isEmpty()) {
                entity.addPassenger(player);
                player.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_ENTER_MEMBER).replace("%p%", vehicle.getOwnerName())));
            }

            return;
        }

        VehicleUtils.enterVehicle(license, player);
    }

}
