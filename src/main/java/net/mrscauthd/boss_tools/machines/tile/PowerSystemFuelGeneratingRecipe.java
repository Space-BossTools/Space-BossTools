package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;

public class PowerSystemFuelGeneratingRecipe extends PowerSystemFuelBurnTime {

	public PowerSystemFuelGeneratingRecipe(AbstractMachineTileEntity tileEntity, Lazy<IItemHandlerModifiable> itemHandler, int slot) {
		super(tileEntity, itemHandler, slot);
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

}
