package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;

public abstract class PowerSystemFuelOxygen extends PowerSystemFuel {

	public PowerSystemFuelOxygen(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	public BossToolsRecipeType<? extends OxygenMakingRecipe> getRecipeType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	@Override
	protected int getFuelInternal(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		OxygenMakingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), f -> f.testIngredient(fuel));
		return recipe != null ? recipe.getOxygen() : -1;
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

	@Override
	public String getName() {
		return "FuelOxygen";
	}
	
}
