package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageHolder {

	void onEnergyReceived(IEnergyStorage energyStorage, int energyReceived);

	void onEnergyExtracted(IEnergyStorage energyStorage, int energyExtracted);

}
