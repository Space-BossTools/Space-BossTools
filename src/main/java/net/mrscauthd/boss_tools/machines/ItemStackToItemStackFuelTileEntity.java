package net.mrscauthd.boss_tools.machines;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public abstract class ItemStackToItemStackFuelTileEntity extends ItemStackToItemStackTileEntity {

	public static final int SLOT_FUEL = 2;

	public ItemStackToItemStackFuelTileEntity(TileEntityType<?> type) {
		super(type);
	}

	protected int getEnergyCapacity() {
		return 0;
	}

	protected int getEnergyMaxRecieve() {
		return Integer.MAX_VALUE;
	}

	protected int getEnergyMaxExtract() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected boolean onTickUpdatePost() {
		boolean changed = super.onTickUpdatePost();
		changed |= this.consumeEnergy();

		if (!this.canCook()) {
			changed |= this.feedEnergy(true);
		}

		return changed;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.UP || side == Direction.DOWN) {
			return super.getSlotsForFace(side);
		} else {
			return new int[] { SLOT_FUEL };
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		if (index == SLOT_FUEL && direction != Direction.DOWN) {
			return true;
		} else {
			return super.canInsertItem(index, stack, direction);
		}
	}

	@Override
	protected int getEnergyForOperation() {
		return 1;
	}

	@Override
	protected boolean canActivated() {
		return this.canCook();
	}

	@Override
	protected void onCantCooking() {
		super.onCantCooking();
		this.setTimer(this.getTimer() - 1);
	}

	@Override
	protected boolean feedEnergy(boolean spareForNextTick) {
		if (spareForNextTick == false) {
			return false;
		}
		IItemHandlerModifiable itemHandler = this.getItemHandler();
		ItemStack extra = itemHandler.getStackInSlot(SLOT_FUEL);

		if (!extra.isEmpty()) {
			ItemStackToItemStackRecipe recipe = this.cacheRecipe();
			if (recipe != null && this.canOutput(recipe.getCraftingResult(this))) {
				int burnTime = ForgeHooks.getBurnTime(extra, recipe.getType());

				if (burnTime > 0) {
					itemHandler.extractItem(SLOT_FUEL, 1, false);
					EnergyStorageCapacityFlexible energyStorage = this.getEnergyStorage();
					energyStorage.setMaxEnergyStored(energyStorage.getEnergyStored() + burnTime);
					energyStorage.receiveEnergy(burnTime, false);
					this.notifyBlockUpdate();
					return true;
				}
			}
		}
		return false;
	}

}
