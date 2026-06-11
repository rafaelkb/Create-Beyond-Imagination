package net.darkygaia.beyondimagination.radiation;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.registry.ModAttachments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = BeyondImagination.MODID)
public class RadiationManager {

    public static float getRadiation(LivingEntity entity) {
        return entity.getData(ModAttachments.RADIATION_LEVEL);
    }

    public static void setRadiation(LivingEntity entity, float amount) {
        if (amount < 0) amount = 0;
        entity.setData(ModAttachments.RADIATION_LEVEL, amount);
        
        // Sync to client if it's a server player
        if (entity instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(serverPlayer, new net.darkygaia.beyondimagination.network.RadiationSyncPacket(amount));
        }
    }

    public static void addRadiation(LivingEntity entity, float amount) {
        setRadiation(entity, getRadiation(entity) + amount);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        
        // Creative players are immune
        if (player.isCreative() || player.isSpectator()) {
            setRadiation(player, 0f);
            return;
        }

        float currentRad = getRadiation(player);

        // Natural decay: -5 mRad/min. We tick every tick, so 5 / 1200 ticks = 0.00416 mRad/tick.
        // But to optimize, we can decay 0.083 mRad every 20 ticks.
        if (player.tickCount % 20 == 0) {
            if (currentRad > 0) {
                setRadiation(player, Math.max(0, currentRad - (5f / 60f)));
                currentRad = getRadiation(player);
            }
            
            // Apply effects
            applyRadiationEffects(player, currentRad);
        }
    }

    private static void applyRadiationEffects(Player player, float rad) {
        if (rad >= 200 && rad < 500) {
            // Nausea occasionally
            if (player.getRandom().nextFloat() < 0.05f) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false, true));
            }
        } else if (rad >= 500 && rad < 1000) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false, true));
            player.hurt(player.damageSources().magic(), 0.5f); // 0.5 HP per second
            
            // Grant glowing_player advancement if not already granted
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                net.minecraft.advancements.AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, "glowing_player"));
                if (advancement != null) {
                    net.minecraft.advancements.AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                    if (!progress.isDone()) {
                        for (String criterion : progress.getRemainingCriteria()) {
                            serverPlayer.getAdvancements().award(advancement, criterion);
                        }
                    }
                }
            }
        } else if (rad >= 1000 && rad < 2000) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 1, false, false, true));
            if (player.getRandom().nextFloat() < 0.2f) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 1, false, false, true));
            }
            player.hurt(player.damageSources().magic(), 2.0f); // 2 HP per second
        } else if (rad >= 2000) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false, true));
            player.hurt(player.damageSources().magic(), 4.0f); // 4 HP per second
        }
    }

    @SubscribeEvent
    public static void onDrinkMilk(LivingEntityUseItemEvent.Finish event) {
        if (!event.getEntity().level().isClientSide()) {
            if (event.getItem().getItem() == Items.MILK_BUCKET) {
                float current = getRadiation(event.getEntity());
                if (current > 0) {
                    setRadiation(event.getEntity(), Math.max(0, current - 50f));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            setRadiation(event.getEntity(), getRadiation(event.getEntity()));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            setRadiation(event.getEntity(), getRadiation(event.getEntity()));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            setRadiation(event.getEntity(), getRadiation(event.getEntity()));
        }
    }
}
