package net.darkygaia.beyondimagination.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.radiation.RadiationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = BeyondImagination.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RadiationOverlay {

    private static final ResourceLocation VIGNETTE_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, "radiation_overlay"), RadiationOverlay::renderOverlay);
    }

    private static void renderOverlay(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        float rad = RadiationManager.getRadiation(player);
        if (rad < 200) return;

        // Calculate opacity based on radiation (200 -> 0.0, 1000 -> 1.0)
        float opacity = Mth.clamp((rad) / 2000f, 0.0f, 1.0f);

        // Render vignette
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderColor(0.2F, 1.0F, 0.2F, opacity);
        
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        guiGraphics.blit(VIGNETTE_LOCATION, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
