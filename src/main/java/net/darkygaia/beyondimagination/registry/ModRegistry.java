package net.darkygaia.beyondimagination.registry;

import com.tterrag.registrate.Registrate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.world.item.CreativeModeTab;

public class ModRegistry {

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(BeyondImagination.MODID)
            .defaultCreativeTab(ModCreativeModeTabs.BEYOND_IMAGINATION_TAB.getKey());

    public static void register() {
        // Initialize other registries to trigger classloading
        ModBlocks.register();
        ModItems.register();
        ModFluids.register();
        ModSounds.register();
        
        //  net.darkygaia.beyondimagination.integration.ie.ImmersiveEngineeringHook.init();
        net.darkygaia.beyondimagination.advancement.ModAdvancements.register();
    }
}
