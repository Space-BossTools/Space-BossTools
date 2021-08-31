package net.mrscauthd.boss_tools.machines.tile;

import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;

public abstract class PowerSystemEnergy extends PowerSystem {
	public PowerSystemEnergy(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public int getStored() {
		return this.getEnergyStorage().getEnergyStored();
	}

	@Override
	public int getCapacity() {
		return this.getEnergyStorage().getMaxEnergyStored();
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return this.getEnergyStorage().receiveEnergy(amount, simulate);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return this.getEnergyStorage().extractEnergy(amount, simulate);
	}

	public final EnergyStorageBasic getEnergyStorage() {
		return this.getTileEntity().getEnergyStorage();
	}

}
