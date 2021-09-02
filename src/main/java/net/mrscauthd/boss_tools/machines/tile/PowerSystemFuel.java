package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class PowerSystemFuel extends PowerSystem {

	private final int slot;

	private int fuel;
	private int maxFuel;

	private ItemStack lastFuelItemStack;
	private int lastFuel;

	public PowerSystemFuel(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity);

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

	protected abstract int getFuelInternal(ItemStack fuel);

	public final int getFuel(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			this.lastFuelItemStack = fuel;
			this.lastFuel = -1;
		} else if (this.lastFuelItemStack == null || !ItemHandlerHelper.canItemStacksStack(this.lastFuelItemStack, lastFuelItemStack)) {
			this.lastFuelItemStack = fuel;
			this.lastFuel = this.getFuelInternal(fuel);
		}
		return this.lastFuel;
	}

	@Override
	public boolean feed(boolean spareForNextTick) {
		if (spareForNextTick == false) {
			return false;
		}

		IItemHandlerModifiable itemHandler = this.getItemHandler();
		int slot = this.getSlot();
		ItemStack fuelItemStack = itemHandler.getStackInSlot(slot);

		if (!fuelItemStack.isEmpty() && this.canFeed(spareForNextTick, fuelItemStack)) {
			int fuel = this.getFuel(fuelItemStack);

			if (fuel > 0) {
				itemHandler.extractItem(slot, 1, false);
				this.addFuel(fuel);
				return true;
			}
		}

		return false;
	}

	public void setFuel(int fuel) {
		fuel = Math.max(fuel, 0);
		this.maxFuel = fuel;
		this.receive(fuel, false);
	}

	public void addFuel(int fuel) {
		fuel = Math.max(fuel, 0);
		this.maxFuel = this.getStored() + fuel;
		this.receive(fuel, false);
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
		return this.getTileEntity().getItemHandler();
	}

	public int getSlot() {
		return this.slot;
	}

	@Override
	public String getName() {
		return "Fuel";
	}
}
