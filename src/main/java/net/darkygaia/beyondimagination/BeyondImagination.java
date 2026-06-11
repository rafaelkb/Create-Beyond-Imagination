package net.darkygaia.beyondimagination;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BeyondImagination.MODID)
public class BeyondImagination {
    public static final String MODID = "beyondimagination";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BeyondImagination(IEventBus modEventBus, ModContainer modContainer) {
        net.darkygaia.beyondimagination.registry.ModBlocks.register();
        net.darkygaia.beyondimagination.registry.ModItems.register();
        net.darkygaia.beyondimagination.registry.ModBlockEntities.register();
        net.darkygaia.beyondimagination.registry.ModCreativeModeTabs.register(modEventBus);
        net.darkygaia.beyondimagination.registry.ModAttachments.register(modEventBus);
        net.darkygaia.beyondimagination.registry.ModDataComponents.register(modEventBus);
        
        // Register common configuration
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        net.darkygaia.beyondimagination.registry.ModSounds.SOUNDS.register(modEventBus);
        net.darkygaia.beyondimagination.registry.ModRegistry.REGISTRATE.registerEventListeners(modEventBus);
        net.darkygaia.beyondimagination.registry.ModRegistry.register();
    }
}
