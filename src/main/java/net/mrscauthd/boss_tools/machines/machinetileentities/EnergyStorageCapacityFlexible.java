package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageCapacityFlexible extends EnergyStorage {

	public EnergyStorageCapacityFlexible(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public EnergyStorageCapacityFlexible(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public EnergyStorageCapacityFlexible(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

	public EnergyStorageCapacityFlexible(int capacity) {
		super(capacity);
	}
	
	public CompoundNBT write()
	{
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("capacity", this.capacity);
		compound.putInt("maxReceive", this.maxReceive);
		compound.putInt("maxExtract", this.maxExtract);
		compound.putInt("energy", this.energy);
		return compound;
	}
	
	public void read(CompoundNBT compound)
	{
		this.capacity = compound.getInt("capacity");
		this.maxReceive = compound.getInt("maxReceive");
		this.maxExtract = compound.getInt("maxExtract");
		this.energy = compound.getInt("energy");
	}
	
//	public void setEnergyStored(int energy) {
//		this.energy = Math.max(Math.min(energy, this.getMaxEnergyStored()), 0);
//	}

	public void setMaxEnergyStored(int capacity) {
		this.capacity = Math.max(0, capacity);
		this.energy = Math.min(this.energy, this.capacity);
	}

	public double getEnergyStoredRatio() {
		return (double) this.getEnergyStored() / this.getMaxEnergyStored();
	}

	public double getEnergyStoredPercentage() {
		return this.getEnergyStoredRatio() * 100.0D;
	}

}
