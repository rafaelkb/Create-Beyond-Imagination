package net.darkygaia.beyondimagination.registry;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondImagination.MODID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<CreativeModeTab, CreativeModeTab> BEYOND_IMAGINATION_TAB = CREATIVE_MODE_TABS.register("cbi_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.beyondimagination"))
                    .icon(() -> new ItemStack(ModItems.YELLOW_CAKE.get()))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
