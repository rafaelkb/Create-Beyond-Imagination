package net.darkygaia.beyondimagination.mixin;

import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.fluid.SmartFluidTank;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidTankBlockEntity.class)
public abstract class MixinFluidTankBlockEntity extends com.simibubi.create.foundation.blockEntity.SmartBlockEntity {

    public MixinFluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow
    protected net.neoforged.neoforge.fluids.capability.templates.FluidTank tankInventory;

    @Shadow
    protected abstract void updateConnectivity();

    @org.spongepowered.asm.mixin.injection.Inject(method = "handlerForCapability", at = @org.spongepowered.asm.mixin.injection.At("RETURN"), cancellable = true, remap = false)
    private void onHandlerForCapability(org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<net.neoforged.neoforge.fluids.capability.IFluidHandler> cir) {
        net.neoforged.neoforge.fluids.capability.IFluidHandler original = cir.getReturnValue();
        if (original != null && !(original instanceof net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler)) {
            cir.setReturnValue(new net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler(original, (FluidTankBlockEntity) (Object) this));
        }
    }

    @org.spongepowered.asm.mixin.injection.Inject(method = "tick", at = @org.spongepowered.asm.mixin.injection.At("TAIL"))
    private void onTickCheckPressure(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (this.level == null || this.level.isClientSide()) return;
        if (this.level.getGameTime() % 10 != 0) return;
        
        FluidTankBlockEntity controller = ((FluidTankBlockEntity) (Object) this).getControllerBE();
        if (controller == null || controller != (Object) this) return;
        
        int currentPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getCurrentPsi(controller);
        int maxPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getMaxPsi(controller);
        
        if (maxPsi > 0 && currentPsi > maxPsi) {
            float extraPsi = currentPsi - maxPsi;
            float percentageOver = extraPsi / (float) maxPsi;
            float chance = percentageOver * 2.5f;
            
            if (this.level.random.nextFloat() < chance) {
                this.level.explode(null, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, 3.0f, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
                this.level.destroyBlock(this.worldPosition, true);
            }
        }
    }

    @org.spongepowered.asm.mixin.injection.Inject(method = "addToGoggleTooltip", at = @org.spongepowered.asm.mixin.injection.At("TAIL"), cancellable = false, remap = false)
    private void onAddToGoggleTooltip(java.util.List<net.minecraft.network.chat.Component> tooltip, boolean isPlayerSneaking, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> cir) {
        FluidTankBlockEntity controller = ((FluidTankBlockEntity) (Object) this).getControllerBE();
        if (controller == null) return;
        
        int currentPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getCurrentPsi(controller);
        int maxPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getMaxPsi(controller);
        
        if (maxPsi > 0) {
            tooltip.add(net.minecraft.network.chat.Component.literal("    §6Pressure: §e" + currentPsi + " / " + maxPsi + " PSI"));
        }
    }
}
