package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeHooks;

public abstract class PowerSystemFuelBurnTimeVanilla extends PowerSystemFuelBurnTime {

	public PowerSystemFuelBurnTimeVanilla(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	public abstract IRecipeType<?> getRecipeType();

	@Override
	public int getFuel(ItemStack fuel) {
		return ForgeHooks.getBurnTime(fuel, this.getRecipeType());
	}

	@Override
	public String getName() {
		return "FuelBurnTimeVanilla";
	}
	
}
