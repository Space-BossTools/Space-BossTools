package net.mrscauthd.boss_tools.machines.machinetileentities;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public abstract class ItemStackToItemStackTileEntity extends AbstractMachineTileEntity {

	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_OUTPUT = 1;
	public static final int SLOT_FUEL = 2;

	public static final String KEY_TIMER = "timer";
	public static final String KEY_MAXTIMER = "maxTimer";

	private ItemStack lastRecipeItemStack = null;
	private ItemStackToItemStackRecipe lastRecipe = null;

	public ItemStackToItemStackTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	protected IFluidHandler createFluidHandler() {
		return null;
	}

	@Override
	protected int getInitialInventorySize() {
		return super.getInitialInventorySize() + 2;
	}

	protected void getSlotsForFace(Direction direction, List<Integer> slots) {
		super.getSlotsForFace(direction, slots);

		if (direction == Direction.UP) {
			slots.add(SLOT_INGREDIENT);
		} else if (direction == Direction.DOWN) {
			slots.add(SLOT_OUTPUT);
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		if (super.canInsertItem(index, stack, direction)) {
			return true;
		} else if (index == SLOT_INGREDIENT && direction == Direction.UP) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (super.canExtractItem(index, stack, direction)) {
			return true;
		} else if (index == SLOT_OUTPUT && direction == Direction.DOWN) {
			return true;
		}
		return false;
	}

	@Override
	protected void tickProcessing() {
		this.cookIngredient();
	}

	protected ItemStackToItemStackRecipe cacheRecipe() {
		ItemStack itemStack = this.getStackInSlot(SLOT_INGREDIENT);

		if (itemStack == null || itemStack.isEmpty()) {
			this.lastRecipeItemStack = itemStack;
			this.lastRecipe = null;
		} else if (this.lastRecipeItemStack == null || !ItemHandlerHelper.canItemStacksStack(this.lastRecipeItemStack, itemStack)) {
			this.lastRecipeItemStack = itemStack;
			this.lastRecipe = this.getRecipeType().findFirst(this.getWorld(), this);

			if (this.lastRecipe != null) {
				this.setMaxTimer(this.lastRecipe.getCookTime());
			}
		}
		return this.lastRecipe;
	}

	@Override
	public boolean hasSpaceInOutput() {
		IRecipe<IInventory> cacheRecipe = this.cacheRecipe();
		return cacheRecipe != null && this.hasSpaceInOutput(cacheRecipe.getRecipeOutput());
	}

	public boolean hasSpaceInOutput(ItemStack recipeOutput) {
		ItemStack output = this.getItemHandler().getStackInSlot(SLOT_OUTPUT);
		return hasSpaceInOutput(recipeOutput, output);
	}

	public boolean hasSpaceInOutput(ItemStack recipeOutput, ItemStack output) {
		if (output.isEmpty()) {
			return true;
		} else if (ItemHandlerHelper.canItemStacksStack(output, recipeOutput)) {
			int limit = Math.min(recipeOutput.getMaxStackSize(), this.getInventoryStackLimit());
			return (output.getCount() + recipeOutput.getCount()) <= limit;
		}

		return false;
	}

	protected boolean resetTimer() {
		if (this.getTimer() > 0) {
			this.setTimer(0);
			return true;
		} else {
			return false;
		}

	}

	protected abstract ItemStackToItemStackRecipeType<?> getRecipeType();

	protected void onCooking() {
		this.setTimer(this.getTimer() + 1);
		this.setProcessedInThisTick();
	}

	protected void onCantCooking() {
		this.setTimer(this.getTimer() - 1);
	}

	protected void cookIngredient() {
		ItemStackToItemStackRecipe recipe = this.cacheRecipe();

		if (recipe != null) {
			ItemStack recipeOutput = recipe.getCraftingResult(this);

			if (this.hasSpaceInOutput(recipeOutput)) {
				if (this.isPowerEnoughAndConsume()) {
					this.onCooking();

					if (this.getTimer() >= this.getMaxTimer()) {
						IItemHandlerModifiable itemHandler = this.getItemHandler();
						itemHandler.insertItem(SLOT_OUTPUT, recipeOutput.copy(), false);
						itemHandler.extractItem(SLOT_INGREDIENT, 1, false);
						this.setTimer(0);
					}
				} else {
					this.onCantCooking();
				}
			} else {
				this.resetTimer();
			}
		} else {
			this.resetTimer();
		}

	}

	public int getTimer() {
		return this.getTileData().getInt(KEY_TIMER);
	}

	public void setTimer(int timer) {
		timer = Math.max(timer, 0);

		if (this.getTimer() != timer) {
			this.getTileData().putInt(KEY_TIMER, timer);
			this.markDirty();
		}
	}

	public int getMaxTimer() {
		return this.getTileData().getInt(KEY_MAXTIMER);
	}

	public void setMaxTimer(int maxTimer) {
		maxTimer = Math.max(maxTimer, 0);

		if (this.getMaxTimer() != maxTimer) {
			this.getTileData().putInt(KEY_MAXTIMER, maxTimer);
			this.markDirty();
		}
	}

	public double getTimerPercentage() {
		return this.getTimer() / (this.getMaxTimer() / 100.0D);
	}

}
