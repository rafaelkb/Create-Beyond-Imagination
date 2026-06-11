package net.darkygaia.beyondimagination.datagen;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> URANIUM_ORE_KEY = registerKey("uranium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE_KEY = registerKey("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNGSTEN_ORE_KEY = registerKey("tungsten_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TORBERNITE_KEY = registerKey("torbernite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUTILE_KEY = registerKey("rutile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOLFRAMITE_KEY = registerKey("wolframite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEPIDOLITE_KEY = registerKey("lepidolite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALDASITE_KEY = registerKey("caldasite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_SAND_KEY = registerKey("zircon_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZEOLITE_KEY = registerKey("zeolite");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest sandReplaceables = new TagMatchTest(BlockTags.SAND);

        // Uranium (only deepslate) - Veins 2-6 (avg 4)
        List<OreConfiguration.TargetBlockState> uraniumTargets = List.of(
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_URANIUM_ORE.get().defaultBlockState())
        );

        // Titanium (Stone and Deepslate) - Veins 4-10 (avg 7)
        List<OreConfiguration.TargetBlockState> titaniumTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TITANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState())
        );

        // Tungsten (Stone and Deepslate) - Veins 3-8 (avg 6)
        List<OreConfiguration.TargetBlockState> tungstenTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TUNGSTEN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get().defaultBlockState())
        );

        register(context, URANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(uraniumTargets, 4));
        register(context, TITANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(titaniumTargets, 7));
        register(context, TUNGSTEN_ORE_KEY, Feature.ORE, new OreConfiguration(tungstenTargets, 6));

        // Torbernite - Veins 10-24 (avg 17)
        register(context, TORBERNITE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.TORBERNITE.get().defaultBlockState(), 17));

        // Rutile - Veins 12-28 (avg 20)
        register(context, RUTILE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.RUTILE.get().defaultBlockState(), 20));

        // Wolframite - Veins 12-28 (avg 20)
        register(context, WOLFRAMITE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.WOLFRAMITE.get().defaultBlockState(), 20));

        // Lepidolite - Veins 15-35 (avg 25)
        register(context, LEPIDOLITE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.LEPIDOLITE.get().defaultBlockState(), 25));

        // Caldasite - Veins 10-25 (avg 17)
        register(context, CALDASITE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.CALDASITE.get().defaultBlockState(), 17));

        // Zircon Sand - Veins 6-15 (avg 10) (can replace stone or sand)
        List<OreConfiguration.TargetBlockState> zirconTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ZIRCON_SAND.get().defaultBlockState()),
                OreConfiguration.target(sandReplaceables, ModBlocks.ZIRCON_SAND.get().defaultBlockState())
        );
        register(context, ZIRCON_SAND_KEY, Feature.ORE, new OreConfiguration(zirconTargets, 10));

        // Zeolite - Veins 8-22 (avg 15)
        register(context, ZEOLITE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables, ModBlocks.ZEOLITE.get().defaultBlockState(), 15));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, name));
    }

    private static <FC extends net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                                                                                    ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
