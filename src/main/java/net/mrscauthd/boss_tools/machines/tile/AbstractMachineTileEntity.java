package net.mrscauthd.boss_tools.machines.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;
import net.mrscauthd.boss_tools.capability.IEnergyStorageHolder;

public abstract class AbstractMachineTileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity, IEnergyStorageHolder {

	public static final String KEY_ACTIVATED = "activated";

	private EnergyStorageBasic energyStorage = null;
	private IFluidHandler fluidHandler = null;
	private final Map<String, PowerSystem> powerSystems;
	private NonNullList<ItemStack> stacks = null;
	private final LazyOptional<? extends IItemHandler>[] itemHandlers;

	private boolean processedInThisTick = false;

	public AbstractMachineTileEntity(TileEntityType<?> type) {
		super(type);

		this.energyStorage = this.createEnergyStorage();
		this.fluidHandler = this.createFluidHandler();

		PowerSystemMap powerSystemMap = new PowerSystemMap();
		this.createPowerSystems(powerSystemMap);
		this.powerSystems = Collections.unmodifiableMap(powerSystemMap);
		this.itemHandlers = SidedInvWrapper.create(this, Direction.values());
		this.stacks = NonNullList.<ItemStack>withSize(this.getInitialInventorySize(), ItemStack.EMPTY);
	}

	public boolean isPowerEnoughForOperation() {
		return this.getPowerSystems().values().stream().allMatch(ps -> ps.isPowerEnoughForOperation());
	}

	public Map<PowerSystem, Integer> consumePowerForOperation() {
		if (this.isPowerEnoughForOperation()) {
			return this.getPowerSystems().values().stream().collect(Collectors.toMap(ps -> ps, ps -> ps.consumeForOperation()));
		} else {
			return null;
		}
	}

	public int getPowerPerTick(PowerSystem powerSystem, int base) {
		return base;
	}

	public int getPowerForOperation(PowerSystem powerSystem, int base) {
		return base;
	}

	protected int getInitialInventorySize() {
		return this.getPowerSystems().values().stream().collect(Collectors.summingInt(ps -> ps.getUsingSlots()));
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		if (!this.checkLootAndRead(compound)) {
			this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		}
		ItemStackHelper.loadAllItems(compound, this.stacks);

		CompoundNBT powerSystemCompound = compound.getCompound("powerSystem");

		for (Entry<String, PowerSystem> entry : this.getPowerSystems().entrySet()) {
			entry.getValue().read(powerSystemCompound.getCompound(entry.getKey()));
		}

		EnergyStorageBasic energyStorage = this.getEnergyStorage();

		if (energyStorage != null) {
			energyStorage.deserializeNBT(compound.getCompound("energyStorage"));
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.stacks);
		}

		CompoundNBT powerSystemCompound = new CompoundNBT();

		for (Entry<String, PowerSystem> entry : this.getPowerSystems().entrySet()) {
			powerSystemCompound.put(entry.getKey(), entry.getValue().write());
		}

		compound.put("powerSystem", powerSystemCompound);

		EnergyStorageBasic energyStorage = this.getEnergyStorage();

		if (energyStorage != null) {
			compound.put("energyStorage", energyStorage.serializeNBT());
		}

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
		this.getPowerSystems().values().stream().forEach(ps -> ps.getSlotsForFace(direction, slots));
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		List<Integer> slots = new ArrayList<>();
		this.getSlotsForFace(side, slots);
		return Ints.toArray(slots);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		if (this.getPowerSystems().values().stream().anyMatch(ps -> ps.canInsertItem(direction, index, stack))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (this.getPowerSystems().values().stream().anyMatch(ps -> ps.canExtractItem(direction, index, stack))) {
			return true;
		}
		return false;
	}

	public <T> LazyOptional<T> getCapabilityItemHandler(Capability<T> capability, @Nullable Direction facing) {
		if (facing != null) {
			return this.itemHandlers[facing.ordinal()].cast();
		} else {
			return super.getCapability(capability, facing);
		}

	}

