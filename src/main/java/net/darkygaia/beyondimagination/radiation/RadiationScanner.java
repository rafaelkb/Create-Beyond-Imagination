package net.darkygaia.beyondimagination.radiation;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.registry.ModBlocks;
import net.darkygaia.beyondimagination.registry.ModItems;
import net.darkygaia.beyondimagination.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = BeyondImagination.MODID)
public class RadiationScanner {

    private static final List<BlockPos> SPHERE_OFFSETS = new ArrayList<>();
    private static final int RADIUS = 10;
    private static final int TICKS_PER_CYCLE = 5;

    static {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    if (x * x + y * y + z * z <= RADIUS * RADIUS) {
                        SPHERE_OFFSETS.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        if (player.isCreative() || player.isSpectator()) return;

        int cycleIndex = player.tickCount % TICKS_PER_CYCLE;
        int totalOffsets = SPHERE_OFFSETS.size();
        int offsetsPerTick = (int) Math.ceil((double) totalOffsets / TICKS_PER_CYCLE);

        int startIndex = cycleIndex * offsetsPerTick;
        int endIndex = Math.min(startIndex + offsetsPerTick, totalOffsets);

        BlockPos playerPos = player.blockPosition();
        Vec3 playerEyePos = player.getEyePosition();

        float totalRadiationToAdd = 0f;

        // Scan Blocks
        for (int i = startIndex; i < endIndex; i++) {
            BlockPos offset = SPHERE_OFFSETS.get(i);
            BlockPos targetPos = playerPos.offset(offset);
            
            // Only check loaded chunks to prevent lag/loading chunks
            if (!level.isLoaded(targetPos)) continue;

            BlockState state = level.getBlockState(targetPos);
            if (state.is(ModTags.Blocks.RADIATION_SOURCES)) {
                float sourceStrength = getBlockRadiationStrength(state);
                if (sourceStrength > 0) {
                    float received = calculateReceivedRadiation(level, targetPos, playerEyePos, sourceStrength);
                    totalRadiationToAdd += received;
                }
            }
        }

        // Scan Item Entities (Only once per cycle, e.g., on tick 0)
        if (cycleIndex == 0) {
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(RADIUS));
            for (ItemEntity item : items) {
                if (item.getItem().is(ModTags.Items.RADIATION_SOURCES)) {
                    float sourceStrength = getItemRadiationStrength(item);
                    if (sourceStrength > 0) {
                        float received = calculateReceivedRadiation(level, item.blockPosition(), playerEyePos, sourceStrength);
                        totalRadiationToAdd += received;
                    }
                }
            }
        }

        // Apply hazmat suit reduction (Sums up percentages from IHazmatArmor)
        float totalProtection = 0.0f;
        for (net.minecraft.world.item.ItemStack stack : player.getInventory().armor) {
            if (stack.getItem() instanceof IHazmatArmor hazmat) {
                totalProtection += hazmat.getRadiationProtection();
            }
        }
        float protectionMultiplier = Math.max(0f, 1.0f - totalProtection);

        if (totalRadiationToAdd > 0) {
            RadiationManager.addRadiation(player, totalRadiationToAdd * protectionMultiplier);
        }
    }

    private static float getBlockRadiationStrength(BlockState state) {
        if (state.is(ModBlocks.URANIUM_ORE.get()) || state.is(ModBlocks.DEEPSLATE_URANIUM_ORE.get())) {
            return 2.0f; // 2 mRad/s
        }
        if (state.is(ModBlocks.TORBERNITE.get())) {
            return 1.0f; // 1 mRad/s
        }
        return 0f;
    }

    private static float getItemRadiationStrength(ItemEntity item) {
        if (item.getItem().is(ModItems.RAW_IMPURE_URANIUM.get())) {
            // Emite 2 mRad/s. Multiplicamos pelo tamanho da stack para realismo? 
            // Sim, um pack de 64 deve ser mais perigoso que 1.
            return 2.0f * item.getItem().getCount();
        }
        if (item.getItem().is(ModItems.YELLOW_CAKE.get())) {
            return 5.0f * item.getItem().getCount();
        }
        if (item.getItem().is(ModItems.URANIUM_FUEL_ROD.get())) {
            return 12.0f * item.getItem().getCount();
        }
        if (item.getItem().is(ModItems.THORIUM_FUEL_ROD.get())) {
            return 10.0f * item.getItem().getCount();
        }
        if (item.getItem().is(ModItems.RADIOACTIVE_WASTE.get())) {
            return 10.0f * item.getItem().getCount();
        }
        if (item.getItem().is(ModItems.UNSTABLE_RADIOACTIVE_WASTE.get())) {
            return 20.0f * item.getItem().getCount();
        }
        return 0f;
    }

    private static float calculateReceivedRadiation(Level level, BlockPos sourcePos, Vec3 playerPos, float sourceStrength) {
        Vec3 sourceCenter = Vec3.atCenterOf(sourcePos);
        double distance = sourceCenter.distanceTo(playerPos);

        // A emissão começa em uma esfera 3x3x3 ao redor da fonte (distância efetiva ~1.5)
        double effectiveDistance = Math.max(0, distance - 1.5);
        
        // Decai 40% a cada bloco
        float radiation = sourceStrength * (float) Math.pow(0.6, effectiveDistance);

        if (radiation <= 0.01f) return 0f;

        int steps = (int) Math.ceil(distance * 2); // 2 checks per block
        if (steps == 0) return radiation;
        
        Vec3 dir = playerPos.subtract(sourceCenter).normalize().scale(0.5);
        Vec3 currentPos = sourceCenter;
        Set<BlockPos> checkedBlocks = new HashSet<>();

        for (int i = 0; i < steps; i++) {
            currentPos = currentPos.add(dir);
            BlockPos pos = BlockPos.containing(currentPos);
            
            if (checkedBlocks.add(pos)) {
                if (pos.equals(sourcePos)) continue;
                
                BlockState state = level.getBlockState(pos);
                if (!state.isAir()) {
                    if (state.is(ModTags.Blocks.RADIATION_SHIELDS)) {
                        radiation -= 50.0f;
                    } else if (state.getFluidState().is(net.minecraft.tags.FluidTags.WATER) || state.getFluidState().is(net.minecraft.tags.FluidTags.LAVA)) {
                        radiation -= 150.0f;
                    } else if (state.canOcclude()) { // Apenas blocos sólidos dão resistência
                        radiation -= 1.0f;
                    }
                }
                
                if (radiation <= 0) return 0f;
            }
        }

        return radiation;
    }
}
