package net.darkygaia.beyondimagination.registry;

import com.mojang.serialization.Codec;
import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BeyondImagination.MODID);

    public static final Supplier<DataComponentType<Float>> FLUID_PSI = COMPONENTS.register("fluid_psi",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).build()
    );

    public static void register(net.neoforged.bus.api.IEventBus bus) {
        COMPONENTS.register(bus);
    }
}
