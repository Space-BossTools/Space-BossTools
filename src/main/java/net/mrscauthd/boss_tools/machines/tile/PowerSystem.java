package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public abstract class PowerSystem {
	private final AbstractMachineTileEntity tileEntity;

	public PowerSystem(AbstractMachineTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	public int getUsingSlots() {
		return 0;
	}

	public abstract int getBasePowerPerTick();

	public abstract int getBasePowerForOperation();

	public abstract int getStored();

	public abstract int getCapacity();

	public double getStoredRatio() {
		int capacity = this.getCapacity();
		return capacity > 0 ? ((double) this.getStored() / (double) capacity) : 0;
	}

	public double getStoredPercentage() {
		return this.getStoredRatio() * 100.0D;
	}

	public abstract int receive(int amount, boolean simulate);

	public abstract int extract(int amount, boolean simulate);

	public void read(CompoundNBT compound) {

	}

	public CompoundNBT write() {
		return new CompoundNBT();
	}

	/**
	 * 
	 * @return complete extract energy for operation
	 */
	public boolean consume(int amount) {
		while (true) {
			if (this.extract(amount, true) == amount) {
				this.extract(amount, false);

				if (this.extract(amount, true) < amount) {
					this.feed(true);
				}

				return true;
			} else if (!this.feed(false)) {
				return false;
			}
		}
	}

	public boolean feed(boolean spareForNextTick) {
		return false;
	}

	public void getSlotsForFace(Direction direction, List<Integer> slots) {

	}

	public boolean canInsertItem(@Nullable Direction direction, int index, ItemStack stack) {
		return false;
	}

	public boolean canExtractItem(@Nullable Direction direction, int index, ItemStack stack) {
		return false;
	}

	public final AbstractMachineTileEntity getTileEntity() {
		return this.tileEntity;
	}

}
