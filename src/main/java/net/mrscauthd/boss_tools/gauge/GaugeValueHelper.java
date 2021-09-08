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

public class GaugeValueHelper {

	public static final ResourceLocation ENERGY_NAME = new ResourceLocation("boss_tools", "energy");
	public static final ResourceLocation OXYGEN_NAME = new ResourceLocation("boss_tools", "oxygen");
	public static final ResourceLocation BURNTIME_NAME = new ResourceLocation("boss_tools", "burntime");
	public static final ResourceLocation COOKTIME_NAME = new ResourceLocation("boss_tools", "cooktime");

	public static IGaugeValue getFluid(Fluid fluid, int amount, int capacity) {
		return getFluid(new FluidStack(fluid, amount), capacity);
	}

	public static IGaugeValue getFluid(FluidStack stack, int capacity) {
		return new FluidStackGaugeValue(stack, capacity, new GaugeValueFormat(true, false));
	}

	public static IGaugeValue getFluid(IFluidTank tank) {
		return getFluid(tank.getFluid(), tank.getCapacity());
	}

	public static IGaugeValue getEnergy(int stored, int capacity) {
		return new SimpleGaugeValue(ENERGY_NAME, stored, capacity, new GaugeValueFormat(true, false)).displayeName("Energy").unit("FE");
	}

	public static IGaugeValue getEnergy(IEnergyStorage energyStorage) {
		return getEnergy(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
	}

	public static IGaugeValue getEnergy(AbstractMachineTileEntity machineTileEntity) {
		return getEnergy(machineTileEntity.getPrimaryEnergyStorage());
	}

	public static IGaugeValue getOxygen(int amount, int capacity) {
		return new SimpleGaugeValue(OXYGEN_NAME, amount, capacity, new GaugeValueFormat(true, false)).displayeName("Oxygen").color(0xA000FFFF);
	}

	public static IGaugeValue getOxygen(IOxygenStorage oxygenStorage) {
		return getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
	}

	public static IGaugeValue getOxygen(PowerSystemFuelOxygen oxygenPowerSystem) {
		return getOxygen(oxygenPowerSystem.getStored(), oxygenPowerSystem.getCapacity());
	}

	public static IGaugeValue getBurnTime(int amount, int capacity) {
		return new SimpleGaugeValue(BURNTIME_NAME, amount, capacity, new GaugeValueFormat(true, false)).displayeName("Burn Time").color(0xA0FF3F00);
	}

	public static IGaugeValue getBurnTime(PowerSystemFuelBurnTime fuelPowerSystem) {
		return getBurnTime(fuelPowerSystem.getStored(), fuelPowerSystem.getCapacity());
	}

	public static IGaugeValue getCookTime(int timer, int maxTimer) {
		return new SimpleGaugeValue(COOKTIME_NAME, maxTimer - timer, maxTimer).format(new GaugeValueFormat(true, true)).displayeName("Cook Time");
	}

}
