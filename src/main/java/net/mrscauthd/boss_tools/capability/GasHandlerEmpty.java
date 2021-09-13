package net.mrscauthd.boss_tools.capability;

import mekanism.api.Action;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;

public class GasHandlerEmpty implements IGasHandler {

	public GasHandlerEmpty() {

	}

	@Override
	public GasStack extractChemical(int var1, long var2, Action var4) {
		return GasStack.EMPTY;
	}

	@Override
	public GasStack getChemicalInTank(int var1) {
		return GasStack.EMPTY;
	}

	@Override
	public long getTankCapacity(int var1) {
		return 0;
	}

	@Override
	public int getTanks() {
		return 0;
	}

	@Override
	public GasStack insertChemical(int var1, GasStack var2, Action var3) {
		return GasStack.EMPTY;
	}

	@Override
	public boolean isValid(int var1, GasStack var2) {
		return false;
	}

	@Override
	public void setChemicalInTank(int var1, GasStack var2) {

	}

}
