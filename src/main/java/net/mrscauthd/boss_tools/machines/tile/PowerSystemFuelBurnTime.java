package net.mrscauthd.boss_tools.machines.tile;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class PowerSystemFuelBurnTime extends PowerSystemFuel {

	public static final int FUEL_PER_TICK = 1;

	public PowerSystemFuelBurnTime(AbstractMachineTileEntity tileEntity, Lazy<IItemHandlerModifiable> itemHandler, int slot) {
		super(tileEntity, itemHandler, slot);
	}

	@Override
	public int getBasePowerPerTick() {
		return FUEL_PER_TICK;
	}

	@Override
	public int getBasePowerForOperation() {
		return 0;
	}

}
