package net.darkygaia.beyondimagination.content.fluids.tank;

import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class PressurizedFluidHandler implements IFluidHandler {

    private final IFluidHandler original;
    private final FluidTankBlockEntity be;

    public PressurizedFluidHandler(IFluidHandler original, FluidTankBlockEntity be) {
        this.original = original;
        this.be = be;
    }

    public static int getConversionValue(FluidTankBlockEntity be) {
        FluidTankBlockEntity controller = be.getControllerBE();
        if (controller != null) be = controller;
        String className = be.getClass().getSimpleName().toLowerCase();
        if (className.contains("steel")) {
            return 65;
        }
        // Normal Create Tank (Copper)
        return 15;
    }

    public static int getMaxPsi(FluidTankBlockEntity be) {
        FluidTankBlockEntity controller = be.getControllerBE();
        if (controller != null) be = controller;
        FluidTank tank = be.getTankInventory();
        if (tank == null) return 0;
        return (tank.getCapacity() / 100) * getConversionValue(be);
    }

    public static int getCurrentPsi(FluidTankBlockEntity be) {
        FluidTankBlockEntity controller = be.getControllerBE();
        if (controller != null) be = controller;
        FluidTank tank = be.getTankInventory();
        if (tank == null) return 0;
        int overfill = tank.getFluidAmount() - tank.getCapacity();
        return Math.max(0, overfill);
    }

    @Override
    public int getTanks() {
        return original.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return original.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return original.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return original.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) return 0;

        FluidTank tank = be.getTankInventory();
        if (tank == null) return original.fill(resource, action);

        int filled = original.fill(resource, action);

        if (filled < resource.getAmount()) {
            FluidStack current = tank.getFluid();

            if (!current.isEmpty() && !FluidStack.isSameFluidSameComponents(current, resource)) {
                return filled;
            }

            int toPressurize = resource.getAmount() - filled;

            if (toPressurize > 0) {
                if (action.execute()) {
                    if (current.isEmpty()) {
                        tank.setFluid(resource.copyWithAmount(filled + toPressurize));
                    } else {
                        current.setAmount(current.getAmount() + toPressurize);
                        tank.setFluid(current);
                    }
                    be.setChanged();
                }
                filled += toPressurize;
            }
        }

        return filled;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return original.drain(resource, action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return original.drain(maxDrain, action);
    }
}
