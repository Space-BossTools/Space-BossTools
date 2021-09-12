package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import mekanism.common.capabilities.Capabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.capability.GasHandlerEmpty;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismHelper;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;

public abstract class PowerSystemFuelOxygen extends PowerSystemFuel {

	private boolean canExtreactNearGas;

	public PowerSystemFuelOxygen(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	@Override
	public void update() {
		super.update();

		this.canExtreactNearGas = this.extractNearGas(1, true) > 0;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (CompatibleManager.MEKANISM.isLoaded()) {
			if (capability == Capabilities.GAS_HANDLER_CAPABILITY) {
				return LazyOptional.of(() -> new GasHandlerEmpty()).cast();
			}
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		int extractedNearGas = this.extractNearGas(amount, simulate);
		int extracted = super.extract(amount - extractedNearGas, simulate);
		return extractedNearGas + extracted;
	}

	public int extractNearGas(int amount, boolean simulate) {
		if (CompatibleManager.MEKANISM.isLoaded()) {
			return MekanismHelper.extracteNearOxygen(this.getTileEntity(), amount, simulate);
		} else {
			return 0;
		}
	}

	@Override
	public List<GaugeData> getGaugeDataList() {
		List<GaugeData> list = super.getGaugeDataList();

		if (!this.isCanExtreactNearGas()) {
			list.add(GaugeDataHelper.getOxygen(this));
		}

		return list;
	}

	public BossToolsRecipeType<? extends OxygenMakingRecipe> getRecipeType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	@Override
	protected int getFuelInternal(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		OxygenMakingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), f -> f.test(fuel));
		return recipe != null ? recipe.getOxygen() : -1;
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

	public boolean isCanExtreactNearGas() {
		return this.canExtreactNearGas;
	}

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/oxygen");
	}

}
