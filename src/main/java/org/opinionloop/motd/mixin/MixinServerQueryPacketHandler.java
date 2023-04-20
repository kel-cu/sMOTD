package org.opinionloop.motd.mixin;

import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import org.opinionloop.motd.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerQueryNetworkHandler.class)
public class MixinServerQueryPacketHandler {

    @Redirect(method = "onRequest", at = @At(value = "NEW", target = "net/minecraft/network/packet/s2c/query/QueryResponseS2CPacket"))
    private QueryResponseS2CPacket getMetadata(ServerMetadata metadata) {
        Main.setMetaData();
        return new QueryResponseS2CPacket(metadata);
    }
}
