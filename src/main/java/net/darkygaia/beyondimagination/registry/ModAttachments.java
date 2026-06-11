package net.darkygaia.beyondimagination.registry;

import com.mojang.serialization.Codec;
import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BeyondImagination.MODID);

    public static final Supplier<AttachmentType<Float>> RADIATION_LEVEL = ATTACHMENTS.register(
            "radiation_level",
            () -> AttachmentType.builder(() -> 0.0f)
                    .serialize(Codec.FLOAT)
                    .build()
    );

    public static void register(net.neoforged.bus.api.IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
