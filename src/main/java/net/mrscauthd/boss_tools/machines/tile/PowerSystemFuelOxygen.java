package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;

public abstract class PowerSystemFuelOxygen extends PowerSystemFuel {

	public PowerSystemFuelOxygen(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	@Override
	public List<GaugeData> getGaugeDataList() {
		List<GaugeData> list = super.getGaugeDataList();
		list.add(GaugeDataHelper.getOxygen(this));
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

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/oxygen");
	}

}
