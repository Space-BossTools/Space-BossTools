package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;

public abstract class OxygenUsingTileEntity extends AbstractMachineTileEntity {

	private PowerSystemFuelOxygen oxygenPowerSystem;

	public OxygenUsingTileEntity(TileEntityType<?> type) {
		super(type);

		this.oxygenPowerSystem = this.createOxygenPowerSystem();
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);

		this.getOxygenPowerSystem().read(compound.getCompound("oxygen"));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		compound.put("oxygen", this.getOxygenPowerSystem().write());

		return compound;
	}

	@Override
	protected void updatePowerSystem() {
		super.updatePowerSystem();

		this.getOxygenPowerSystem().update();
	}

	protected abstract PowerSystemFuelOxygen createOxygenPowerSystem();

	public PowerSystemFuelOxygen getOxygenPowerSystem() {
		return this.oxygenPowerSystem;
	}

	@Override
	protected int getInitialInventorySize() {
		return super.getInitialInventorySize() + this.getOxygenPowerSystem().getUsingSlots();
	}

	public BossToolsRecipeType<? extends OxygenMakingRecipe> getOxygenMakingRecipeType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	public abstract int getActivatingSlot();

	@Override
	protected void tickProcessing() {
		PowerSystem powerSystem = this.getPowerSystem();
		PowerSystemFuelOxygen oxygenPowerSystem = this.getOxygenPowerSystem();

		if (this.canUsingOxygen() && this.hasSpaceInOutput() && powerSystem.isPowerEnoughForOperation() && oxygenPowerSystem.isPowerEnoughForOperation()) {
			if (powerSystem.consumeForOperation() && oxygenPowerSystem.consumeForOperation()) {
				this.onUsingMaking();
				this.markDirty();
				this.setProcessedInThisTick();
			}
		}
	}

	protected abstract boolean canUsingOxygen();

	protected abstract void onUsingMaking();

	@Override
	protected void getSlotsForFace(Direction direction, List<Integer> slots) {
		super.getSlotsForFace(direction, slots);

		this.getOxygenPowerSystem().getSlotsForFace(direction, slots);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, Direction direction) {
		if (super.canInsertItem(index, stack, direction)) {
			return true;
		}

		if (this.getOxygenPowerSystem().canInsertItem(direction, index, stack)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (super.canExtractItem(index, stack, direction)) {
			return true;
		}

		if (this.getOxygenPowerSystem().canExtractItem(direction, index, stack)) {
			return true;
		}

		return false;
	}

	public boolean hasSpaceInOutput(IOxygenStorage oxygenStorage) {
		return oxygenStorage != null ? oxygenStorage.getOxygenStored() < oxygenStorage.getMaxOxygenStored() : false;
	}

	public IOxygenStorage getItemOxygenStorage(ItemStack itemStack) {
		return !itemStack.isEmpty() ? itemStack.getCapability(CapabilityOxygen.OXYGEN).orElse(null) : null;
	}
}
