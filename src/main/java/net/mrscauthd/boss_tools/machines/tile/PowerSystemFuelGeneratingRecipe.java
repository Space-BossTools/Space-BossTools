package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;

public class PowerSystemFuelGeneratingRecipe extends PowerSystemFuelBurnTime {

	public PowerSystemFuelGeneratingRecipe(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	public BossToolsRecipeType<? extends GeneratingRecipe> getRecipeType() {
		return BossToolsRecipeTypes.GENERATING;
	}

	@Override
	public int getFuel(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		GeneratingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), f -> f.testIngredient(fuel));
		return recipe != null ? recipe.getBurnTime() : -1;
	}

	@Override
	public String getName() {
		return "FuelBurnTimeGenerating";
	}
	
}
