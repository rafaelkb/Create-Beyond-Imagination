package net.darkygaia.beyondimagination.client;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.radiation.RadiationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

import java.util.Random;

@EventBusSubscriber(modid = BeyondImagination.MODID, value = Dist.CLIENT)
public class CameraShakeHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        float rad = RadiationManager.getRadiation(player);
        if (rad > 1000) {
            // Tremor scale based on radiation
            float intensity = (rad - 1000f) / 1000f; // 0.0 at 1000, 1.0 at 2000
            intensity = Math.min(1.0f, intensity); // cap at 1.0

            // Apply tiny random offsets to pitch and yaw
            float pitchOffset = (RANDOM.nextFloat() - 0.5f) * 2.0f * intensity;
            float yawOffset = (RANDOM.nextFloat() - 0.5f) * 2.0f * intensity;
            float rollOffset = (RANDOM.nextFloat() - 0.5f) * 1.0f * intensity;

            event.setPitch(event.getPitch() + pitchOffset);
            event.setYaw(event.getYaw() + yawOffset);
            event.setRoll(event.getRoll() + rollOffset);
        }
    }
}
