package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageHolder {

	int getEnergyCapacity(IEnergyStorage energyStorage);

	int getEnergyMaxExtract(IEnergyStorage energyStorage);

	int getEnergyMaxReceive(IEnergyStorage energyStorage);

	void onEnergyReceived(IEnergyStorage energyStorage, int energyReceived);

	void onEnergyExtracted(IEnergyStorage energyStorage, int energyExtracted);

}
