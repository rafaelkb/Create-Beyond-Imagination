package net.darkygaia.beyondimagination.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final ItemEntry<Item> RAW_IMPURE_URANIUM = ModRegistry.REGISTRATE.item("raw_impure_uranium", Item::new)
            .tag(net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("c:raw_materials/uranium")))
            .tag(ModTags.Items.RADIATION_SOURCES)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/raw_iron")))
            .register();

    public static final ItemEntry<Item> RAW_TITANIUM = ModRegistry.REGISTRATE.item("raw_titanium", Item::new)
            .tag(net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("c:raw_materials/titanium")))
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/raw_iron")))
            .register();

    public static final ItemEntry<Item> RAW_TUNGSTEN = ModRegistry.REGISTRATE.item("raw_tungsten", Item::new)
            .tag(net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("c:raw_materials/tungsten")))
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/raw_iron")))
            .register();
    private static ItemEntry<Item> simpleItem(String name) {
        return ModRegistry.REGISTRATE.item(name, Item::new)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/iron_ingot")))
                .register();
    }

    private static ItemEntry<Item> taggedItem(String name, net.minecraft.tags.TagKey<Item>... tags) {
        var builder = ModRegistry.REGISTRATE.item(name, Item::new)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/iron_ingot")));
        for (net.minecraft.tags.TagKey<Item> tag : tags) {
            builder.tag(tag);
        }
        return builder.register();
    }

    // --- Dusts ---
    public static final ItemEntry<Item> IMPURE_URANIUM_DUST = taggedItem("impure_uranium_dust", ModTags.Items.DUSTS_IMPURE_URANIUM);
    public static final ItemEntry<Item> ZEOLITE_DUST = taggedItem("zeolite_dust", ModTags.Items.DUSTS_ZEOLITE);
    public static final ItemEntry<Item> ACTIVATED_CARBON = taggedItem("activated_carbon", ModTags.Items.DUSTS_ACTIVATED_CARBON);
    public static final ItemEntry<Item> LEAD_DUST = taggedItem("lead_dust", ModTags.Items.DUSTS_LEAD);

    // --- Nuclear Materials ---
    public static final ItemEntry<Item> YELLOW_CAKE = taggedItem("yellow_cake", ModTags.Items.RADIATION_SOURCES);
    public static final ItemEntry<Item> CONVENTIONAL_URANIUM = simpleItem("conventional_uranium");
    public static final ItemEntry<Item> ENRICHED_URANIUM = simpleItem("enriched_uranium");
    public static final ItemEntry<Item> THORIUM = simpleItem("thorium");
    public static final ItemEntry<Item> RADIOACTIVE_WASTE = taggedItem("radioactive_waste", ModTags.Items.RADIATION_SOURCES);
    public static final ItemEntry<Item> UNSTABLE_RADIOACTIVE_WASTE = taggedItem("unstable_radioactive_waste", ModTags.Items.RADIATION_SOURCES);

    // --- Ingots & Alloys ---
    public static final ItemEntry<Item> SILICON_INGOT = taggedItem("silicon_ingot", ModTags.Items.INGOTS_SILICON);
    public static final ItemEntry<Item> ZIRCONIUM_INGOT = taggedItem("zirconium_ingot", ModTags.Items.INGOTS_ZIRCONIUM);
    public static final ItemEntry<Item> ZIRCALOY_INGOT = taggedItem("zircaloy_ingot", ModTags.Items.INGOTS_ZIRCALOY);
    public static final ItemEntry<Item> TITANIUM_INGOT = taggedItem("titanium_ingot", ModTags.Items.INGOTS_TITANIUM);
    public static final ItemEntry<Item> TUNGSTEN_INGOT = taggedItem("tungsten_ingot", ModTags.Items.INGOTS_TUNGSTEN);

    // --- Parts & Components ---
    public static final ItemEntry<Item> SILICON_WAFER = simpleItem("silicon_wafer");
    public static final ItemEntry<Item> DOPED_SILICON_WAFER = simpleItem("doped_silicon_wafer");
    public static final ItemEntry<Item> ZIRCALOY_TUBE = simpleItem("zircaloy_tube");
    public static final ItemEntry<Item> TITANIUM_PLATE = simpleItem("titanium_plate");
    public static final ItemEntry<Item> TUNGSTEN_TIP = simpleItem("tungsten_tip");

    // --- Pellets & Rods ---
    public static final ItemEntry<Item> URANIUM_PELLET = simpleItem("uranium_pellet");
    public static final ItemEntry<Item> THORIUM_PELLET = simpleItem("thorium_pellet");
    public static final ItemEntry<Item> EMPTY_FUEL_ROD = simpleItem("empty_fuel_rod");
    public static final ItemEntry<Item> URANIUM_FUEL_ROD = taggedItem("uranium_fuel_rod", ModTags.Items.RADIATION_SOURCES);
    public static final ItemEntry<Item> THORIUM_FUEL_ROD = taggedItem("thorium_fuel_rod", ModTags.Items.RADIATION_SOURCES);

    // --- Utilities ---
    public static final ItemEntry<Item> ANTI_RADIATION_FIBER = simpleItem("anti_radiation_fiber");
    public static final ItemEntry<Item> ZEOLITE_FILTER = ModRegistry.REGISTRATE.item("zeolite_filter", p -> new Item(p.durability(100)))
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated")).texture("layer0", prov.mcLoc("item/paper")))
            .register();
    public static void register() {}
}
