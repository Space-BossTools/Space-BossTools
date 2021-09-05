package net.mrscauthd.boss_tools.capability;

import java.util.List;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class MultiFluidHandler implements IFluidHandler, INBTSerializable<CompoundNBT> {

	private FluidTank[] tanks;

	public MultiFluidHandler(List<Fluid> fluids, int capacity) {
		int size = fluids.size();
		this.tanks = new FluidTank[size];

		for (int i = 0; i < size; i++) {
			Fluid fluid = fluids.get(i);
			this.tanks[i] = new FluidTank(capacity, fs -> fs.getFluid().isEquivalentTo(fluid)) {
				@Override
				protected void onContentsChanged() {
					super.onContentsChanged();
					MultiFluidHandler.this.onContentsChanged();
				}
			};
		}
	}

	protected void onContentsChanged() {

	}

	@Override
	public FluidStack drain(FluidStack stack, FluidAction action) {
		int amount = stack.getAmount();
		int drained = 0;

		for (int i = 0; i < this.getTanks(); i++) {
			FluidTank tank = this.getTank(i);

			if (amount <= 0) {
				break;
			}

			if (tank.isFluidValid(stack)) {
				FluidStack tankDrained = tank.drain(new FluidStack(stack.getFluid(), amount), action);

				if (!tankDrained.isEmpty()) {
					amount -= tankDrained.getAmount();
					drained += tankDrained.getAmount();
				}
			}
		}

		return new FluidStack(stack.getFluid(), drained);
	}

	@Override
	public FluidStack drain(int amount, FluidAction action) {
		for (int i = 0; i < this.getTanks(); i++) {
			FluidTank tank = this.getTank(i);

			if (!tank.isEmpty()) {
				return this.drain(new FluidStack(tank.getFluid(), amount), action);
			}
		}

		return FluidStack.EMPTY;
	}

	@Override
	public int fill(FluidStack stack, FluidAction action) {
		int amount = stack.getAmount();
		int filled = 0;

		for (int i = 0; i < this.getTanks(); i++) {

			if (amount <= 0) {
				break;
			}

			FluidTank tank = this.getTank(i);

			if (tank.isFluidValid(stack)) {
				int tankFilled = tank.fill(new FluidStack(stack.getFluid(), amount), action);
				amount -= tankFilled;
				filled = tankFilled;
			}
		}

		return filled;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return this.getTank(tank).getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		return this.getTank(tank).getCapacity();
	}

	@Override
	public int getTanks() {
		return this.tanks.length;
	}

	public FluidTank getTank(int tank) {
		return this.tanks[tank];
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return this.getTank(tank).isFluidValid(stack);
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		ListNBT tanksNBT = compound.getList("tanks", 10);

		for (int i = 0; i < this.getTanks(); i++) {
			CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(this.getTank(i), null, tanksNBT.getCompound(i));
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		ListNBT tanksNBT = new ListNBT();
		compound.put("tanks", tanksNBT);

		for (int i = 0; i < this.getTanks(); i++) {
			tanksNBT.add(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(this.getTank(i), null));
		}

		return compound;
	}

}
