package net.mrscauthd.boss_tools.machines.machinetileentities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.primitives.Ints;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public abstract class ItemStackToItemStackTileEntity extends AbstractMachineTileEntity {

	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_OUTPUT = 1;
	public static final int SLOT_FUEL = 2;

	public static final String KEY_TIMER = "timer";
	public static final String KEY_MAXTIMER = "maxTimer";
	public static final String KEY_ACTIVATED = "activated";

	private final LazyOptional<? extends IItemHandler>[] handlers;
	private NonNullList<ItemStack> stacks = null;
	private PowerSystem powerSystem = null;

	private ItemStack lastRecipeItemStack = null;
	private ItemStackToItemStackRecipe lastRecipe = null;
	private boolean cookedInThisTick = false;

	public ItemStackToItemStackTileEntity(TileEntityType<?> type) {
		super(type);

		this.handlers = SidedInvWrapper.create(this, Direction.values());
		this.stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
		this.powerSystem = this.createPowerSystem();
	}

	public int getPowerPerTick() {
		return this.getPowerSystem().getBasePowerPerTick();
	}

	public int getPowerForOperation() {
		return this.getPowerSystem().getBasePowerForOperation();
	}

	public boolean isPowerEnoughForOperation() {
		return this.getPowerSystem().getStored() >= this.getPowerPerTick() + this.getPowerForOperation();
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		if (!this.checkLootAndRead(compound)) {
			this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		}
		ItemStackHelper.loadAllItems(compound, this.stacks);
		this.getPowerSystem().read(compound.getCompound("powerSystem"));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.stacks);
		}
		compound.put("powerSystem", this.getPowerSystem().write());
		return compound;
	}

	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		return this.getItems().stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	protected void getSlotsForFace(Direction direction, List<Integer> slots) {
		this.getPowerSystem().getSlotsForFace(direction, slots);
		if (direction == Direction.UP) {
			slots.add(SLOT_INGREDIENT);
		} else if (direction == Direction.DOWN) {
			slots.add(SLOT_OUTPUT);
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		List<Integer> slots = new ArrayList<>();
		this.getSlotsForFace(side, slots);
		return Ints.toArray(slots);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		if (this.getPowerSystem().canInsertItem(direction, index, stack)) {
			return true;
		} else if (index == SLOT_INGREDIENT && direction == Direction.UP) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (this.getPowerSystem().canExtractItem(direction, index, stack)) {
			return true;
		} else if (index == SLOT_OUTPUT && direction == Direction.DOWN) {
			return true;
		}
		return false;
	}

	public <T> LazyOptional<T> getCapabilityItemHandler(Capability<T> capability, @Nullable Direction facing) {

		if (facing != null) {
			return this.handlers[facing.ordinal()].cast();
		} else {
			return super.getCapability(capability, facing);
		}

	}

	public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, @Nullable Direction facing) {
		PowerSystem powerSystem = this.getPowerSystem();

		if (powerSystem instanceof PowerSystemEnergy) {
			PowerSystemEnergy powerSystemEnergy = (PowerSystemEnergy) powerSystem;

			if (powerSystemEnergy.canProvideCapability()) {
				IEnergyStorage energyStorage = powerSystemEnergy.getEnergyStorage();

				if (energyStorage != null) {
					return LazyOptional.of(() -> energyStorage).cast();
				}

			}

		}

		return null;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {

		if (!this.removed) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				LazyOptional<T> optional = this.getCapabilityItemHandler(capability, facing);
				if (optional != null) {
					return optional;
				}
			} else if (capability == CapabilityEnergy.ENERGY) {
				LazyOptional<T> optional = this.getCapabilityEnergy(capability, facing);
				if (optional != null) {
					return optional;
				}
			}
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void remove() {
		super.remove();
		Arrays.stream(this.handlers).forEach(h -> h.invalidate());
	}

	public ItemStackToItemStackRecipe cacheRecipe() {
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
	public void tick() {
		World world = this.getWorld();

		if (world.isRemote()) {
			return;
		}

		this.onTickUpdatePre();
		this.cookIngredient();
		this.onTickUpdatePost();

		this.updatePowerSystem();

		this.updateActivated();
		this.refreshBlockActivatedChanged();
	}

	protected void updatePowerSystem() {
		int eft = this.getPowerPerTick();
		PowerSystem powerSystem = this.getPowerSystem();

		if (eft > 0) {
			powerSystem.consume(eft);

			if (!this.isPowerEnoughForOperation()) {
				powerSystem.feed(true);
			}

		}

	}

	protected BooleanProperty getBlockActivatedProperty() {
		return BlockStateProperties.LIT;
	}

	protected void refreshBlockActivatedChanged() {
		BooleanProperty property = this.getBlockActivatedProperty();

		if (property == null) {
			return;
		}

		World world = this.getWorld();
		BlockPos pos = this.getPos();
		BlockState state = this.getBlockState();
		boolean activated = this.isActivated();

		if (state.get(property).booleanValue() != activated) {
			world.setBlockState(pos, state.with(property, activated), 3);
		}
	}

	protected void onTickUpdatePre() {
		this.cookedInThisTick = false;
	}

	protected void onTickUpdatePost() {
	}

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

	public boolean resetTimer() {
		if (this.getTimer() > 0) {
			this.setTimer(0);
			return true;
		} else {
			return false;
		}

	}

	protected abstract ItemStackToItemStackRecipeType<?> getRecipeType();

	@Override
	public boolean isIngredientReady() {
		return this.hasSpaceInOutput();
	}

	protected void onCooking() {
		this.setTimer(this.getTimer() + 1);
		this.cookedInThisTick = true;
	}

	protected void onCantCooking() {

	}

	public void cookIngredient() {
		ItemStackToItemStackRecipe recipe = this.cacheRecipe();

		if (recipe != null) {
			ItemStack recipeOutput = recipe.getCraftingResult(this);

			if (this.hasSpaceInOutput(recipeOutput)) {
				if (this.isPowerEnoughForOperation() && this.getPowerSystem().consume(this.getPowerForOperation())) {
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

	public boolean updateActivated() {
		this.setActivated(this.canActivated());
		return true;
	}

	protected boolean canActivated() {
		if (this.getPowerSystem() instanceof PowerSystemFuel) {
			return this.isPowerEnoughForOperation();
		} else {
			return this.cookedInThisTick;
		}

	}

	protected abstract PowerSystem createPowerSystem();

	public final PowerSystem getPowerSystem() {
		return this.powerSystem;
	}

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
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

	public boolean isActivated() {
		return this.getTileData().getBoolean(KEY_ACTIVATED);
	}

	public void setActivated(boolean activated) {
		if (this.isActivated() != activated) {
			this.getTileData().putBoolean(KEY_ACTIVATED, activated);
			this.markDirty();
		}
	}

}
