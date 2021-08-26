package net.mrscauthd.boss_tools.machines.machinetileentities;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class ItemStackToItemStackEnergyTileEntity extends ItemStackToItemStackTileEntity {

	public ItemStackToItemStackEnergyTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && capability == CapabilityEnergy.ENERGY)
			return LazyOptional.of(() -> this.getEnergyStorage()).cast();
		return super.getCapability(capability, facing);
	}

	@Override
	protected boolean canCook() {
		return this.consumeEnergy();
	}

	@Override
	protected void onCantCook() {

	}

	@Override
	protected boolean canActivated() {
		return this.getTimer() > 0;
	}

}