	public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, @Nullable Direction facing) {

		EnergyStorageBasic energyStorage = this.getEnergyStorage();

		if (energyStorage != null) {
			return LazyOptional.of(() -> energyStorage).cast();
		}

		return null;
	}

	public <T> LazyOptional<T> getCapabilityFluidHandler(Capability<T> capability, Direction facing) {
		IFluidHandler fluidHandler = this.getFluidHandler();

		if (fluidHandler != null) {
			return LazyOptional.of(() -> fluidHandler).cast();
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
			} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
				LazyOptional<T> optional = this.getCapabilityFluidHandler(capability, facing);
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
		Arrays.stream(this.itemHandlers).forEach(h -> h.invalidate());
	}

	@Override
	public void tick() {
		World world = this.getWorld();

		if (world.isRemote()) {
			return;
		}

		this.onTickProcessingPre();

		if (this.canTickProcessing()) {
			this.tickProcessing();
		}

		this.onTickProcessingPost();

		this.updatePowerSystem();

		this.updateActivated();
		this.refreshBlockActivatedChanged();
	}

	protected void updatePowerSystem() {
		this.getPowerSystems().values().forEach(ps -> ps.update());
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

		if (state.hasProperty(property) && state.get(property).booleanValue() != activated) {
			world.setBlockState(pos, state.with(property, activated), 3);
		}
	}

	protected void onTickProcessingPre() {
		this.processedInThisTick = false;
	}

	protected boolean canTickProcessing() {
		return true;
	}

	protected abstract void tickProcessing();

	protected void onTickProcessingPost() {

	}

	public void updateActivated() {
		this.setActivated(this.canActivated());
	}

	protected boolean canActivated() {
		List<Entry<String, PowerSystem>> powerSystems = Lists.newArrayList(this.getPowerSystems().entrySet());
		int size = powerSystems.size();

		if (size == 0) {
			return this.processedInThisTick;
		} else if (size == 1) {
			PowerSystem primary = powerSystems.get(0).getValue();

			if (primary instanceof PowerSystemFuel) {
				return primary.isPowerEnoughForOperation();
			}
		}
		return this.processedInThisTick;
	}

	@Nonnull
	protected void createPowerSystems(PowerSystemMap map) {

	}

	public Map<String, PowerSystem> getPowerSystems() {
		return this.powerSystems;
	}

	@SuppressWarnings("unchecked")
	public <T extends PowerSystem> T getPowerSystem(Class<T> clazz) {
		return (T) this.getPowerSystems().values().stream().filter(ps -> clazz.isAssignableFrom(ps.getClass())).findFirst().orElse(null);
	}

	@Nullable
	protected IFluidHandler createFluidHandler() {
		return null;
	}

	@Nullable
	public IFluidHandler getFluidHandler() {
		return this.fluidHandler;
	}

	@Nullable
	protected EnergyStorageBasic createEnergyStorage() {
		return null;
	}

	protected EnergyStorageBasic createEnergyStorageCommonUsing() {
		return new EnergyStorageBasic(this, 9000, 200, 200);
	}

	protected EnergyStorageBasic createEnergyStorageCommonGenerating() {
		return new EnergyStorageBasic(this, 9000, 0, 200);
	}

	@Nullable
	public EnergyStorageBasic getEnergyStorage() {
		return this.energyStorage;
	}

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
	}

	public boolean isActivated() {
		return this.getTileData().getBoolean(KEY_ACTIVATED);
	}

	protected void setActivated(boolean activated) {
		if (this.isActivated() != activated) {
			this.getTileData().putBoolean(KEY_ACTIVATED, activated);
			this.markDirty();
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();

		World world = this.getWorld();
		if (world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.getChunkProvider().markBlockChanged(this.getPos());
		}
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
	public void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta) {
		if (energyStorage == this.getEnergyStorage()) {
			this.markDirty();
		}
	}

	protected boolean isProcessedInThisTick() {
		return processedInThisTick;
	}

	protected void setProcessedInThisTick() {
		this.processedInThisTick = true;
	}

	public abstract boolean hasSpaceInOutput();

	public boolean nullOrMatch(@Nullable Direction direction, Direction... matches) {
		return direction == null || ArrayUtils.contains(matches, direction);
	}
}
