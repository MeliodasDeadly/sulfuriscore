package fr.sulfuris.dev.vehicles.movement;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.annotations.VersionSpecific;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static fr.sulfuris.dev.vehicles.infrastructure.modules.VersionModule.getServerVersion;

/**
 * Packet handling system in different minecraft versions.
 */
@VersionSpecific
public class PacketHandler {

    /**
     * Packet handler for vehicle steering in 1.16.5 and 1.16.4 (NMS versions 1_16_R2 and 1_16_R1 are not supported)
     *
     * @param player Player whose steering is being regarded
     */
    public static void movement_1_16(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                super.channelRead(channelHandlerContext, packet);
                if (packet instanceof net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle) {
                    net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle ppisv = (net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle) packet;
                    VehicleMovement movement = new VehicleMovement();
                    movement.vehicleMovement(player, ppisv);
                }
            }
        };
        ChannelPipeline pipeline = ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        try {
            pipeline.remove(player.getName());
        } catch (
                NoSuchElementException e) { //It isn't good practice to ignore exceptions, but I'll keep it like this for now :)
        }
        try {
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (
                NoSuchElementException e) { //It isn't good practice to ignore exceptions, but I'll keep it like this for now :)
        }
    }

    /**
     * Check whether a given object is a valid packet. If not, return false and send an error to the console.
     *
     * @param object Checked object (likely a packet)
     * @return True if the given object is an instance of the steering packet (PacketPlayInSteerVehicle).
     */
    @VersionSpecific
    public static boolean isObjectPacket(Object object) {
        final String errorMessage = "An unexpected error occurred. Try reinstalling the plugin or contact the developer: https://discord.gg/vehicle";
        if (getServerVersion().is1_16()) {
            if (!(object instanceof net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle)) {
                main.logSevere(errorMessage);
                return false;
            }
        }
        return true;
    }

}
