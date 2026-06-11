package net.darkygaia.beyondimagination.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import net.darkygaia.beyondimagination.BeyondImagination;

@JeiPlugin
public class JEIIntegration implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // Stubs para:
        // Centrifugação
        // Irradiação Passiva
        // Irradiação Elétrica
        // Pressurização
        // Reprocessamento Nuclear
        // Impressão de Chips
    }
}
