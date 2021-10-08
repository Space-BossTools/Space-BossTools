package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.IOxygenStorageHolder;
import net.mrscauthd.boss_tools.capability.OxygenStorage;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismHelper;
import net.mrscauthd.boss_tools.compat.mekanism.OxygenStorageGasAdapter;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipeAbstract;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.inventory.StackCacher;

public abstract class OxygenMakingTileEntity extends AbstractMachineTileEntity {
	public static final int TANK_CAPACITY = 3000;
	public static final int TRANSFER_PER_TICK = 256;
	public static final ResourceLocation TANK_INPUT = new ResourceLocation("boss_tools", "input");
	public static final ResourceLocation TANK_OUTPUT = new ResourceLocation("boss_tools", "output");
	public static final int SLOT_INPUT_SOURCE = 0;
	public static final int SLOT_INPUT_SINK = 1;

	private FluidTank inputTank;
	private OxygenStorage outputTank;

	private StackCacher recipeCacher;
	private OxygenMakingRecipeAbstract cachedRecipe;

	public OxygenMakingTileEntity(TileEntityType<?> type) {
		super(type);

		this.recipeCacher = new StackCacher();
		this.cachedRecipe = null;
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);

		CapabilityOxygen.OXYGEN.readNBT(this.getOutputTank(), null, compound.getCompound("outputTank"));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		compound.put("outputTank", CapabilityOxygen.OXYGEN.writeNBT(this.getOutputTank(), null));

