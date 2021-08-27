package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageExtends extends IEnergyStorage {
	void read(CompoundNBT compound);

	void write(CompoundNBT compound);

	public default CompoundNBT write() {
		CompoundNBT compound = new CompoundNBT();
		this.write(compound);
		return compound;
	}
}
