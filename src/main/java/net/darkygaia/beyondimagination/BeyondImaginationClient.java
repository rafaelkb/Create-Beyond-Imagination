package net.darkygaia.beyondimagination;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = BeyondImagination.MODID, value = Dist.CLIENT)
public class BeyondImaginationClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BeyondImagination.LOGGER.info("BeyondImagination Client Setup");
        // Client-side setup code
    }
}
