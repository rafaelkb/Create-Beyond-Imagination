package net.darkygaia.beyondimagination.network;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = BeyondImagination.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(BeyondImagination.MODID).versioned("1.0");
        registrar.playToClient(RadiationSyncPacket.TYPE, RadiationSyncPacket.STREAM_CODEC, RadiationSyncPacket::handle);
    }
}