		return compound;
	}

	@Override
	public List<GaugeData> getGaugeDataList() {
		List<GaugeData> list = super.getGaugeDataList();

		if (!CompatibleManager.MEKANISM.isLoaded()) {
			list.add(GaugeDataHelper.getOxygen(this.getOutputTank()));
		}

		return list;
	}

	@Override
	protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry) {
		super.createFluidHandlers(registry);
		this.inputTank = (FluidTank) registry.computeIfAbsent(this.getInputTankName(), k -> this.creatFluidTank(k));
		this.outputTank = this.createOxygenTank(this.getOutputTankName());
	}

	protected int getInitialTankCapacity(ResourceLocation name) {
		return TANK_CAPACITY;
	}

	protected FluidTank creatFluidTank(ResourceLocation name) {
		return new FluidTank(this.getInitialTankCapacity(name), this.getInitialTankValidator(name)) {
			@Override
			protected void onContentsChanged() {
				super.onContentsChanged();
				OxygenMakingTileEntity.this.markDirty();
			}
		};
	}

	protected OxygenStorage createOxygenTank(ResourceLocation name) {
		return new OxygenStorage(new IOxygenStorageHolder() {
			@Override
			public void onOxygenChanged(IOxygenStorage oxygenStorage, int oxygenDelta) {
				OxygenMakingTileEntity.this.markDirty();
			}
		}, this.getInitialTankCapacity(name));
	}

	protected Predicate<FluidStack> getInitialTankValidator(ResourceLocation name) {
		Fluid fluid = this.getTankFluid(name);
		return fluid != null ? fs -> FluidUtil2.isEquivalentTo(fs, fluid) : null;
	}

	protected Fluid getTankFluid(ResourceLocation name) {
		if (name.equals(this.getInputTankName())) {
			return Fluids.WATER;
		} else {
			return null;
		}
	}

	@Override
	protected void tickProcessing() {
		this.drainSources();
		this.consumeIngredients();
		this.fillSinks();
	}

	public boolean consumeIngredients() {
		OxygenMakingRecipeAbstract recipe = this.cacheRecipe();

		if (recipe != null) {
			int oxygen = recipe.getOxygen();

			if (this.hasSpaceInOutput(oxygen)) {
				if (this.consumePowerForOperation() != null) {
					this.getInputTank().drain(recipe.getInput().getAmount(), FluidAction.EXECUTE);
					this.getOutputTank().receiveOxygen(oxygen, false);
					this.setProcessedInThisTick();
					return true;
				}
			}
		}

		return false;
	}

	protected void drainSources() {
		FluidUtil2.drainSource(this.getItemHandler(), this.getInputSourceSlot(), this.getInputTank(), this.getTransferPerTick());
	}

	protected void fillSinks() {
		FluidUtil2.fillSink(this.getItemHandler(), this.getInputSinkSlot(), this.getInputTank(), this.getTransferPerTick());
	}

	@Override
	public <T> LazyOptional<T> getCapabilityFluidHandler(Capability<T> capability, @Nullable Direction facing) {
		return LazyOptional.of(this::getInputTank).cast();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (CompatibleManager.MEKANISM.isLoaded()) {
			if (capability == MekanismHelper.getGasHandlerCapability()) {
				return LazyOptional.of(() -> new OxygenStorageGasAdapter(this.getOutputTank(), false, true)).cast();
			}
		}

		return super.getCapability(capability, facing);
	}

	@Override
	protected void getSlotsForFace(Direction direction, List<Integer> slots) {
		super.getSlotsForFace(direction, slots);
		slots.add(this.getInputSourceSlot());
	}

	@Override
	protected boolean onCanInsertItem(int index, ItemStack stack, Direction direction) {
		if (index == this.getInputSourceSlot()) {
			return FluidUtil2.canDrain(stack, this.getTankFluid(this.slotToTankName(index)));
		} else if (index == this.getInputSinkSlot()) {
			return FluidUtil2.canFill(stack, this.getTankFluid(this.slotToTankName(index)));
		}

		return super.onCanInsertItem(index, stack, direction);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (index == this.getInputSourceSlot()) {
			return !FluidUtil2.canDrain(stack, this.getTankFluid(this.slotToTankName(index)));
		} else if (index == this.getInputSinkSlot()) {
			return !FluidUtil2.canFill(stack, this.getTankFluid(this.slotToTankName(index)));
		}

		return super.canExtractItem(index, stack, direction);
	}

	@Override
	public boolean hasSpaceInOutput() {
		OxygenMakingRecipeAbstract recipe = this.cacheRecipe();
		return recipe != null && this.hasSpaceInOutput(recipe.getOxygen());
	}

	public boolean hasSpaceInOutput(int oxygen) {
		return hasSpaceInOutput(oxygen, this.getOutputTank());
	}

	public boolean hasSpaceInOutput(int oxygen, IOxygenStorage storage) {
		return (oxygen + storage.getOxygenStored()) <= storage.getMaxOxygenStored();
	}

	public OxygenMakingRecipeAbstract cacheRecipe() {
		FluidStack fluidStack = this.getInputTank().getFluid();

		if (fluidStack.isEmpty()) {
			this.recipeCacher.set(fluidStack);
			this.cachedRecipe = null;
		} else if (!this.recipeCacher.test(fluidStack)) {
			this.recipeCacher.set(fluidStack);
			this.cachedRecipe = this.getRecipeType().findFirst(this.getWorld(), r -> r.test(fluidStack));
		}

		return this.cachedRecipe;
	}

	public abstract BossToolsRecipeType<? extends OxygenMakingRecipeAbstract> getRecipeType();

	@Override
	protected int getInitialInventorySize() {
		return super.getInitialInventorySize() + 2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public int getInputSourceSlot() {
		return SLOT_INPUT_SOURCE;
	}

	public int getInputSinkSlot() {
		return SLOT_INPUT_SINK;
	}

	public boolean isSourceSlot(int slot) {
		return slot == this.getInputSourceSlot();
	}

	public boolean isSinkSlot(int slot) {
		return slot == this.getInputSinkSlot();
	}

	public FluidTank slotToFluidTank(int slot) {
		if (slot == this.getInputSourceSlot() || slot == this.getInputSinkSlot()) {
			return this.getInputTank();
		} else {
			return null;
		}
	}

	public IOxygenStorage slotToOxygenTank(int slot) {
		return null;
	}

	public ResourceLocation slotToTankName(int slot) {
		if (slot == this.getInputSourceSlot() || slot == this.getInputSinkSlot()) {
			return this.getInputTankName();
		} else {
			return null;
		}
	}

	public ResourceLocation getInputTankName() {
		return TANK_INPUT;
	}

	public FluidTank getInputTank() {
		return this.inputTank;
	}

	public ResourceLocation getOutputTankName() {
		return TANK_OUTPUT;
	}

	public IOxygenStorage getOutputTank() {
		return this.outputTank;
	}

	public int getTransferPerTick() {
		return TRANSFER_PER_TICK;
	}

}
