package net.darkygaia.beyondimagination.datagen;

import net.darkygaia.beyondimagination.registry.ModBlocks;
import net.darkygaia.beyondimagination.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Activated Carbon from Charcoal
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CHARCOAL), RecipeCategory.MISC, ModItems.ACTIVATED_CARBON.get(), 0.1f, 200)
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(output, "activated_carbon_from_smelting");

        // Silicon Ingot from Sand (Blast Furnace)
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Items.SAND), RecipeCategory.MISC, ModItems.SILICON_INGOT.get(), 1.0f, 180)
                .unlockedBy("has_sand", has(Items.SAND))
                .save(output, "silicon_ingot_from_blasting");

        // Zirconium Ingot from Zircon Sand (Blast Furnace)
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ZIRCON_SAND.get()), RecipeCategory.MISC, ModItems.ZIRCONIUM_INGOT.get(), 1.0f, 200)
                .unlockedBy("has_zircon_sand", has(ModBlocks.ZIRCON_SAND.get()))
                .save(output, "zirconium_ingot_from_blasting");

        // Titanium Ingot from Raw Titanium (Blast Furnace)
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TITANIUM.get()), RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0f, 160)
                .unlockedBy("has_raw_titanium", has(ModItems.RAW_TITANIUM.get()))
                .save(output, "titanium_ingot_from_blasting");

        // Tungsten Ingot from Raw Tungsten (Blast Furnace)
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TUNGSTEN.get()), RecipeCategory.MISC, ModItems.TUNGSTEN_INGOT.get(), 1.0f, 200)
                .unlockedBy("has_raw_tungsten", has(ModItems.RAW_TUNGSTEN.get()))
                .save(output, "tungsten_ingot_from_blasting");
    }
}
