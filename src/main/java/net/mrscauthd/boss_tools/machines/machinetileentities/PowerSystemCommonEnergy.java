package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraftforge.energy.IEnergyStorage;

public abstract class PowerSystemCommonEnergy extends PowerSystemEnergy {

	public PowerSystemCommonEnergy(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public int getEnergyCapacity(IEnergyStorage energyStorage) {
		return 9000;
	}

	@Override
	public int getEnergyMaxExtract(IEnergyStorage energyStorage) {
		return 200;
	}

	@Override
	public int getEnergyMaxReceive(IEnergyStorage energyStorage) {
		return 200;
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

}
