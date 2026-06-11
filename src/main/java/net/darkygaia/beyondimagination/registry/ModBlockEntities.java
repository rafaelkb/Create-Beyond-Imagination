package net.darkygaia.beyondimagination.registry;

import com.simibubi.create.content.fluids.tank.FluidTankRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {

    public static final BlockEntityEntry<com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity> TITANIUM_PIPE = ModRegistry.REGISTRATE
            .blockEntity("titanium_pipe", com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity::new)
            .validBlocks(ModBlocks.TITANIUM_PIPE)
            .register();

    public static void register() {}
}
