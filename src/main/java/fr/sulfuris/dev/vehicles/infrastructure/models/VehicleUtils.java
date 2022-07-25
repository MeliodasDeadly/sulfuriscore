package fr.sulfuris.dev.vehicles.infrastructure.models;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.sulfuris.dev.vehicles.infrastructure.annotations.ToDo;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.VehicleDataConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.InventoryTitle;
import fr.sulfuris.dev.vehicles.infrastructure.enums.Message;
import fr.sulfuris.dev.vehicles.infrastructure.enums.RegionAction;
import fr.sulfuris.dev.vehicles.infrastructure.enums.VehicleType;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.*;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class VehicleUtils {

        public static HashMap<Player, String> openedTrunk = new HashMap<>();

    private VehicleUtils() {
    }

    public static void spawnVehicle(String licensePlate, Location location) throws IllegalArgumentException {
        if (!existsByLicensePlate(licensePlate)) throw new IllegalArgumentException("Vehicle does not exists.");

        ArmorStand standSkin = location.getWorld().spawn(location, ArmorStand.class);
        standSkin.setVisible(false);
        standSkin.setCustomName("svehicles_SKIN_" + licensePlate);
        standSkin.getEquipment().setHelmet(
                ItemUtils.getVehicleItem(
                        ItemUtils.getMaterial(ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.SKIN_ITEM).toString()),
                        (int) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.SKIN_DAMAGE),
                        false,
                        ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.NAME).toString(),
                        licensePlate));

        ArmorStand standMain = location.getWorld().spawn(location, ArmorStand.class);
        standMain.setVisible(false);
        standMain.setCustomName("svehicles_MAIN_" + licensePlate);

        Vehicle vehicle = getVehicle(licensePlate);

        List<Map<String, Double>> seats = (List<Map<String, Double>>) vehicle.getVehicleData().get("seats");
        Map<String, Double> mainSeat = seats.get(0);
        Location locationMainSeat = new Location(location.getWorld(), location.getX() + mainSeat.get("x"), location.getY() + mainSeat.get("y"), location.getZ() + mainSeat.get("z"));
        ArmorStand standMainSeat = locationMainSeat.getWorld().spawn(locationMainSeat, ArmorStand.class);
        standMainSeat.setCustomName("svehicles_MAINSEAT_" + licensePlate);
        standMainSeat.setGravity(false);
        standMainSeat.setVisible(false);

        if (ConfigModule.vehicleDataConfig.getType(licensePlate).isHelicopter()) {
            List<Map<String, Double>> helicopterBlades = (List<Map<String, Double>>) vehicle.getVehicleData().get("wiekens");
            Map<?, ?> blade = helicopterBlades.get(0);
            Location locationBlade = new Location(location.getWorld(), location.getX() + (double) blade.get("z"), location.getY() + (double) blade.get("y"), location.getZ() + (double) blade.get("x"));
            ArmorStand standRotors = locationBlade.getWorld().spawn(locationBlade, ArmorStand.class);
            standRotors.setCustomName("svehicles_WIEKENS_" + licensePlate);
            standRotors.setGravity(false);
            standRotors.setVisible(false);

            if ((boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.HELICOPTER_BLADES_ALWAYS_ON)) {
                ItemStack rotor = (new ItemFactory(Material.getMaterial("DIAMOND_HOE"))).setDurability((short) 1058).setName(TextUtils.colorize("&6Wieken")).setNBT("svehicles.kenteken", licensePlate).toItemStack();
                ItemMeta itemMeta = rotor.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(TextUtils.colorize("&a"));
                lore.add(TextUtils.colorize("&a" + licensePlate));
                lore.add(TextUtils.colorize("&a"));
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                rotor.setItemMeta(itemMeta);
                standRotors.setHelmet((ItemStack) blade.get("item"));
            }
        }
    }

    public static String getLicensePlate(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.getString("svehicles.kenteken");
    }

    public static ItemStack getItemByUUID(Player p, String uuid) {
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        List<Map<?, ?>> matchedVehicles = new ArrayList<>();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("uuid") != null) {
                    if (skin.get("uuid").equals(uuid)) {
                        String nbtVal;
                        if (skin.get("nbtValue") == null) {
                            nbtVal = "null";
                        } else {
                            nbtVal = skin.get("nbtValue").toString();
                        }
                        ItemStack is = ItemUtils.getVehicleItem(ItemUtils.getMaterial(skin.get("SkinItem").toString()), (int) skin.get("itemDamage"), ((String) skin.get("name")), "mtcustom", nbtVal);
                        NBTItem nbt = new NBTItem(is);
                        String licensePlate = nbt.getString("svehicles.kenteken");
                        matchedVehicles.add(configVehicle);

                        Vehicle vehicle = new Vehicle();
                        List<String> members = ConfigModule.vehicleDataConfig.getMembers(licensePlate);
                        List<String> riders = ConfigModule.vehicleDataConfig.getRiders(licensePlate);
                        List<String> trunkData = ConfigModule.vehicleDataConfig.getTrunkData(licensePlate);

                        vehicle.setLicensePlate(licensePlate);
                        vehicle.setName((String) skin.get("name"));
                        vehicle.setVehicleType((String) configVehicle.get("vehicleType"));
                        vehicle.setSkinDamage((Integer) skin.get("itemDamage"));
                        vehicle.setSkinItem((String) skin.get("SkinItem"));
                        vehicle.setGlow(false);
                        vehicle.setBenzineEnabled((Boolean) configVehicle.get("benzineEnabled"));
                        vehicle.setFuel(100);
                        vehicle.setHornEnabled((Boolean) configVehicle.get("hornEnabled"));
                        vehicle.setHealth((double) configVehicle.get("maxHealth"));
                        vehicle.setTrunk((Boolean) configVehicle.get("kofferbakEnabled"));
                        vehicle.setTrunkRows(1);
                        vehicle.setFuelUsage(0.01);
                        vehicle.setTrunkData(trunkData);
                        vehicle.setAccelerationSpeed((Double) configVehicle.get("acceleratieSpeed"));
                        vehicle.setMaxSpeed((Double) configVehicle.get("maxSpeed"));
                        vehicle.setBrakingSpeed((Double) configVehicle.get("brakingSpeed"));
                        vehicle.setFrictionSpeed((Double) configVehicle.get("aftrekkenSpeed"));
                        vehicle.setRotateSpeed((Integer) configVehicle.get("rotateSpeed"));
                        vehicle.setMaxSpeedBackwards((Double) configVehicle.get("maxSpeedBackwards"));
                        vehicle.setOwner(p.getUniqueId().toString());
                        vehicle.setRiders(riders);
                        vehicle.setMembers(members);
                        vehicle.setNbtValue(((String) skin.get("nbtValue")));
                        vehicle.save();
                        return is;
                    }
                }
            }
        }
        return null;
    }

    public static boolean getHornByDamage(int damage) {
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("itemDamage") != null) {
                    if (skin.get("itemDamage").equals(damage)) {
                        return (boolean) configVehicle.get("hornEnabled");
                    }
                }
            }
        }
        return false;
    }

    public static double getMaxHealthByDamage(int damage) {
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("itemDamage") != null) {
                    if (skin.get("itemDamage").equals(damage)) {
                        return (double) configVehicle.get("maxHealth");
                    }
                }
            }
        }
        return 0;
    }

    public static ItemStack getCarItem(String carUUID) {
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        List<Map<?, ?>> matchedVehicles = new ArrayList<>();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("uuid") != null) {
                    if (skin.get("uuid").equals(carUUID)) {
                        if (skin.get("uuid") != null) {
                            ItemStack is = ItemUtils.getVehicleItem(ItemUtils.getMaterial(skin.get("SkinItem").toString()), (int) skin.get("itemDamage"), ((String) skin.get("name")));
                            matchedVehicles.add(configVehicle);
                            return is;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isVehicle(Entity entity) {
        return entity.getCustomName() != null && entity instanceof ArmorStand && entity.getCustomName().contains("svehicles");
    }

    public static String getLicensePlate(@Nullable Entity entity) {
        if (entity == null) return null;
        final String name = entity.getCustomName();
        if (name.split("_").length > 1) {
            return name.split("_")[2];
        }
        return null;
    }

    public static String getCarUUID(String licensePlate) {
        if (!existsByLicensePlate(licensePlate)) return null;

        Map<?, ?> vehicleData = ConfigModule.vehicleDataConfig.getConfig()
                .getConfigurationSection(String.format("vehicle.%s", licensePlate)).getValues(true);
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        List<Map<?, ?>> matchedVehicles = new ArrayList<>();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("itemDamage").equals(vehicleData.get("skinDamage"))) {
                    if (skin.get("SkinItem").equals(vehicleData.get("skinItem"))) {
                        if (skin.get("nbtValue") != null) {
                            if (skin.get("nbtValue").equals(vehicleData.get("nbtValue"))) {
                                matchedVehicles.add(configVehicle);
                                return skin.get("uuid").toString();
                            }
                        } else {
                            matchedVehicles.add(configVehicle);
                            return skin.get("uuid").toString();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Deprecated
    public static Vehicle getByLicensePlate(String licensePlate) {
        return getVehicle(licensePlate);
    }

    @ToDo("Beautify the code inside this method.")
    public static Vehicle getVehicle(String licensePlate) {
        if (!existsByLicensePlate(licensePlate)) return null;

        ConfigModule.vehicleDataConfig.reload();
        ConfigModule.vehiclesConfig.reload();

        Map<?, ?> vehicleData = ConfigModule.vehicleDataConfig.getConfig()
                .getConfigurationSection(String.format("vehicle.%s", licensePlate)).getValues(true);
        List<Map<?, ?>> vehicles = ConfigModule.vehiclesConfig.getVehicles();
        List<Map<?, ?>> matchedVehicles = new ArrayList<>();
        for (Map<?, ?> configVehicle : vehicles) {
            List<Map<?, ?>> skins = (List<Map<?, ?>>) configVehicle.get("cars");
            for (Map<?, ?> skin : skins) {
                if (skin.get("itemDamage").equals(vehicleData.get("skinDamage"))) {
                    if (skin.get("SkinItem").equals(vehicleData.get("skinItem"))) {
                        if (skin.get("nbtValue") != null) {
                            if (skin.get("nbtValue").equals(vehicleData.get("nbtValue"))) {
                                matchedVehicles.add(configVehicle);
                            }
                        } else {
                            matchedVehicles.add(configVehicle);
                        }
                    }
                }
            }
        }
        if (matchedVehicles.size() == 0) return null;
        if (matchedVehicles.size() > 1) return null;
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleData(matchedVehicles.get(0));
        vehicle.setLicensePlate(licensePlate);
        vehicle.setOpen(false);
        vehicle.setName((String) vehicleData.get("name"));
        vehicle.setVehicleType((String) vehicleData.get("vehicleType"));
        vehicle.setSkinDamage((Integer) vehicleData.get("skinDamage"));
        vehicle.setSkinItem((String) vehicleData.get("skinItem"));
        vehicle.setGlow((Boolean) vehicleData.get("isGlow"));
        vehicle.setHornEnabled(ConfigModule.vehicleDataConfig.isHornSet(licensePlate) ? (boolean) vehicleData.get("hornEnabled") : ConfigModule.vehicleDataConfig.isHornEnabled(licensePlate));
        vehicle.setHealth(ConfigModule.vehicleDataConfig.isHealthSet(licensePlate) ? (double) vehicleData.get("health") : ConfigModule.vehicleDataConfig.getHealth(licensePlate));
        vehicle.setBenzineEnabled((Boolean) vehicleData.get("benzineEnabled"));
        vehicle.setFuel((Double) vehicleData.get("benzine"));
        vehicle.setFuelUsage((Double) vehicleData.get("benzineVerbruik"));
        vehicle.setTrunk((Boolean) vehicleData.get("kofferbak"));
        vehicle.setTrunkRows((Integer) vehicleData.get("kofferbakRows"));
        vehicle.setTrunkData((List<String>) vehicleData.get("kofferbakData"));
        vehicle.setAccelerationSpeed((Double) vehicleData.get("acceleratieSpeed"));
        vehicle.setMaxSpeed((Double) vehicleData.get("maxSpeed"));
        vehicle.setBrakingSpeed((Double) vehicleData.get("brakingSpeed"));
        vehicle.setFrictionSpeed((Double) vehicleData.get("aftrekkenSpeed"));
        vehicle.setRotateSpeed((Integer) vehicleData.get("rotateSpeed"));
        vehicle.setMaxSpeedBackwards((Double) vehicleData.get("maxSpeedBackwards"));
        vehicle.setOwner((String) vehicleData.get("owner"));
        vehicle.setRiders((List<String>) vehicleData.get("riders"));
        vehicle.setMembers((List<String>) vehicleData.get("members"));
        return vehicle;
    }

    public static boolean existsByLicensePlate(String licensePlate) {
        return ConfigModule.vehicleDataConfig.getConfig().get(String.format("vehicle.%s", licensePlate)) != null;
    }

    public static boolean canRide(Player player, String licensePlate) {
        return ConfigModule.vehicleDataConfig.getRiders(licensePlate).contains(player.getUniqueId().toString());
    }

    public static boolean canSit(Player player, String licensePlate) {
        return ConfigModule.vehicleDataConfig.getMembers(licensePlate).contains(player.getUniqueId().toString());
    }

    public static UUID getOwnerUUID(String licensePlate) {
        if (ConfigModule.vehicleDataConfig.getConfig().getString("vehicle." + licensePlate + ".owner") == null) {
            return null;
        }
        return UUID.fromString(ConfigModule.vehicleDataConfig.getConfig().getString("vehicle." + licensePlate + ".owner"));
    }

    public static void openTrunk(Player p, String license) {
        if ((boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.TRUNK_ENABLED)) {
            if (VehicleUtils.getVehicle(license) == null) {
                ConfigModule.messagesConfig.sendMessage(p, Message.VEHICLE_NOT_FOUND);
                return;
            }

            if (VehicleUtils.getVehicle(license).isOwner(p) || p.hasPermission("svehicles.kofferbak")) {
                ConfigModule.configList.forEach(Config::reload);
                Inventory inv = Bukkit.createInventory(null, (int) ConfigModule.vehicleDataConfig.get(license, VehicleDataConfig.Option.TRUNK_ROWS) * 9, InventoryTitle.VEHICLE_TRUNK.getStringTitle());

                if (ConfigModule.vehicleDataConfig.get(license, VehicleDataConfig.Option.TRUNK_DATA) != null) {
                    List<ItemStack> chestContentsFromConfig = (List<ItemStack>) ConfigModule.vehicleDataConfig.get(license, VehicleDataConfig.Option.TRUNK_DATA);

                    for (ItemStack item : chestContentsFromConfig) {
                        if (item != null) inv.addItem(item);
                    }
                }

                openedTrunk.put(p, license);
                p.openInventory(inv);

            } else {
                p.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_NO_RIDER_TRUNK).replace("%p%", VehicleUtils.getVehicle(license).getOwnerName())));
            }
        }
    }

    public static boolean isInsideVehicle(Player p) {
        if (p == null) return false;
        if (!p.isInsideVehicle()) return false;
        return VehicleUtils.isVehicle(p.getVehicle());
    }

    @Deprecated
    public static String getRidersAsString(String licensePlate) {
        StringBuilder sb = new StringBuilder();
        for (String s : ConfigModule.vehicleDataConfig.getConfig().getStringList("vehicle." + licensePlate + ".riders")) {
            if (!UUID.fromString(s).equals(getOwnerUUID(licensePlate))) {
                sb.append(Bukkit.getOfflinePlayer(UUID.fromString(s)).getName()).append(", ");
            }
        }
        if (sb.toString().isEmpty()) {
            sb.append("Niemand");
        }
        return sb.toString();
    }

    public static void pickupVehicle(String license, Player player) {
        if (getVehicle(license) == null) {
            for (World world : Bukkit.getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getCustomName() != null && entity.getCustomName().contains(license)) {
                        ArmorStand test = (ArmorStand) entity;
                        if (test.getCustomName().contains("svehicles_SKIN_" + license)) {
                            if (!TextUtils.checkInvFull(player)) {
                                player.getInventory().addItem(test.getHelmet());
                            } else {
                                ConfigModule.messagesConfig.sendMessage(player, Message.INVENTORY_FULL);
                                return;
                            }
                        }
                        test.remove();
                    }
                }
            }
            ConfigModule.messagesConfig.sendMessage(player, Message.VEHICLE_NOT_FOUND);
            return;
        }
        if (getVehicle(license).isOwner(player) && !((boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.CAR_PICKUP)) || player.hasPermission("svehicles.oppakken")) {
            for (World world : Bukkit.getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getCustomName() != null && entity.getCustomName().contains(license)) {
                        ArmorStand test = (ArmorStand) entity;
                        if (test.getCustomName().contains("svehicles_SKIN_" + license)) {
                            if (!TextUtils.checkInvFull(player)) {
                                player.getInventory().addItem(test.getHelmet());
                                player.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_PICKUP).replace("%p%", getVehicle(license).getOwnerName())));
                            } else {
                                ConfigModule.messagesConfig.sendMessage(player, Message.INVENTORY_FULL);
                                return;
                            }
                        }
                        test.remove();
                    }
                }
            }
        } else {
            if ((boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.CAR_PICKUP)) {
                player.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.CANNOT_DO_THAT_HERE)));
                return;
            }
            player.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_NO_OWNER_PICKUP).replace("%p%", getVehicle(license).getOwnerName())));
            return;
        }
    }

    @ToDo("Beautify the code inside this method.")
    public static void enterVehicle(String licensePlate, Player p) {
        if (!(VehicleData.autostand2.get(licensePlate) == null)) {
            if (!VehicleData.autostand2.get(licensePlate).isEmpty()) {
                return;
            }
        }

        Vehicle vehicle = getVehicle(licensePlate);

        if (vehicle == null) {
            ConfigModule.messagesConfig.sendMessage(p, Message.VEHICLE_NOT_FOUND);
            return;
        }

        if (!vehicle.isOpen() && !vehicle.isOwner(p) && !vehicle.canRide(p) && !p.hasPermission("svehicles.ride")) {
            p.sendMessage(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_NO_RIDER_ENTER).replace("%p%", vehicle.getOwnerName()));
            return;
        }

        for (Entity entity : p.getWorld().getEntities()) {

            if (entity.getCustomName() != null && entity.getCustomName().contains(licensePlate)) {
                ArmorStand vehicleAs = (ArmorStand) entity;
                if (!entity.isEmpty()) {
                    return;
                }
                VehicleData.fuel.put(licensePlate, vehicle.getFuel());
                VehicleData.fuelUsage.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.FUEL_USAGE));
                VehicleData.type.put(licensePlate, VehicleType.valueOf(ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.VEHICLE_TYPE).toString().toUpperCase(Locale.ROOT)));

                VehicleData.RotationSpeed.put(licensePlate, (int) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.ROTATION_SPEED));
                VehicleData.MaxSpeed.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.MAX_SPEED));
                VehicleData.AccelerationSpeed.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.ACCELARATION_SPEED));
                VehicleData.BrakingSpeed.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.BRAKING_SPEED));
                VehicleData.MaxSpeedBackwards.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.MAX_SPEED_BACKWARDS));
                VehicleData.FrictionSpeed.put(licensePlate, (double) ConfigModule.vehicleDataConfig.get(licensePlate, VehicleDataConfig.Option.FRICTION_SPEED));

                Location location = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch());

                if (!ConfigModule.defaultConfig.canProceedWithAction(RegionAction.ENTER, vehicle.getVehicleType(), location)) {
                    ConfigModule.messagesConfig.sendMessage(p, Message.CANNOT_DO_THAT_HERE);
                    return;
                }

                if (vehicleAs.getCustomName().contains("svehicles_SKIN_" + licensePlate)) {
                    basicStandCreator(licensePlate, "SKIN", location, vehicleAs.getHelmet(), false);
                    basicStandCreator(licensePlate, "MAIN", location, null, true);
                    List<Map<String, Double>> seats = (List<Map<String, Double>>) vehicle.getVehicleData().get("seats");
                    for (int i = 1; i <= seats.size(); i++) {
                        Map<String, Double> seat = seats.get(i - 1);
                        if (i == 1) {
                            mainSeatStandCreator(licensePlate, location, p, seat.get("x"), seat.get("y"), seat.get("z"));
                            BossBarUtils.addBossBar(p, licensePlate);
                            p.sendMessage(TextUtils.colorize(ConfigModule.messagesConfig.getMessage(Message.VEHICLE_ENTER_RIDER).replace("%p%", getVehicle(licensePlate).getOwnerName())));
                        }
                        if (i > 1) {
                            VehicleData.seatsize.put(licensePlate, seats.size());
                            VehicleData.seatx.put("svehicles_SEAT" + (Integer) i + "_" + licensePlate, seat.get("x"));
                            VehicleData.seaty.put("svehicles_SEAT" + (Integer) i + "_" + licensePlate, seat.get("y"));
                            VehicleData.seatz.put("svehicles_SEAT" + (Integer) i + "_" + licensePlate, seat.get("z"));
                            Location location2 = new Location(location.getWorld(), location.getX() + Double.valueOf(seat.get("z")), location.getY() + Double.valueOf(seat.get("y")), location.getZ() + Double.valueOf(seat.get("x")));
                            ArmorStand as = location2.getWorld().spawn(location2, ArmorStand.class);
                            as.setCustomName("svehicles_SEAT" + (Integer) i + "_" + licensePlate);
                            as.setGravity(false);
                            as.setVisible(false);
                            VehicleData.autostand.put("svehicles_SEAT" + (Integer) i + "_" + licensePlate, as);
                        }
                    }
                    List<Map<String, Double>> wiekens = (List<Map<String, Double>>) vehicle.getVehicleData().get("wiekens");
                    VehicleType vehicleType = ConfigModule.vehicleDataConfig.getType(licensePlate);
                    if (vehicleType.isHelicopter()) {
                        VehicleData.maxheight.put(licensePlate, (int) ConfigModule.defaultConfig.get(DefaultConfig.Option.MAX_FLYING_HEIGHT));
                        for (int i = 1; i <= wiekens.size(); i++) {
                            Map<?, ?> seat = wiekens.get(i - 1);
                            if (i == 1) {
                                Location location2 = new Location(location.getWorld(), location.getX() + (Double) seat.get("z"), (Double) location.getY() + (Double) seat.get("y"), location.getZ() + (Double) seat.get("x"));
                                VehicleData.wiekenx.put("svehicles_WIEKENS_" + licensePlate, (Double) seat.get("x"));
                                VehicleData.wiekeny.put("svehicles_WIEKENS_" + licensePlate, (Double) seat.get("y"));
                                VehicleData.wiekenz.put("svehicles_WIEKENS_" + licensePlate, (Double) seat.get("z"));
                                ArmorStand as = location2.getWorld().spawn(location2, ArmorStand.class);
                                as.setCustomName("svehicles_WIEKENS_" + licensePlate);
                                as.setGravity(false);
                                as.setVisible(false);
                                VehicleData.autostand.put("svehicles_WIEKENS_" + licensePlate, as);
                                as.setHelmet((ItemStack) seat.get("item"));
                            }
                        }
                    }
                }
                vehicleAs.remove();
            }
        }
    }

    private static void basicStandCreator(String license, String type, Location location, ItemStack item, Boolean gravity) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);
        as.setCustomName("svehicles_" + type + "_" + license);
        as.setHelmet(item);
        as.setGravity(gravity);
        as.setVisible(false);
        VehicleData.autostand.put("svehicles_" + type + "_" + license, as);
    }

    private static void mainSeatStandCreator(String license, Location location, Player p, double x, double y, double z) {
        Location location2 = new Location(location.getWorld(), location.getX() + Double.valueOf(z), location.getY() + Double.valueOf(y), location.getZ() + Double.valueOf(z));
        ArmorStand as = location2.getWorld().spawn(location2, ArmorStand.class);
        as.setCustomName("svehicles_MAINSEAT_" + license);
        VehicleData.autostand.put("svehicles_MAINSEAT_" + license, as);
        as.setGravity(false);
        VehicleData.speed.put(license, 0.0);
        VehicleData.speedhigh.put(license, 0.0);
        VehicleData.mainx.put("svehicles_MAINSEAT_" + license, x);
        VehicleData.mainy.put("svehicles_MAINSEAT_" + license, y);
        VehicleData.mainz.put("svehicles_MAINSEAT_" + license, z);
        as.setPassenger(p);
        as.setVisible(false);
        VehicleData.autostand2.put(license, as);
    }
}
