package net.mrscauthd.boss_tools.machines.machinetileentities;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public abstract class ItemStackToItemStackTileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {

	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_OUTPUT = 1;

	public static final String KEY_TIMER = "timer";
	public static final String KEY_MAXTIMER = "maxTimer";
	public static final String KEY_ACTIVATED = "activated";

	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	private ItemStack lastRecipeItemStack = null;
	private ItemStackToItemStackRecipe lastRecipe = null;

	public ItemStackToItemStackTileEntity(TileEntityType<?> type) {
		super(type);
	}

	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	private final EnergyStorageCapacityFlexible energyStorage = new EnergyStorageCapacityFlexible(this.getEnergyCapacity(), this.getEnergyMaxRecieve(), this.getEnergyMaxExtract(), 0) {
		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			int retval = super.receiveEnergy(maxReceive, simulate);
			ItemStackToItemStackTileEntity.this.onReceiveEnergy(maxReceive, simulate, retval);

			return retval;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			int retval = super.extractEnergy(maxExtract, simulate);
			ItemStackToItemStackTileEntity.this.onExtractEnergy(maxExtract, simulate, retval);
			return retval;
		}

	};

	protected int getEnergyCapacity() {
		return 9000;
	}

	protected int getEnergyMaxRecieve() {
		return 200;
	}

	protected int getEnergyMaxExtract() {
		return 200;
	}

	protected void onReceiveEnergy(int maxReceive, boolean simulate, int retval) {
		if (!simulate) {
			this.onEnergyDirty();
		}
	}

	protected void onExtractEnergy(int maxExtract, boolean simulate, int retval) {
		if (!simulate) {
			this.onEnergyDirty();
		}
	}

	protected void onEnergyDirty() {
		this.notifyBlockUpdate(2);
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		if (!this.checkLootAndRead(compound)) {
			this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		}
		ItemStackHelper.loadAllItems(compound, this.stacks);
		this.energyStorage.read(compound.getCompound("energyStorage"));

		if (this.energyStorage.getMaxEnergyStored() == 0) {
			this.energyStorage.setMaxEnergyStored(this.getEnergyCapacity());
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.stacks);
		}
		compound.put("energyStorage", this.energyStorage.write());
		return compound;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(this.getBlockState(), pkt.getNbtCompound());
	}

	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.UP) {
			return new int[] { SLOT_INGREDIENT };
		} else if (side == Direction.DOWN) {
			return new int[] { SLOT_OUTPUT };
		} else {
			return new int[] {};
		}

	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		if (index == SLOT_INGREDIENT && direction == Direction.UP) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return index == SLOT_OUTPUT && direction == Direction.DOWN;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return this.handlers[facing.ordinal()].cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void remove() {
		super.remove();

		for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
			handler.invalidate();
		}
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

		boolean requireNotify = false;
		requireNotify |= this.onTickUpdatePre();
		requireNotify |= this.cookIngredient();
		requireNotify |= this.onTickUpdatePost();
		requireNotify |= this.updateActivated();

		this.refreshBlockActivatedChanged();

		if (requireNotify) {
			this.notifyBlockUpdate();
		}

	}

	public void notifyBlockUpdate() {
		this.notifyBlockUpdate(3);
	}

	public void notifyBlockUpdate(int flag) {
		this.markDirty();
		BlockState state = this.getBlockState();
		this.getWorld().notifyBlockUpdate(this.getPos(), state, state, flag);
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

	protected boolean onTickUpdatePre() {
		return false;
	}

	protected boolean onTickUpdatePost() {
		return false;
	}

	public boolean canOutput(ItemStack recipeOutput) {
		ItemStack output = this.getItemHandler().getStackInSlot(SLOT_OUTPUT);
		return canOutput(recipeOutput, output);
	}

	public boolean canOutput(ItemStack recipeOutput, ItemStack output) {
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

	protected abstract int getEnergyForOperation();

	/**
	 * 
	 * @return complete extract energy for operation
	 */
	public boolean consumeEnergy() {
		EnergyStorageCapacityFlexible energyStorage = this.getEnergyStorage();
		int energyForOperateion = this.getEnergyForOperation();

		while (true) {
			if (energyStorage.extractEnergy(energyForOperateion, true) == energyForOperateion) {
				energyStorage.extractEnergy(energyForOperateion, false);
				this.notifyBlockUpdate();

				if (energyStorage.extractEnergy(energyForOperateion, true) < energyForOperateion) {
					this.feedEnergy(true);
				}

				return true;
			} else if (!this.feedEnergy(false)) {
				this.notifyBlockUpdate();
				return false;
			}
		}
	}

	protected boolean feedEnergy(boolean spareForNextTick) {
		return false;
	}

	protected boolean canCook() {
		return this.getEnergyStorage().getEnergyStored() >= this.getEnergyForOperation();
	}

	protected void onCooking() {
		this.setTimer(this.getTimer() + 1);
	}

	protected void onCantCooking() {

	}

	public boolean cookIngredient() {
		ItemStackToItemStackRecipe recipe = this.cacheRecipe();

		if (recipe == null) {
			return this.resetTimer();
		}

		ItemStack recipeOutput = recipe.getCraftingResult(this);

		if (this.canOutput(recipeOutput) == true) {

			if (this.canCook()) {
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

			return true;
		} else {
			return this.resetTimer();
		}

	}

	public boolean updateActivated() {
		boolean activated = this.canActivated();

		if (this.isActivated() != activated) {
			this.setActivated(activated);
			return true;
		}

		return false;
	}

	protected abstract boolean canActivated();

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
	}

	public int getTimer() {
		return this.getTileData().getInt(KEY_TIMER);
	}

	public void setTimer(int timer) {
		this.getTileData().putInt(KEY_TIMER, Math.max(timer, 0));
	}

	public int getMaxTimer() {
		return this.getTileData().getInt(KEY_MAXTIMER);
	}

	public void setMaxTimer(int maxTimer) {
		this.getTileData().putInt(KEY_MAXTIMER, maxTimer);
	}

	public double getTimerPercentage() {
		return this.getTimer() / (this.getMaxTimer() / 100.0D);
	}

	public boolean isActivated() {
		return this.getTileData().getBoolean(KEY_ACTIVATED);
	}

	public void setActivated(boolean activated) {
		this.getTileData().putBoolean(KEY_ACTIVATED, activated);
	}

	public EnergyStorageCapacityFlexible getEnergyStorage() {
		return this.energyStorage;
	}

}
