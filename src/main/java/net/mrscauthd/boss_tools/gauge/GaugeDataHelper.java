package net.mrscauthd.boss_tools.gauge;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelBurnTime;

public class GaugeDataHelper {

	public static final int NO_CAPACITY = -1;

	public static final ResourceLocation ENERGY_NAME = new ResourceLocation("boss_tools", "energy");
	public static final ResourceLocation OXYGEN_NAME = new ResourceLocation("boss_tools", "oxygen");
	public static final ResourceLocation BURNTIME_NAME = new ResourceLocation("boss_tools", "burntime");
	public static final ResourceLocation COOKTIME_NAME = new ResourceLocation("boss_tools", "cooktime");

	public static final int ENERGY_COLOR = 0xA0FF404B;
	public static final int OXYGEN_COLOR = 0xA000FFFF;
	public static final int BURNTIME_COLOR = 0xA0FF3F00;
	public static final int COOKTIME_COLOR = 0xA0FFFFFF;

	public static final String ENERGY_UNIT = "FE";
	public static final String OXYGEN_UNIT = "mB";
	public static final String FLUID_UNIT = "mB";

	public static String makeTranslationKey(ResourceLocation name) {
		return "gague." + name.getNamespace() + "." + name.getPath();
	}

	public static GaugeData getFluid(Fluid fluid, int amount) {
		return getFluid(new FluidStack(fluid, amount));
	}

	public static GaugeData getFluid(Fluid fluid, int amount, int capacity) {
		return getFluid(new FluidStack(fluid, amount), capacity);
	}

	public static GaugeData getFluid(FluidStack stack) {
		return getFluid(stack, NO_CAPACITY);
	}

	public static GaugeData getFluid(FluidStack stack, int capacity) {
		return new GaugeData(new GaugeValueFluidStack(stack, capacity), new GaugeFormat(capacity > NO_CAPACITY));
	}

	public static GaugeData getFluid(IFluidTank tank) {
		return getFluid(tank.getFluid(), tank.getCapacity());
	}

	public static GaugeData getEnergy(int amount) {
		return getEnergy(amount, NO_CAPACITY);
	}

	public static GaugeData getEnergy(int stored, int capacity) {
		return new GaugeData(new GaugeValueSimple(ENERGY_NAME, stored, capacity, null, ENERGY_UNIT).color(ENERGY_COLOR), new GaugeFormat(capacity > NO_CAPACITY));
	}

	public static GaugeData getEnergy(IEnergyStorage energyStorage) {
		return getEnergy(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
	}

	public static GaugeData getEnergy(AbstractMachineTileEntity machineTileEntity) {
		return getEnergy(machineTileEntity.getPrimaryEnergyStorage());
	}

	public static GaugeData getOxygen(int amount) {
		return getOxygen(amount, NO_CAPACITY);
	}

	public static GaugeData getOxygen(int amount, int capacity) {
		return new GaugeData(new GaugeValueSimple(OXYGEN_NAME, amount, capacity, null, OXYGEN_UNIT).color(OXYGEN_COLOR), new GaugeFormat(capacity > NO_CAPACITY));
	}

	public static GaugeData getOxygen(IOxygenStorage oxygenStorage) {
		return getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
	}

	public static GaugeData getBurnTime(int amount) {
		return getBurnTime(amount, NO_CAPACITY);
	}

	public static GaugeData getBurnTime(int amount, int capacity) {
		return new GaugeData(new GaugeValueSimple(BURNTIME_NAME, amount, capacity).color(BURNTIME_COLOR), new GaugeFormat(capacity > NO_CAPACITY));
	}

	public static GaugeData getBurnTime(PowerSystemFuelBurnTime fuelPowerSystem) {
		return getBurnTime(fuelPowerSystem.getStored(), fuelPowerSystem.getCapacity());
	}

	public static GaugeData getCookTime(int timer, int maxTimer) {
		return new GaugeData(new GaugeValueSimple(COOKTIME_NAME, maxTimer - timer, maxTimer).color(COOKTIME_COLOR).reverse(true), new GaugeFormat(true));
	}

}
