package net.darkygaia.beyondimagination.mixin;

import com.drmangotea.tfmg.content.decoration.tanks.steel.SteelTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SteelTankBlockEntity.class)
public abstract class MixinSteelTankBlockEntity {

    @Inject(method = "addToGoggleTooltip", at = @At("HEAD"), cancellable = true, remap = false)
    private void onAddToGoggleTooltipHead(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        SteelTankBlockEntity be = (SteelTankBlockEntity) (Object) this;
        if (be.getControllerBE() == null) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "addToGoggleTooltip", at = @At("TAIL"), cancellable = false, remap = false)
    private void onAddToGoggleTooltipTail(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        FluidTankBlockEntity controller = ((FluidTankBlockEntity) (Object) this).getControllerBE();
        if (controller == null) return;
        
        int currentPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getCurrentPsi(controller);
        int maxPsi = net.darkygaia.beyondimagination.content.fluids.tank.PressurizedFluidHandler.getMaxPsi(controller);
        
        if (maxPsi > 0) {
            tooltip.add(Component.literal("    §6Pressure: §e" + currentPsi + " / " + maxPsi + " PSI"));
        }
    }
}
