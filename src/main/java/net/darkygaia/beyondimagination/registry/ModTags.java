package net.darkygaia.beyondimagination.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ORES_URANIUM = tag("c", "ores/uranium");
        public static final TagKey<Block> ORES_TITANIUM = tag("c", "ores/titanium");
        public static final TagKey<Block> ORES_TUNGSTEN = tag("c", "ores/tungsten");
        public static final TagKey<Block> ORES_IN_GROUND_STONE = tag("c", "ores_in_ground/stone");
        public static final TagKey<Block> ORES_IN_GROUND_DEEPSLATE = tag("c", "ores_in_ground/deepslate");
        
        public static final TagKey<Block> RADIATION_SOURCES = tag(net.darkygaia.beyondimagination.BeyondImagination.MODID, "radiation_sources");
        public static final TagKey<Block> RADIATION_SHIELDS = tag(net.darkygaia.beyondimagination.BeyondImagination.MODID, "radiation_shields");

        private static TagKey<Block> tag(String namespace, String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> RAW_MATERIALS_URANIUM = tag("c", "raw_materials/uranium");
        public static final TagKey<Item> RAW_MATERIALS_TITANIUM = tag("c", "raw_materials/titanium");
        public static final TagKey<Item> RAW_MATERIALS_TUNGSTEN = tag("c", "raw_materials/tungsten");
        
        public static final TagKey<Item> ORES_URANIUM = tag("c", "ores/uranium");
        public static final TagKey<Item> ORES_TITANIUM = tag("c", "ores/titanium");
        public static final TagKey<Item> ORES_TUNGSTEN = tag("c", "ores/tungsten");
        
        // Ingots
        public static final TagKey<Item> INGOTS_LEAD = tag("c", "ingots/lead");
        public static final TagKey<Item> INGOTS_TITANIUM = tag("c", "ingots/titanium");
        public static final TagKey<Item> INGOTS_TUNGSTEN = tag("c", "ingots/tungsten");
        public static final TagKey<Item> INGOTS_ZIRCONIUM = tag("c", "ingots/zirconium");
        public static final TagKey<Item> INGOTS_ZIRCALOY = tag("c", "ingots/zircaloy");
        public static final TagKey<Item> INGOTS_SILICON = tag("c", "ingots/silicon");

        // Dusts
        public static final TagKey<Item> DUSTS_IMPURE_URANIUM = tag("c", "dusts/impure_uranium");
        public static final TagKey<Item> DUSTS_ZEOLITE = tag("c", "dusts/zeolite");
        public static final TagKey<Item> DUSTS_ACTIVATED_CARBON = tag("c", "dusts/activated_carbon");
        public static final TagKey<Item> DUSTS_LEAD = tag("c", "dusts/lead");

        public static final TagKey<Item> RADIATION_SOURCES = tag(net.darkygaia.beyondimagination.BeyondImagination.MODID, "radiation_source_items");

        private static TagKey<Item> tag(String namespace, String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }
}
