package net.mrscauthd.boss_tools.machines.machinetileentities;

import java.util.Optional;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageBasic extends EnergyStorage implements IEnergyStorageExtends {
	private final IEnergyStorageHolder holder;

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
		this.holder = holder;
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
		this.holder = holder;
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
		this.holder = holder;
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity) {
		super(capacity);
		this.holder = holder;
	}

	public int receiveEnergyInternal(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(this.capacity - this.energy, maxReceive);
		if (!simulate && energyReceived > 0) {
			this.energy += energyReceived;
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, +energyReceived));
		}

		return energyReceived;
	}

	public int extractEnergyInternal(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(this.energy, maxExtract);
		if (!simulate && energyExtracted > 0) {
			this.energy -= energyExtracted;
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, -energyExtracted));
		}

		return energyExtracted;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = super.receiveEnergy(maxReceive, simulate);

		if (energyReceived > 0) {
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, +energyReceived));
		}

		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = super.extractEnergy(maxExtract, simulate);

		if (energyExtracted > 0) {
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, -energyExtracted));
		}

		return energyExtracted;
	}

	public IEnergyStorageHolder getHolder() {
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
