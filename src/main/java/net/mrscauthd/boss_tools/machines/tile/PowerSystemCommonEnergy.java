package net.mrscauthd.boss_tools.machines.tile;

public abstract class PowerSystemCommonEnergy extends PowerSystemEnergy {

	public PowerSystemCommonEnergy(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

}
