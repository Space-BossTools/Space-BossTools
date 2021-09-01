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

	public int getPowerPerTick() {
		int base = this.getBasePowerPerTick();
		return this.getTileEntity().getPowerPerTick(this, base);
	}

	public int getPowerForOperation() {
		int base = this.getBasePowerForOperation();
		return this.getTileEntity().getPowerForOperation(this, base);
	}

	public boolean isPowerEnoughForOperation() {
		while (true) {
			if (this.getStored() >= this.getPowerPerTick() + this.getPowerForOperation()) {
				return true;
			} else if (!this.feed(false)) {
				return false;
			}
		}
	}

	/**
	 * 
	 * @return is power enough for operation and success consume power
	 */
	public boolean consumeForOperation() {
		return this.isPowerEnoughForOperation() && this.consume(this.getPowerForOperation());
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

	public void update() {
		int powerPerTick = this.getPowerPerTick();

		if (powerPerTick > 0) {
			this.consume(powerPerTick);

			if (!this.isPowerEnoughForOperation()) {
				this.feed(true);
			}
		}
	}
}
