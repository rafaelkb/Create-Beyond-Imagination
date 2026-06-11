package net.darkygaia.beyondimagination.datagen;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> URANIUM_ORE_PLACED_KEY = registerKey("uranium_ore_placed");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = registerKey("titanium_ore_placed");
    public static final ResourceKey<PlacedFeature> TUNGSTEN_ORE_PLACED_KEY = registerKey("tungsten_ore_placed");
    public static final ResourceKey<PlacedFeature> TORBERNITE_PLACED_KEY = registerKey("torbernite_placed");
    public static final ResourceKey<PlacedFeature> RUTILE_PLACED_KEY = registerKey("rutile_placed");
    public static final ResourceKey<PlacedFeature> WOLFRAMITE_PLACED_KEY = registerKey("wolframite_placed");
    public static final ResourceKey<PlacedFeature> LEPIDOLITE_PLACED_KEY = registerKey("lepidolite_placed");
    public static final ResourceKey<PlacedFeature> CALDASITE_PLACED_KEY = registerKey("caldasite_placed");
    public static final ResourceKey<PlacedFeature> ZIRCON_SAND_PLACED_KEY = registerKey("zircon_sand_placed");
    public static final ResourceKey<PlacedFeature> ZEOLITE_PLACED_KEY = registerKey("zeolite_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, URANIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.URANIUM_ORE_KEY),
                commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-35))));

        register(context, TITANIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE_KEY),
                commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(30))));

        register(context, TUNGSTEN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TUNGSTEN_ORE_KEY),
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(30))));

        register(context, TORBERNITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TORBERNITE_KEY),
                commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-25))));

        register(context, RUTILE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RUTILE_KEY),
                commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-15))));

        register(context, WOLFRAMITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.WOLFRAMITE_KEY),
                commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-15))));

        register(context, LEPIDOLITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LEPIDOLITE_KEY),
                commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(35), VerticalAnchor.absolute(100))));

        register(context, CALDASITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CALDASITE_KEY),
                commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.absolute(120))));

        register(context, ZIRCON_SAND_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZIRCON_SAND_KEY),
                commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(70))));

        register(context, ZEOLITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZEOLITE_KEY),
                commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(40))));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier count, PlacementModifier heightRange) {
        return List.of(count, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
