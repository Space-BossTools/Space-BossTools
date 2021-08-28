package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageHolder {

	void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta);

}
