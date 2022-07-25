package fr.sulfuris.dev.vehicles.movement;

import fr.sulfuris.dev.vehicles.infrastructure.annotations.VersionSpecific;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.DefaultConfig;
import fr.sulfuris.dev.vehicles.infrastructure.dataconfig.VehicleDataConfig;
import fr.sulfuris.dev.vehicles.infrastructure.enums.ServerVersion;
import fr.sulfuris.dev.vehicles.infrastructure.enums.VehicleType;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.BossBarUtils;
import fr.sulfuris.dev.vehicles.infrastructure.helpers.VehicleData;
import fr.sulfuris.dev.vehicles.infrastructure.modules.ConfigModule;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static fr.sulfuris.dev.main.schedulerRun;
import static fr.sulfuris.dev.vehicles.infrastructure.modules.VersionModule.getServerVersion;
import static fr.sulfuris.dev.vehicles.movement.PacketHandler.isObjectPacket;

public class VehicleMovement {
    protected Object packet;

    protected Player player;
    protected VehicleType vehicleType;
    protected String license;

    protected ArmorStand standMain;
    protected ArmorStand standSkin;
    protected ArmorStand standMainSeat;
    protected @Nullable ArmorStand standRotors;

    protected boolean isFalling = false;
    protected boolean extremeFalling = false;

    @VersionSpecific
    protected static String getTeleportMethod() {
        if (getServerVersion().isNewerOrEqualTo(ServerVersion.v1_18_R1)) return "a";
        else return "setLocation";
    }

    public void vehicleMovement(Player player, Object packet) {

        if (!isObjectPacket(packet)) return;
        this.packet = packet;

        this.player = player;
        long lastUsed = 0L;

        if (player.getVehicle() == null) return;
        final Entity vehicle = player.getVehicle();

        if (!(vehicle instanceof ArmorStand)) return;
        if (vehicle.getCustomName() == null) return;

        if (vehicle.getCustomName().replace("svehicles_MAINSEAT_", "").isEmpty())
            return;
        this.license = vehicle.getCustomName().replace("svehicles_MAINSEAT_", "");

        if (VehicleData.autostand.get("svehicles_MAIN_" + license) == null) return;

        if (VehicleData.speed.get(license) == null) {
            VehicleData.speed.put(license, 0.0);
            return;
        }

        vehicleType = VehicleData.type.get(license);
        if (vehicleType == null) return;

        if (VehicleData.fuel.get(license) < 1) {
            BossBarUtils.setBossBarValue(0 / 100.0D, license);
            if (vehicleType.canFly()) {
                isFalling = true;
                extremeFalling = vehicleType.isHelicopter() && (boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.EXTREME_HELICOPTER_FALL);
            } else return;
        }

        BossBarUtils.setBossBarValue(VehicleData.fuel.get(license) / 100.0D, license);

        standMain = VehicleData.autostand.get("svehicles_MAIN_" + license);
        standSkin = VehicleData.autostand.get("svehicles_SKIN_" + license);
        standMainSeat = VehicleData.autostand.get("svehicles_MAINSEAT_" + license);
        standRotors = VehicleData.autostand.get("svehicles_WIEKENS_" + license);

        if ((boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.DAMAGE_ENABLED) && ConfigModule.vehicleDataConfig.getHealth(license) == 0) {
            standMain.getWorld().spawnParticle(Particle.SMOKE_NORMAL, standMain.getLocation(), 2);
            return;
        }

        schedulerRun(() -> standSkin.teleport(new Location(standMain.getLocation().getWorld(), standMain.getLocation().getX(), standMain.getLocation().getY(), standMain.getLocation().getZ(), standMain.getLocation().getYaw(), standMain.getLocation().getPitch())));

        updateStand();
        if (!vehicleType.canFly()) blockCheck();
        mainSeat();

        if (VehicleData.seatsize.get(license + "addon") != null) {
            for (int i = 1; i <= VehicleData.seatsize.get(license + "addon"); i++) {
                ArmorStand standAddon = VehicleData.autostand.get("svehicles_ADDON" + i + "_" + license);
                schedulerRun(() -> standAddon.teleport(standMain.getLocation()));
            }
        }

        if (vehicleType.isHelicopter()) rotors();

        if (ConfigModule.vehicleDataConfig.isHornEnabled(license) && steerIsJumping() && !isFalling) {
            if (VehicleData.lastUsage.containsKey(player.getName()))
                lastUsed = VehicleData.lastUsage.get(player.getName());

            if (System.currentTimeMillis() - lastUsed >= Long.parseLong(ConfigModule.defaultConfig.get(DefaultConfig.Option.HORN_COOLDOWN).toString()) * 1000L) {
                standMain.getWorld().playSound(standMain.getLocation(), Objects.requireNonNull(ConfigModule.defaultConfig.get(DefaultConfig.Option.HORN_TYPE).toString()), 0.9f, 1f);
                VehicleData.lastUsage.put(player.getName(), System.currentTimeMillis());
            }
        }

        if (vehicleType.isTank() && steerIsJumping()) {
            if (VehicleData.lastUsage.containsKey(player.getName()))
                lastUsed = VehicleData.lastUsage.get(player.getName());

            if (System.currentTimeMillis() - lastUsed >= Long.parseLong(ConfigModule.defaultConfig.get(DefaultConfig.Option.TANK_COOLDOWN).toString()) * 1000L) {
                standMain.getWorld().playEffect(standMain.getLocation(), Effect.BLAZE_SHOOT, 1, 1);
                standMain.getWorld().playEffect(standMain.getLocation(), Effect.GHAST_SHOOT, 1, 1);
                standMain.getWorld().playEffect(standMain.getLocation(), Effect.WITHER_BREAK_BLOCK, 1, 1);
                double xOffset = 4;
                double yOffset = 1.6;
                double zOffset = 0;
                Location locvp = standMain.getLocation().clone();
                Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
                float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
                float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
                Location loc = new Location(standMain.getWorld(), xvp, standMain.getLocation().getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());
                spawnParticles(standMain, loc);
                spawnTNT(standMain, loc);
                VehicleData.lastUsage.put(player.getName(), System.currentTimeMillis());
            }
        }

        rotation();
        move();
    }

