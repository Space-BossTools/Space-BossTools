package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;

public class PowerSystemFuelGeneratingRecipe extends PowerSystemFuelAbstract {

	public PowerSystemFuelGeneratingRecipe(AbstractMachineTileEntity tileEntity, Lazy<IItemHandlerModifiable> itemHandler, int slot) {
		super(tileEntity, itemHandler, slot);
	}

	public BossToolsRecipeType<? extends GeneratingRecipe> getRecipeType() {
		return BossToolsRecipeTypes.GENERATING;
	}

	public int getRecipeSlot() {
		return GeneratingRecipe.SLOT_FUEL;
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		int recipeSlot = this.getRecipeSlot();
		Inventory virtualInventory = new Inventory(recipeSlot + 1);
		virtualInventory.setInventorySlotContents(recipeSlot, fuel);

		GeneratingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), virtualInventory);
		return recipe != null ? recipe.getBurnTime() : -1;
	}

}
