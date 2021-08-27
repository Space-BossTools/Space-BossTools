package net.mrscauthd.boss_tools.machines.machinetileentities;

public class PowerSystemNone extends PowerSystem {
	public PowerSystemNone(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

	@Override
	public int getBasePowerForOperation() {
		return 0;
	}

	@Override
	public int getStored() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return 0;
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return 0;
	}

}