    protected void rotation() {
        final int rotationSpeed = VehicleData.RotationSpeed.get(license);
        final Location locBelow = new Location(standMain.getLocation().getWorld(), standMain.getLocation().getX(), standMain.getLocation().getY() - 0.2, standMain.getLocation().getZ(), standMain.getLocation().getYaw(), standMain.getLocation().getPitch());

        if (isFalling && vehicleType.isHelicopter()) return;
        if (vehicleType.isHelicopter() && !locBelow.getBlock().getType().equals(Material.AIR)) return;

        if (ConfigModule.defaultConfig.usePlayerFacingDriving()) {
            rotateVehicle(player.getLocation().getYaw());
        } else {
            final int rotation = (VehicleData.speed.get(license) < 0.1) ? rotationSpeed / 3 : rotationSpeed;
            if (steerGetXxa() > 0) rotateVehicle(standMain.getLocation().getYaw() - rotation);
            else if (steerGetXxa() < 0) rotateVehicle(standMain.getLocation().getYaw() + rotation);
        }
    }

    protected void rotateVehicle(float yaw) {
        schedulerRun(() -> {
            standMain.setRotation(yaw, standMain.getLocation().getPitch());
            standMainSeat.setRotation(yaw, standMain.getLocation().getPitch());
            standSkin.setRotation(yaw, standMain.getLocation().getPitch());
        });
    }

    protected void move() {
        final double maxSpeed = VehicleData.MaxSpeed.get(license);
        final double accelerationSpeed = VehicleData.AccelerationSpeed.get(license);
        final double brakingSpeed = VehicleData.BrakingSpeed.get(license);
        final double maxSpeedBackwards = VehicleData.MaxSpeedBackwards.get(license);
        final Location locBelow = new Location(standMain.getLocation().getWorld(), standMain.getLocation().getX(), standMain.getLocation().getY() - 0.2, standMain.getLocation().getZ(), standMain.getLocation().getYaw(), standMain.getLocation().getPitch());

        if (steerGetZza() == 0.0 && !locBelow.getBlock().getType().equals(Material.AIR)) {
            putFrictionSpeed();
        }

        if (steerGetZza() > 0.0) {
            if (VehicleData.speed.get(license) < 0) {
                VehicleData.speed.put(license, VehicleData.speed.get(license) + brakingSpeed);
                return;
            }
            putFuelUsage();

            if (VehicleData.speed.get(license) > maxSpeed - accelerationSpeed) return;
            VehicleData.speed.put(license, VehicleData.speed.get(license) + accelerationSpeed);
        }
        if (steerGetZza() < 0.0) {
            if (VehicleData.speed.get(license) > 0) {
                VehicleData.speed.put(license, VehicleData.speed.get(license) - brakingSpeed);
                return;
            }
            putFuelUsage();

            if (VehicleData.speed.get(license) < -maxSpeedBackwards) return;
            VehicleData.speed.put(license, VehicleData.speed.get(license) - accelerationSpeed);
        }
    }

