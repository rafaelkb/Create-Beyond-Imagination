package net.darkygaia.beyondimagination.registry;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import com.simibubi.create.foundation.data.CreateRegistrate;

public class ModBlocks {

    // Helper for base ores (drop Raw Items)
    @SafeVarargs
    private static BlockEntry<Block> baseOre(String name, boolean deepslate, ItemEntry<Item> drop, TagKey<Block>... tags) {
        var builder = ModRegistry.REGISTRATE.block(name, Block::new)
                .initialProperties(() -> net.minecraft.world.level.block.Blocks.STONE)
                .properties(p -> p.strength(deepslate ? 4.5f : 3.0f, 3.0f)
                        .requiresCorrectToolForDrops()
                        .sound(deepslate ? SoundType.DEEPSLATE : SoundType.STONE))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .tag(BlockTags.NEEDS_IRON_TOOL);

        for (TagKey<Block> t : tags) {
            builder.tag(t);
        }

        return builder.blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cubeAll(ctx.getName(), prov.mcLoc(deepslate ? "block/deepslate" : "block/stone"))))
                .loot((lt, b) -> lt.add(b, lt.createOreDrop(b, drop.get())))
                .item()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc(deepslate ? "block/deepslate" : "block/stone")))
                .build()
                .register();
    }

    // Helper for geological/decorative ores (drop themselves)
    @SafeVarargs
    private static BlockEntry<Block> geoOre(String name, boolean deepslate, TagKey<Block>... tags) {
        var builder = ModRegistry.REGISTRATE.block(name, Block::new)
                .initialProperties(() -> net.minecraft.world.level.block.Blocks.STONE)
                .properties(p -> p.strength(deepslate ? 4.5f : 3.0f, 3.0f)
                        .requiresCorrectToolForDrops()
                        .sound(deepslate ? SoundType.DEEPSLATE : SoundType.STONE))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .tag(BlockTags.NEEDS_IRON_TOOL);

        for (TagKey<Block> t : tags) {
            builder.tag(t);
        }

        return builder.blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cubeAll(ctx.getName(), prov.mcLoc(deepslate ? "block/deepslate" : "block/stone"))))
                .simpleItem()
                .register();
    }

    // --- Base Ores ---
    public static final BlockEntry<Block> URANIUM_ORE = baseOre("uranium_ore", false, ModItems.RAW_IMPURE_URANIUM, ModTags.Blocks.ORES_URANIUM, ModTags.Blocks.ORES_IN_GROUND_STONE, ModTags.Blocks.RADIATION_SOURCES);
    public static final BlockEntry<Block> DEEPSLATE_URANIUM_ORE = baseOre("deepslate_uranium_ore", true, ModItems.RAW_IMPURE_URANIUM, ModTags.Blocks.ORES_URANIUM, ModTags.Blocks.ORES_IN_GROUND_DEEPSLATE, ModTags.Blocks.RADIATION_SOURCES);

    public static final BlockEntry<Block> TITANIUM_ORE = baseOre("titanium_ore", false, ModItems.RAW_TITANIUM, ModTags.Blocks.ORES_TITANIUM, ModTags.Blocks.ORES_IN_GROUND_STONE);
    public static final BlockEntry<Block> DEEPSLATE_TITANIUM_ORE = baseOre("deepslate_titanium_ore", true, ModItems.RAW_TITANIUM, ModTags.Blocks.ORES_TITANIUM, ModTags.Blocks.ORES_IN_GROUND_DEEPSLATE);

    public static final BlockEntry<Block> TUNGSTEN_ORE = baseOre("tungsten_ore", false, ModItems.RAW_TUNGSTEN, ModTags.Blocks.ORES_TUNGSTEN, ModTags.Blocks.ORES_IN_GROUND_STONE);
    public static final BlockEntry<Block> DEEPSLATE_TUNGSTEN_ORE = baseOre("deepslate_tungsten_ore", true, ModItems.RAW_TUNGSTEN, ModTags.Blocks.ORES_TUNGSTEN, ModTags.Blocks.ORES_IN_GROUND_DEEPSLATE);

    // --- Geological Ores ---
    public static final BlockEntry<Block> TORBERNITE = geoOre("torbernite", true, ModTags.Blocks.RADIATION_SOURCES);
    public static final BlockEntry<Block> RUTILE = geoOre("rutile", false);
    public static final BlockEntry<Block> WOLFRAMITE = geoOre("wolframite", false);
    public static final BlockEntry<Block> LEPIDOLITE = geoOre("lepidolite", false, net.minecraft.tags.BlockTags.create(net.minecraft.resources.ResourceLocation.parse("c:ores/lithium")));
    public static final BlockEntry<Block> CALDASITE = geoOre("caldasite", false);
    public static final BlockEntry<Block> ZEOLITE = geoOre("zeolite", false);

    // --- Sand blocks ---
    public static final BlockEntry<net.minecraft.world.level.block.ColoredFallingBlock> ZIRCON_SAND = ModRegistry.REGISTRATE.block("zircon_sand", p -> new net.minecraft.world.level.block.ColoredFallingBlock(new net.minecraft.util.ColorRGBA(0x8B7D6B), p))
            .initialProperties(() -> net.minecraft.world.level.block.Blocks.SAND)
            .properties(p -> p.strength(0.5f).sound(SoundType.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cubeAll(ctx.getName(), prov.mcLoc("block/sand"))))
            .simpleItem()
            .register();

    // --- Other Blocks ---
    public static final BlockEntry<Block> TITANIUM_BLOCK = ModRegistry.REGISTRATE.block("titanium_block", Block::new)
            .initialProperties(() -> net.minecraft.world.level.block.Blocks.IRON_BLOCK)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cubeAll(ctx.getName(), prov.mcLoc("block/iron_block"))))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> LEAD_GLASS = ModRegistry.REGISTRATE.block("lead_glass", Block::new)
            .initialProperties(() -> net.minecraft.world.level.block.Blocks.GLASS)
            .tag(ModTags.Blocks.RADIATION_SHIELDS)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cubeAll(ctx.getName(), prov.mcLoc("block/glass"))))
            .simpleItem()
            .register();

    public static final BlockEntry<com.simibubi.create.content.fluids.pipes.FluidPipeBlock> TITANIUM_PIPE = ModRegistry.REGISTRATE.block("titanium_pipe", com.simibubi.create.content.fluids.pipes.FluidPipeBlock::new)
            .initialProperties(com.simibubi.create.foundation.data.SharedProperties::copperMetal)
            .properties(p -> p.strength(3.0f, 6.0f).requiresCorrectToolForDrops())
            .blockstate(com.simibubi.create.foundation.data.BlockStateGen.pipe())
            .simpleItem()
            .register();

    public static void register() {}
}
