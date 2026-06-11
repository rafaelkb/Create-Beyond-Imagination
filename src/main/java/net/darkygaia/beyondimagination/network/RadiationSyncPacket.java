package net.darkygaia.beyondimagination.network;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.registry.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RadiationSyncPacket(float radiation) implements CustomPacketPayload {
    public static final Type<RadiationSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, "radiation_sync"));
    public static final StreamCodec<FriendlyByteBuf, RadiationSyncPacket> STREAM_CODEC = StreamCodec.ofMember(RadiationSyncPacket::write, RadiationSyncPacket::new);

    public RadiationSyncPacket(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.radiation);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RadiationSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.setData(ModAttachments.RADIATION_LEVEL, packet.radiation());
            }
        });
    }
}