    protected void putFrictionSpeed() {
        final double frictionSpeed = VehicleData.FrictionSpeed.get(license);
        BigDecimal round = BigDecimal.valueOf(VehicleData.speed.get(license)).setScale(1, BigDecimal.ROUND_DOWN);
        if (Double.parseDouble(String.valueOf(round)) == 0.0) {
            VehicleData.speed.put(license, 0.0);
            return;
        }
        if (Double.parseDouble(String.valueOf(round)) > 0.01) {
            VehicleData.speed.put(license, VehicleData.speed.get(license) - frictionSpeed);
            return;
        }
        if (Double.parseDouble(String.valueOf(round)) < 0.01) {
            VehicleData.speed.put(license, VehicleData.speed.get(license) + frictionSpeed);
        }
    }

    @Deprecated
    protected boolean slabCheck() {
        return blockCheck();
    }

    protected boolean blockCheck() {
        final Location loc = getLocationOfBlockAhead();
        final String locY = String.valueOf(standMain.getLocation().getY());
        final Location locBlockAbove = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ(), loc.getYaw(), loc.getPitch());
        final Location locBlockBelow = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ(), loc.getYaw(), loc.getPitch());
        final String drivingOnY = locY.substring(locY.length() - 2);

        final boolean isOnGround = drivingOnY.contains(".0");
        final boolean isOnSlab = drivingOnY.contains(".5");
        final boolean isPassable = isPassable(loc.getBlock());
        final boolean isAbovePassable = isPassable(locBlockAbove.getBlock());

        final double difference = Double.parseDouble("0." + locY.split("\\.")[1]);
        final BlockData blockData = loc.getBlock().getBlockData();
        final BlockData blockDataBelow = locBlockBelow.getBlock().getBlockData();

        if (loc.getBlock().getType().toString().contains("CARPET")) {
            if (!(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.DRIVE_ON_CARPETS)) {
                VehicleData.speed.put(license, 0.0);
                return false;
            }

            if (!isAbovePassable) {
                VehicleData.speed.put(license, 0.0);
                return false;
            }

            if (isOnGround) pushVehicleUp(0.0625);
            return true;
        }

        if (blockData instanceof Snow) {
            final int layers = ((Snow) blockData).getLayers();
            double layerHeight = getLayerHeight(layers);
            if (VehicleData.speed.get(license) > 0.1) VehicleData.speed.put(license, 0.1);

            if (layerHeight == difference) return false;
            final double snowDifference = layerHeight - difference;
            pushVehicleUp(snowDifference);
            return true;
        }

        if (blockData instanceof Fence || loc.getBlock().getType().toString().contains("WALL") || blockData instanceof TrapDoor) {
            VehicleData.speed.put(license, 0.0);
            return false;
        }

        if (ConfigModule.defaultConfig.driveUpSlabs().isSlabs()) {
            if (isOnSlab) {
                if (isPassable) {
                    pushVehicleDown(0.5);
                    return false;
                }

                if (blockData instanceof Slab) {
                    Slab slab = (Slab) blockData;
                    if (slab.getType().equals(Slab.Type.BOTTOM)) {
                        return false;
                    }
                }

                if (!isAbovePassable) {
                    VehicleData.speed.put(license, 0.0);
                    return false;
                }


                pushVehicleUp(0.5);
                return true;
            }

            if (!isPassable) {
                if (blockData instanceof Slab) {
                    Slab slab = (Slab) blockData;
                    if (slab.getType().equals(Slab.Type.BOTTOM)) {

                        if (!isAbovePassable) {
                            VehicleData.speed.put(license, 0.0);
                            return false;
                        }

                        if (isOnGround) {
                            pushVehicleUp(0.5);
                        } else {
                            if ((0.5 - difference) > 0) pushVehicleUp(0.5 - difference);
                        }
                    }
                }

                VehicleData.speed.put(license, 0.0);
                return false;
            }

        } else if (ConfigModule.defaultConfig.driveUpSlabs().isBlocks()) {

            if (!isOnSlab) {
                if (!isPassable) {
                    if (blockData instanceof Slab) {
                        Slab slab = (Slab) blockData;
                        if (slab.getType().equals(Slab.Type.BOTTOM)) {
                            VehicleData.speed.put(license, 0.0);
                            return false;
                        }
                    }

                    if (!isAbovePassable) {
                        VehicleData.speed.put(license, 0.0);
                        return false;
                    }

                    if (isOnGround) {
                        pushVehicleUp(1);
                    } else {
                        if ((1 - difference) > 0) pushVehicleUp(1 - difference);
                    }

                    return true;
                }
            }

            if (isPassable) {
                pushVehicleDown(0.5);
                return false;
            }

            if (blockData instanceof Slab) {
                Slab slab = (Slab) blockData;
                if (slab.getType().equals(Slab.Type.BOTTOM)) {
                    return false;
                }
            }

            if (!isAbovePassable) {
                VehicleData.speed.put(license, 0.0);
                return false;
            }

            pushVehicleUp(0.5);
            return true;

        } else if (ConfigModule.defaultConfig.driveUpSlabs().isBoth()) {

            if (isOnSlab) {
                if (isPassable) {
                    pushVehicleDown(0.5);
                    return false;
                }

                if (blockData instanceof Slab) {
                    Slab slab = (Slab) blockData;
                    if (slab.getType().equals(Slab.Type.BOTTOM)) {
                        return false;
                    }
                }

                if (!isAbovePassable) {
                    VehicleData.speed.put(license, 0.0);
                    return false;
                }

                pushVehicleUp(0.5);
                return true;
            }

            if (!isPassable) {

                if (!isAbovePassable) {
                    VehicleData.speed.put(license, 0.0);
                    return false;
                }

                if (blockData instanceof Slab) {
                    Slab slab = (Slab) blockData;
                    if (slab.getType().equals(Slab.Type.BOTTOM)) {
                        if (isOnGround) {
                            pushVehicleUp(0.5);
                        } else {
                            if ((0.5 - difference) > 0) pushVehicleUp(0.5 - difference);
                        }
                        return true;
                    }
                }

                if (isOnGround) {
                    pushVehicleUp(1);
                } else {
                    if ((1 - difference) > 0) pushVehicleUp(1 - difference);
                }

                return true;

            }

            if (blockDataBelow instanceof Slab) {
                Slab slab = (Slab) blockDataBelow;
                if (slab.getType().equals(Slab.Type.BOTTOM)) {
                    pushVehicleDown(0.5);
                    return false;
                }
            }

        }

        return false;
    }

    protected double getLayerHeight(int layers) {
        switch (layers) {
            case 1:
                return 0.125;
            case 2:
                return 0.25;
            case 3:
                return 0.375;
            case 4:
                return 0.5;
            case 5:
                return 0.625;
            case 6:
                return 0.75;
            case 7:
                return 0.875;
            default:
                return 1;
        }
    }

    protected void mainSeat() {
        if (VehicleData.seatsize.get(license) != null) {
            for (int i = 2; i <= VehicleData.seatsize.get(license); i++) {
                ArmorStand seatas = VehicleData.autostand.get("svehicles_SEAT" + i + "_" + license);
                double xOffset = VehicleData.seatx.get("svehicles_SEAT" + i + "_" + license);
                double yOffset = VehicleData.seaty.get("svehicles_SEAT" + i + "_" + license);
                double zOffset = VehicleData.seatz.get("svehicles_SEAT" + i + "_" + license);
                Location locvp = standMain.getLocation().clone();
                Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
                float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
                float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
                Location loc = new Location(standMain.getWorld(), xvp, standMain.getLocation().getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());
                teleportSeat(seatas, loc);
            }
        }
        double xOffset = VehicleData.mainx.get("svehicles_MAINSEAT_" + license);
        double yOffset = VehicleData.mainy.get("svehicles_MAINSEAT_" + license);
        double zOffset = VehicleData.mainz.get("svehicles_MAINSEAT_" + license);
        Location locvp = standMain.getLocation().clone();
        Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
        float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
        float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
        Location loc = new Location(standMain.getWorld(), xvp, standMain.getLocation().getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());
        teleportSeat(standMainSeat, loc);
    }

    @VersionSpecific
    protected void teleportSeat(ArmorStand seat, Location loc) {
        if (getServerVersion().is1_16())
            teleportSeat(((org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity) seat).getHandle(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    protected void teleportSeat(Object seat, double x, double y, double z, float yaw, float pitch) {
        schedulerRun(() -> {
            try {
                Method method = seat.getClass().getSuperclass().getSuperclass().getDeclaredMethod(getTeleportMethod(), double.class, double.class, double.class, float.class, float.class);
                method.invoke(seat, x, y, z, yaw, pitch);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void updateStand() {
        final Location loc = standMain.getLocation();
        final Location locBlockAhead = getLocationOfBlockAhead();
        final Location locBlockAheadAndBelow = new Location(locBlockAhead.getWorld(), locBlockAhead.getX(), locBlockAhead.getY() - 1, locBlockAhead.getZ(), locBlockAhead.getPitch(), locBlockAhead.getYaw());
        final Location locBelow = new Location(loc.getWorld(), loc.getX(), loc.getY() - 0.2, loc.getZ(), loc.getYaw(), loc.getPitch());

        final Material block = locBelow.getBlock().getType();
        final String blockName = block.toString();

        boolean space = !isFalling && steerIsJumping();

        if (vehicleType.canFly()) {

            if (vehicleType.isAirplane() && isFalling && !block.equals(Material.AIR)) {
                putFrictionSpeed();
                standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), 0.0, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                return;
            }

            if ((vehicleType.isHelicopter() && !isPassable(locBelow.getBlock()))
                    || (vehicleType.isAirplane() && VehicleData.fuel.get(license) < 1 && !block.equals(Material.AIR))
            ) VehicleData.speed.put(license, 0.0);

            if (space) {
                final double takeOffSpeed = ((double) ConfigModule.defaultConfig.get(DefaultConfig.Option.TAKE_OFF_SPEED) > 0) ? (double) ConfigModule.defaultConfig.get(DefaultConfig.Option.TAKE_OFF_SPEED) : 0.4;
                if (vehicleType.isAirplane() && VehicleData.speed.get(license) < takeOffSpeed) {
                    double y = (isPassable(locBelow.getBlock())) ? -0.2 : 0;
                    standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), y, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                    return;
                }

                putFuelUsage();

                if (loc.getY() > (int) ConfigModule.defaultConfig.get(DefaultConfig.Option.MAX_FLYING_HEIGHT)) return;
                standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), 0.2, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                return;
            }

            if (extremeFalling) {
                if (standMain.isOnGround()) {
                    if (VehicleData.fallDamage.get(license) == null) {
                        double damageAmount = ((double) ConfigModule.defaultConfig.get(DefaultConfig.Option.HELICOPTER_FALL_DAMAGE) <= 0) ?
                                (double) DefaultConfig.Option.HELICOPTER_FALL_DAMAGE.getDefaultValue() : (double) ConfigModule.defaultConfig.get(DefaultConfig.Option.HELICOPTER_FALL_DAMAGE);
                        schedulerRun(() -> {
                            player.damage(damageAmount);
                            if (VehicleData.seatsize.get(license) != null) {
                                for (int i = 2; i <= VehicleData.seatsize.get(license); i++) {
                                    ArmorStand seat = VehicleData.autostand.get("svehicles_SEAT" + i + "_" + license);
                                    List<Entity> passengers = seat.getPassengers();
                                    for (Entity p : passengers) {
                                        if (p instanceof LivingEntity) {
                                            ((LivingEntity) p).damage(damageAmount);
                                        }
                                    }
                                }
                            }
                        });
                        VehicleData.fallDamage.put(license, true);
                    }
                }
                standMain.setGravity(true);
                return;
            }

            putFuelUsage();
            standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), -0.2, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
            return;
        }

        if (vehicleType.isHover()) {
            if (block.equals(Material.AIR)) {
                standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), -0.8, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                return;
            }
            standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), 0.00001, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
            return;
        }

        if (blockName.contains("WATER")) {
            standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), -0.8, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
            return;
        }

        if (isPassable(locBlockAhead.getBlock()) && isPassable(locBlockAheadAndBelow.getBlock())) {
            if (isPassable(locBelow.getBlock())) {
                standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), -0.8, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                return;
            }

            if (blockName.contains("CARPET")) {
                standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), -0.7375, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
                return;
            }
        }

        standMain.setVelocity(new Vector(loc.getDirection().multiply(VehicleData.speed.get(license)).getX(), 0.0, loc.getDirection().multiply(VehicleData.speed.get(license)).getZ()));
    }

    protected void putFuelUsage() {
        if (!(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.FUEL_ENABLED) || !(boolean) ConfigModule.vehicleDataConfig.get(license, VehicleDataConfig.Option.FUEL_ENABLED))
            return;

        double fuelMultiplier = Double.parseDouble(ConfigModule.defaultConfig.get(DefaultConfig.Option.FUEL_MULTIPLIER).toString());
        if (fuelMultiplier < 0.1 || fuelMultiplier > 10) fuelMultiplier = 1;
        final double newFuel = VehicleData.fuel.get(license) - (fuelMultiplier * VehicleData.fuelUsage.get(license));
        if (newFuel < 0) VehicleData.fuel.put(license, 0.0);
        else VehicleData.fuel.put(license, newFuel);
    }

    protected boolean isPassable(Block block) {
        return block.isPassable();
    }

    protected void rotors() {
        double xOffset = VehicleData.wiekenx.get("svehicles_WIEKENS_" + license);
        double yOffset = VehicleData.wiekeny.get("svehicles_WIEKENS_" + license);
        double zOffset = VehicleData.wiekenz.get("svehicles_WIEKENS_" + license);
        final Location locvp = standMain.getLocation().clone();
        final Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
        final float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(standRotors.getLocation().getYaw())));
        final float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(standRotors.getLocation().getYaw())));
        float yawAdd = (isFalling) ? 5 : 15;
        if (extremeFalling) yawAdd = 0;
        final Location loc = new Location(standMain.getWorld(), xvp, standMain.getLocation().getY() + yOffset, zvp, standRotors.getLocation().getYaw() + yawAdd, standRotors.getLocation().getPitch());
        schedulerRun(() -> standRotors.teleport(loc));
    }

    protected void pushVehicleUp(double plus) {
        final Location newLoc = new Location(standMain.getLocation().getWorld(), standMain.getLocation().getX(), standMain.getLocation().getY() + plus, standMain.getLocation().getZ(), standMain.getLocation().getYaw(), standMain.getLocation().getPitch());
        schedulerRun(() -> standMain.teleport(newLoc));
    }

    protected void pushVehicleDown(double minus) {
        pushVehicleUp(-minus);
    }

    protected Location getLocationOfBlockAhead() {
        double xOffset = 0.7;
        double yOffset = 0.4;
        double zOffset = 0.0;
        Location locvp = standMain.getLocation().clone();
        Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
        float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
        float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
        return new Location(standMain.getWorld(), xvp, standMain.getLocation().getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());
    }

    protected boolean steerIsJumping() {
        boolean isJumping = false;
        try {
            Method method = packet.getClass().getDeclaredMethod("d");
            isJumping = (Boolean) method.invoke(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isJumping;
    }

    protected float steerGetXxa() {
        float Xxa = 0;
        try {
            Method method = packet.getClass().getDeclaredMethod("b");
            Xxa = (float) method.invoke(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Xxa;
    }

    protected float steerGetZza() {
        float Zza = 0;
        try {
            Method method = packet.getClass().getDeclaredMethod("c");
            Zza = (float) method.invoke(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Zza;
    }

    @VersionSpecific
    protected void spawnParticles(ArmorStand stand, Location loc) {
        stand.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 2);
        stand.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 2);
        stand.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 5);
        if (!getServerVersion().isOlderOrEqualTo(ServerVersion.v1_13))
            stand.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, loc, 5);
    }

    protected void spawnTNT(ArmorStand stand, Location loc) {
        if (!(boolean) ConfigModule.defaultConfig.get(DefaultConfig.Option.TANK_TNT)) return;

        schedulerRun(() -> {
            TNTPrimed tnt = loc.getWorld().spawn(loc, TNTPrimed.class);
            tnt.setFuseTicks(20);
            tnt.setVelocity(stand.getLocation().getDirection().multiply(3.0));
        });
    }

}
