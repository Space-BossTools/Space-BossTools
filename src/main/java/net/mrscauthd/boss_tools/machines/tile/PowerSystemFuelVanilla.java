package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class PowerSystemFuelVanilla extends PowerSystemFuelAbstract {

	public PowerSystemFuelVanilla(AbstractMachineTileEntity tileEntity, Lazy<IItemHandlerModifiable> itemHandler, int slot) {
		super(tileEntity, itemHandler, slot);
	}

	public abstract IRecipeType<?> getRecipeType();

	@Override
	public int getBurnTime(ItemStack fuel) {
		return ForgeHooks.getBurnTime(fuel, this.getRecipeType());
	}

}
