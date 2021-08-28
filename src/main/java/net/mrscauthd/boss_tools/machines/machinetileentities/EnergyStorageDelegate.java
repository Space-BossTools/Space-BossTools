package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraft.nbt.CompoundNBT;

public class EnergyStorageDelegate extends EnergyStorageBasic {

	private final IEnergyStorageDelegateHolder holder;
	private int energy;

	public EnergyStorageDelegate(IEnergyStorageDelegateHolder holder) {
		super(holder, 0);
		this.holder = holder;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!this.canReceive()) {
			return 0;
		} else {
			int energyReceived = Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), Math.min(this.getMaxReceive(), maxReceive));
			if (!simulate && energyReceived > 0) {
				this.energy += energyReceived;
				this.getHolder().onEnergyChanged(this, +maxReceive);
			}

			return energyReceived;
		}
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!this.canExtract()) {
			return 0;
		} else {
			int energyExtracted = Math.min(this.getEnergyStored(), Math.min(this.getMaxExtract(), maxExtract));
			if (!simulate && energyExtracted > 0) {
				this.energy -= energyExtracted;
				this.getHolder().onEnergyChanged(this, -energyExtracted);
			}

			return energyExtracted;
		}
	}

	@Override
	public int getEnergyStored() {
		return this.energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return this.getHolder().getEnergyCapacity(this);
	}

	public int getMaxExtract() {
		return this.getHolder().getEnergyMaxExtract(this);
	}

	@Override
	public boolean canExtract() {
		return this.getMaxExtract() > 0;
	}

	public int getMaxReceive() {
		return this.getHolder().getEnergyMaxReceive(this);
	}

	@Override
	public boolean canReceive() {
		return this.getMaxReceive() > 0;
	}

	public IEnergyStorageDelegateHolder getHolder() {
		return this.holder;
	}

	@Override
	public void read(CompoundNBT compound) {
		this.energy = compound.getInt("energy");
	}

	@Override
	public void write(CompoundNBT compound) {
		compound.putInt("energy", this.energy);
	}

}
