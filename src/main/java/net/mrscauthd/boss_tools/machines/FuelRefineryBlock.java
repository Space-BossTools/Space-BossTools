package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;
import net.mrscauthd.boss_tools.capability.MultiFluidHandler;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.FuelRefineryGUIGui;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemCommonEnergy;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemMap;

public class FuelRefineryBlock {
	public static final int ENERGY_PER_TICK = 1;
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;
	public static final int SLOT_INPUT_SOURCE = 0;
	public static final int SLOT_INPUT_SINK = 1;
	public static final int SLOT_OUTPUT_SOURCE = 2;
	public static final int SLOT_OUTPUT_SINK = 3;

	// Fuel Refinery Block
	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;

		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIAVATED, Boolean.valueOf(false)));
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, ACTIAVATED);
		}

		public BlockState rotate(BlockState state, Rotation rot) {
			return state.with(FACING, rot.rotate(state.get(FACING)));
		}

		@SuppressWarnings("deprecation")
		public BlockState mirror(BlockState state, Mirror mirrorIn) {
			return state.rotate(mirrorIn.toRotation(state.get(FACING)));
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		}

		@Override
		public PushReaction getPushReaction(BlockState state) {
			return PushReaction.BLOCK;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			@SuppressWarnings("deprecation")
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty()) {
				return dropsOriginal;
			} else {
				return Collections.singletonList(new ItemStack(this, 1));
			}
		}

		@Override
		public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return state.get(ACTIAVATED) ? 12 : 0;
		}

		@Override
		public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
			return state.get(ACTIAVATED) ? 12 : 0;
		}

		@Override
		public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, this.getContainer(state, world, pos), pos);
				return ActionResultType.CONSUME;
			} else {
				return ActionResultType.SUCCESS;
			}
		}

		@Override
		public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new CustomTileEntity();
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
			super.eventReceived(state, world, pos, eventID, eventParam);
			TileEntity tileentity = world.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
			if (state.getBlock() != newState.getBlock()) {
				CustomTileEntity tileentity = (CustomTileEntity) world.getTileEntity(pos);
				InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
				world.updateComparatorOutputLevel(pos, this);
				super.onReplaced(state, world, pos, newState, isMoving);
			}
		}

		@Override
		public boolean hasComparatorInputOverride(BlockState state) {
			return true;
		}

		@Override
		public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
			CustomTileEntity tileentity = (CustomTileEntity) world.getTileEntity(pos);
			return Container.calcRedstoneFromInventory(tileentity);
		}

	}

	// Fuel Refinery Tile Entity
	public static class CustomTileEntity extends AbstractMachineTileEntity {
		private MultiFluidHandler fluidTank;

		private StackCacher recipeCacher;
		private FuelRefiningRecipe cachedRecipe;

		public CustomTileEntity() {
			super(ModInnet.FUEL_REFINERY.get());

			this.recipeCacher = new StackCacher();
			this.cachedRecipe = null;
		}

		@Override
		protected EnergyStorageBasic createEnergyStorage() {
			return this.createEnergyStorageCommonUsing();
		}

		@Override
		protected void createPowerSystems(PowerSystemMap map) {
			super.createPowerSystems(map);
			map.put(new PowerSystemCommonEnergy(this) {
				@Override
				public int getBasePowerForOperation() {
					return ENERGY_PER_TICK;
				}
			});
		}

		@Override
		protected IFluidHandler createFluidHandler() {
			this.fluidTank = new MultiFluidHandler(this.getTankableFluids(), 3000) {
				@Override
				protected void onContentsChanged() {
					super.onContentsChanged();
					markDirty();
				}
			};
			return this.fluidTank;
		}

		@Override
		public void read(BlockState blockState, CompoundNBT compound) {
			super.read(blockState, compound);

			this.fluidTank.deserializeNBT(compound.getCompound("fluidTank"));
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);

			compound.put("fluidTank", this.fluidTank.serializeNBT());

			return compound;
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("fuel_refinery");
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new FuelRefineryGUIGui.GuiContainerMod(id, player, this);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Fuel Refinery");
		}

		@Override
		protected void tickProcessing() {
			this.drainSources();
			this.consumeIngredients();
			this.fillSinks();
		}

		public boolean consumeIngredients() {
			FuelRefiningRecipe recipe = this.cacheRecipe();

			if (recipe != null) {
				FluidIngredient recipeOutput = recipe.getOutput();

				if (this.hasSpaceInOutput(recipeOutput)) {
					if (this.consumePowerForOperation() != null) {
						MultiFluidHandler tanks = this.getFluidTanks();
						tanks.getTank(this.getInputTank()).drain(recipe.getInput().getAmount(), FluidAction.EXECUTE);
						tanks.getTank(this.getOutputTank()).fill(recipeOutput.toStack(), FluidAction.EXECUTE);
						this.setProcessedInThisTick();
						return true;
					}
				}
			}

			return false;
		}

		protected void drainSources() {
			this.drainSource(this.getInputSourceSlot(), this.getInputTank());
			this.drainSource(this.getOutputSourceSlot(), this.getOutputTank());
		}

		protected void fillSinks() {
			this.fillSink(this.getInputSinkSlot(), this.getInputTank());
			this.fillSink(this.getOutputSinkSlot(), this.getOutputTank());
		}

		protected boolean fillSink(int itemSlot, int tankIndex) {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack stack = itemHandler.getStackInSlot(itemSlot);
			FluidTank tank = this.getFluidTanks().getTank(tankIndex);

			if (this.fillSinkBucket(itemSlot, tank, stack)) {
				return true;
			} else if (this.fillSinkCapability(itemSlot, tank, stack)) {
				return true;
			}

			return false;
		}

		protected boolean fillSinkBucket(int itemSlot, FluidTank tank, ItemStack itemStack) {
			if (itemStack.getItem() == Items.BUCKET) {
				int size = FluidUtil2.BUCKET_SIZE;
				FluidStack fluidStack = tank.drain(size, FluidAction.SIMULATE);

				if (fluidStack.getAmount() == size) {
					tank.drain(size, FluidAction.EXECUTE);
					this.getItemHandler().setStackInSlot(itemSlot, new ItemStack(fluidStack.getFluid().getFilledBucket()));
					return true;
				}
			}

			return false;
		}

		protected boolean fillSinkCapability(int slot, FluidTank ownTank, ItemStack itemStack) {
			IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

			if (fluidHandlerItem != null) {
				return this.fillSinkCapability(ownTank, fluidHandlerItem);
			} else {
				return false;
			}

		}

		protected boolean fillSinkCapability(FluidTank ownTank, IFluidHandler fluidHandler) {
			boolean accepted = false;

			if (fluidHandler != null) {
				for (int i = 0; i < fluidHandler.getTanks(); i++) {
					FluidStack fluidStack = ownTank.getFluid();
					FluidStack theriFilled = FluidUtil.tryFluidTransfer(fluidHandler, ownTank, fluidStack, true);

					if (theriFilled.isEmpty()) {
						accepted = true;
					}
				}
			}

			return accepted;
		}

		protected boolean drainSource(int itemSlot, int tankIndex) {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack stack = itemHandler.getStackInSlot(itemSlot);
			FluidTank tank = this.getFluidTanks().getTank(tankIndex);

			if (this.drainSourceBucket(itemSlot, tank, stack)) {
				return true;
			} else if (this.drainSourceCapability(itemSlot, tank, stack)) {
				return true;
			}

			return false;
		}

		protected boolean drainSourceBucket(int slot, FluidTank ownTank, ItemStack itemStack) {
			Item item = itemStack.getItem();
			Fluid fluid = FluidUtil2.findBucketFluid(item);

			if (fluid != null) {
				FluidStack fluidStack = new FluidStack(fluid, FluidUtil2.BUCKET_SIZE);

				if (ownTank.fill(fluidStack, FluidAction.SIMULATE) == fluidStack.getAmount()) {
					ownTank.fill(fluidStack, FluidAction.EXECUTE);
					this.getItemHandler().setStackInSlot(slot, new ItemStack(Items.BUCKET));
					return true;
				}
			}

			return false;
		}

		protected boolean drainSourceCapability(int slot, FluidTank ownTank, ItemStack itemStack) {
			IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

			if (fluidHandlerItem != null) {
				return this.drainSourceCapability(ownTank, fluidHandlerItem);
			} else {
				return false;
			}

		}

		protected boolean drainSourceCapability(FluidTank ownTank, IFluidHandler fluidHandler) {
			boolean accepted = false;

			if (fluidHandler != null) {
				for (int i = 0; i < fluidHandler.getTanks(); i++) {
					FluidStack fluidStack = fluidHandler.getFluidInTank(i);
					FluidStack theriDrained = FluidUtil.tryFluidTransfer(ownTank, fluidHandler, fluidStack, true);

					if (theriDrained.isEmpty()) {
						accepted = true;
					}
				}
			}

			return accepted;
		}

		@Override
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			IntStream.range(0, this.getSizeInventory()).forEach(slots::add);
		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, Direction direction) {
			if (super.canInsertItem(index, stack, direction)) {
				return true;
			}

			if (index == this.getInputSourceSlot()) {
				FluidTank tank = this.getFluidTanks().getTank(this.getInputTank());
				return FluidUtil2.getFluidStack(stack).stream().filter(tank::isFluidValid).findFirst().isPresent();
			} else if (index == this.getOutputSourceSlot()) {
				FluidTank tank = this.getFluidTanks().getTank(this.getOutputTank());
				return FluidUtil2.getFluidStack(stack).stream().filter(tank::isFluidValid).findFirst().isPresent();
			} else if (index == this.getInputSinkSlot()) {
				FluidStack fluidStack = this.getFluidTanks().getFluidInTank(this.getInputTank());
				return FluidUtil2.canAccept(stack, fluidStack) > 0;
			} else if (index == this.getOutputSinkSlot()) {
				FluidStack fluidStack = this.getFluidTanks().getFluidInTank(this.getOutputTank());
				return FluidUtil2.canAccept(stack, fluidStack) > 0;
			}

			return false;
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			return super.canExtractItem(index, stack, direction);
		}

		@Override
		public boolean hasSpaceInOutput() {
			FuelRefiningRecipe recipe = this.cacheRecipe();
			return recipe != null && this.hasSpaceInOutput(recipe.getOutput());
		}

		public boolean hasSpaceInOutput(FluidIngredient recipeOutput) {
			FluidTank tank = this.getFluidTanks().getTank(this.getOutputTank());
			return hasSpaceInOutput(recipeOutput, tank);
		}

		public FuelRefiningRecipe cacheRecipe() {
			FluidStack fluidStack = this.getFluidTanks().getFluidInTank(this.getInputTank());

			if (fluidStack.isEmpty()) {
				this.recipeCacher.set(fluidStack);
				this.cachedRecipe = null;
			} else if (!this.recipeCacher.test(fluidStack)) {
				this.recipeCacher.set(fluidStack);
				this.cachedRecipe = this.getRecipeType().findFirst(this.getWorld(), r -> r.test(fluidStack));
			}

			return this.cachedRecipe;
		}

		public BossToolsRecipeType<? extends FuelRefiningRecipe> getRecipeType() {
			return BossToolsRecipeTypes.FUELREFINING;
		}

		public MultiFluidHandler getFluidTanks() {
			return this.fluidTank;
		}

		@Override
		public IFluidHandler getFluidHandler() {
			return this.fluidTank;
		}

		@Override
		protected int getInitialInventorySize() {
			return 4;
		}

		public int getInputSourceSlot() {
			return SLOT_INPUT_SOURCE;
		}

		public int getInputSinkSlot() {
			return SLOT_INPUT_SINK;
		}

		public int getOutputSourceSlot() {
			return SLOT_OUTPUT_SOURCE;
		}

		public int getOutputSinkSlot() {
			return SLOT_OUTPUT_SINK;
		}

		protected List<Fluid> getTankableFluids() {
			return Lists.newArrayList(ModInnet.OIL_STILL.get(), ModInnet.FUEL_STILL.get());
		}

		public int getInputTank() {
			return TANK_INPUT;
		}

		public int getOutputTank() {
			return TANK_OUTPUT;
		}

	}
}