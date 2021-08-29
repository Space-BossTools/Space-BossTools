package net.mrscauthd.boss_tools.capability;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageDelegateHolder extends IEnergyStorageHolder {

	int getEnergyCapacity(IEnergyStorage energyStorage);

	int getEnergyMaxExtract(IEnergyStorage energyStorage);

	int getEnergyMaxReceive(IEnergyStorage energyStorage);

}
