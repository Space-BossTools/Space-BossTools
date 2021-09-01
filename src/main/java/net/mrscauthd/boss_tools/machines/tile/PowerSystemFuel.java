package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class PowerSystemFuel extends PowerSystem {
	
	private final Lazy<IItemHandlerModifiable> itemHandler;
	private final int slot;

	private int fuel;
	private int maxFuel;

	public PowerSystemFuel(AbstractMachineTileEntity tileEntity, Lazy<IItemHandlerModifiable> itemHandler, int slot) {
		super(tileEntity);

		this.itemHandler = itemHandler;
		this.slot = slot;
	}

	@Override
	public int getUsingSlots() {
		return 1;
	}

	@Override
	public int receive(int amount, boolean simulate) {
		int received = Math.min(this.getCapacity() - this.getStored(), Math.max(amount, 0));

		if (!simulate) {
			this.fuel += received;
			this.getTileEntity().markDirty();
		}

		return received;
	}

	@Override
	public int extract(int amount, boolean simulate) {
		int extracted = Math.min(this.getStored(), Math.max(amount, 0));

		if (!simulate) {
			this.fuel -= extracted;
			this.getTileEntity().markDirty();
		}

		return extracted;
	}

	@Override
	public int getStored() {
		return this.fuel;
	}

	@Override
	public int getCapacity() {
		return this.maxFuel;
	}

	public boolean canFeed(boolean spareForNextTick, ItemStack fuel) {
		return this.getTileEntity().hasSpaceInOutput();
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		this.fuel = compound.getInt("fuel");
		this.maxFuel = compound.getInt("maxFuel");
	}

	@Override
	public CompoundNBT write() {
		CompoundNBT compound = super.write();

		compound.putInt("fuel", this.fuel);
		compound.putInt("maxFuel", this.maxFuel);

		return compound;
	}

	public abstract int getFuel(ItemStack fuel);

	@Override
	public boolean feed(boolean spareForNextTick) {
		if (spareForNextTick == false) {
			return false;
		}

		IItemHandlerModifiable itemHandler = this.getItemHandler();
		int slot = this.getSlot();
		ItemStack fuel = itemHandler.getStackInSlot(slot);

		if (!fuel.isEmpty() && this.canFeed(spareForNextTick, fuel)) {
			int burnTime = this.getFuel(fuel);

			if (burnTime > 0) {
				itemHandler.extractItem(slot, 1, false);
				this.maxFuel = this.getStored() + burnTime;
				this.receive(burnTime, false);
				return true;
			}
		}

		return false;
	}

	public boolean matchDirection(Direction direction) {
		return direction == null || (direction != Direction.UP && direction != Direction.DOWN);
	}

	@Override
	public void getSlotsForFace(Direction direction, List<Integer> slots) {
		if (this.matchDirection(direction)) {
			slots.add(this.getSlot());
		}
	}

	@Override
	public boolean canInsertItem(@Nullable Direction direction, int index, ItemStack stack) {
		return this.matchDirection(direction) && index == this.getSlot() && this.getFuel(stack) > 0;
	}

	public IItemHandlerModifiable getItemHandler() {
		return this.itemHandler.get();
	}

	public int getSlot() {
		return this.slot;
	}

}
