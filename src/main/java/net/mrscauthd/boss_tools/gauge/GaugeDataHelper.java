package net.mrscauthd.boss_tools.gauge;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelBurnTime;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelOxygen;

public class GaugeDataHelper {

	public static final ResourceLocation ENERGY_NAME = new ResourceLocation("boss_tools", "energy");
	public static final ResourceLocation OXYGEN_NAME = new ResourceLocation("boss_tools", "oxygen");
	public static final ResourceLocation BURNTIME_NAME = new ResourceLocation("boss_tools", "burntime");
	public static final ResourceLocation COOKTIME_NAME = new ResourceLocation("boss_tools", "cooktime");
	public static final ResourceLocation OXYGENLOADING_NAME = new ResourceLocation("boss_tools", "oxygenloading");

	public static GaugeData getFluid(Fluid fluid, int amount, int capacity) {
		return getFluid(new FluidStack(fluid, amount), capacity);
	}

	public static GaugeData getFluid(FluidStack stack, int capacity) {
		return new GaugeData(new GaugeValueFluidStack(stack, capacity), new GaugeFormat(true, false));
	}

	public static GaugeData getFluid(IFluidTank tank) {
		return getFluid(tank.getFluid(), tank.getCapacity());
	}

	public static GaugeData getEnergy(int stored, int capacity) {
		return new GaugeData(new GaugeValueSimple(ENERGY_NAME, stored, capacity, "Energy", "FE").color(0xA0FF404B), new GaugeFormat(true, false));
	}

	public static GaugeData getEnergy(IEnergyStorage energyStorage) {
		return getEnergy(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
	}

	public static GaugeData getEnergy(AbstractMachineTileEntity machineTileEntity) {
		return getEnergy(machineTileEntity.getPrimaryEnergyStorage());
	}

	public static GaugeData getOxygen(int amount, int capacity) {
		return new GaugeData(new GaugeValueSimple(OXYGEN_NAME, amount, capacity, "Oxygen").color(0xA000FFFF), new GaugeFormat(true, false));
	}

	public static GaugeData getOxygen(IOxygenStorage oxygenStorage) {
		return getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
	}

	public static GaugeData getOxygen(PowerSystemFuelOxygen oxygenPowerSystem) {
		return getOxygen(oxygenPowerSystem.getStored(), oxygenPowerSystem.getCapacity());
	}

	public static GaugeData getBurnTime(int amount, int capacity) {
		return new GaugeData(new GaugeValueSimple(BURNTIME_NAME, amount, capacity, "Burn Time").color(0xA0FF3F00), new GaugeFormat(true, false));
	}

	public static GaugeData getBurnTime(PowerSystemFuelBurnTime fuelPowerSystem) {
		return getBurnTime(fuelPowerSystem.getStored(), fuelPowerSystem.getCapacity());
	}

	public static GaugeData getCookTime(int timer, int maxTimer) {
		return new GaugeData(new GaugeValueSimple(COOKTIME_NAME, maxTimer - timer, maxTimer, "Cook Time"), new GaugeFormat(true, true));
	}

	public static GaugeData getOxygenLoading(int timer, int maxTimer) {
		return new GaugeData(new GaugeValueSimple(COOKTIME_NAME, maxTimer - timer, maxTimer, "Cook Time"), new GaugeFormat(true, true));
	}

	public static GaugeData getOxygenLoading(IOxygenStorage oxygenStorage) {
		return new GaugeData(new GaugeValueSimple(OXYGENLOADING_NAME, oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored(), "Loading"), new GaugeFormat(true, false));
	}

}
