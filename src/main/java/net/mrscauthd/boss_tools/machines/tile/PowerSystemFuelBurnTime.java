package net.mrscauthd.boss_tools.machines.tile;

public abstract class PowerSystemFuelBurnTime extends PowerSystemFuel {

	public static final int FUEL_PER_TICK = 1;

	public PowerSystemFuelBurnTime(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	@Override
	public int getBasePowerPerTick() {
		return FUEL_PER_TICK;
	}

	@Override
	public int getBasePowerForOperation() {
		return 0;
	}

	@Override
	public String getName() {
		return "FuelBurnTime";
	}
}
