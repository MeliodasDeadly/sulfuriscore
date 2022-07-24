package fr.sulfuris.dev.vehicles.movement;

import fr.sulfuris.dev.main;
import fr.sulfuris.dev.vehicles.infrastructure.annotations.VersionSpecific;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

@VersionSpecific
public class PacketHandler {

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
                NoSuchElementException e) {
        }
        try {
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (
                NoSuchElementException e) {
        }
    }

    @VersionSpecific
    public static boolean isObjectPacket(Object object) {
        final String errorMessage = "An unexpected error occurred. Try reinstalling the plugin or contact the developer: https:        if (getServerVersion().is1_16()) {
            if (!(object instanceof net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle)) {
                main.logSevere(errorMessage);
                return false;
            }
        }
        return true;
    }

}
