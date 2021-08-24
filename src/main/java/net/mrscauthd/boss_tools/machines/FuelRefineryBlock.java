package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.FuelRefineryGUIGui;

public class FuelRefineryBlock {
	public static final String KEY_ACTIVATED = "activated";
	public static final String KEY_FUEL = "fuel";
	public static final int BUCKET_SIZE = 1000;
	public static final int BARREL_SIZE = 3000;
	public static final int LAVA_TO_FUEL = 100;
	public static final int FUEL_CONSUME_PER_TICK = 1;
	public static final int ENERGY_CONSUME_PER_TICK = 1;
	public static final int FLUID_GEN_PER_TICK = 10;
	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_OUTPUT = 1;

	//Fuel Refinery Block
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
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

		@Override
		public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
			super.onBlockAdded(state, world, pos, oldState, moving);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), this, 1);
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
			super.onBlockActivated(state, world, pos, entity, hand, hit);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("Fuel Refinery");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new FuelRefineryGUIGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				}, new BlockPos(x, y, z));
			}
			return ActionResultType.SUCCESS;
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

		@Override
		public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
			super.eventReceived(state, world, pos, eventID, eventParam);
			TileEntity tileentity = world.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}

		@Override
		public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
			if (state.getBlock() != newState.getBlock()) {
				TileEntity tileentity = world.getTileEntity(pos);
				if (tileentity instanceof CustomTileEntity) {
					InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
					world.updateComparatorOutputLevel(pos, this);
				}
				super.onReplaced(state, world, pos, newState, isMoving);
			}
		}

		@Override
		public boolean hasComparatorInputOverride(BlockState state) {
			return true;
		}

		@Override
		public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof CustomTileEntity)
				return Container.calcRedstoneFromInventory((CustomTileEntity) tileentity);
			else
				return 0;
		}

	}

	// Fuel Refinery Tile Entity
	public static class CustomTileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {
		private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);

		public CustomTileEntity() {
			super(ModInnet.FUEL_REFINERY.get());
		}

		@Override
		public void read(BlockState blockState, CompoundNBT compound) {
			super.read(blockState, compound);
			if (!this.checkLootAndRead(compound)) {
				this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
			}
			ItemStackHelper.loadAllItems(compound, this.stacks);
			if (compound.get("energyStorage") != null)
				CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.get("energyStorage"));
			if (compound.get("fluidTank") != null)
				CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(fluidTank, null, compound.get("fluidTank"));
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);
			if (!this.checkLootAndWrite(compound)) {
				ItemStackHelper.saveAllItems(compound, this.stacks);
			}
			compound.put("energyStorage", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
			compound.put("fluidTank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(fluidTank, null));
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
		public ITextComponent getDefaultName() {
			return new StringTextComponent("fuel_refinery");
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new FuelRefineryGUIGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Fuel Refinery");
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
			return IntStream.range(0, this.getSizeInventory()).toArray();
		}

		public boolean isFuelFillableItem(Item item) {
			return item == Items.BUCKET || item == ModInnet.BARREL.get();
		}

		public boolean isIngredientItem(Item item) {
			return item == Items.LAVA_BUCKET;
		}

		public boolean isSpentItem(Item item) {
			return item == Items.BUCKET;
		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {

			ItemStack stackInSlot = this.getStackInSlot(index);

			if (stackInSlot.isEmpty() == true) {
				Item item = stack != null ? stack.getItem() : null;

				if (index == SLOT_INGREDIENT) {
					return this.isIngredientItem(item); // Inject lava bucket
				} else if (index == SLOT_OUTPUT) {
					return this.isFuelFillableItem(item); // Wait for fill
				}
			}

			return false;
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			Item item = stack != null ? stack.getItem() : null;

			if (index == SLOT_INGREDIENT) {
				return this.isSpentItem(item); // Eject empty bucket
			} else if (index == SLOT_OUTPUT) {
				return !this.isFuelFillableItem(item); // Wait for fill
			}

			return false;
		}

		private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
		private final EnergyStorage energyStorage = new EnergyStorage(9000, 200, 200, 0) {
			@Override
			public int receiveEnergy(int maxReceive, boolean simulate) {
				int retval = super.receiveEnergy(maxReceive, simulate);
				if (!simulate) {
					markDirty();
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
				}
				return retval;
			}

			@Override
			public int extractEnergy(int maxExtract, boolean simulate) {
				int retval = super.extractEnergy(maxExtract, simulate);
				if (!simulate) {
					markDirty();
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
				}
				return retval;
			}
		};
		private final FluidTank fluidTank = new FluidTank(3000, fs -> {
			if (fs.getFluid() == ModInnet.FUEL_STILL.get())
				return true;
			if (fs.getFluid() == ModInnet.FLOWING_FUEL.get())
				return true;
			return false;
		}) {
			@Override
			protected void onContentsChanged() {
				super.onContentsChanged();
				markDirty();
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			}
		};

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return handlers[facing.ordinal()].cast();
			if (!this.removed && capability == CapabilityEnergy.ENERGY)
				return LazyOptional.of(() -> energyStorage).cast();
			if (!this.removed && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
				return LazyOptional.of(() -> fluidTank).cast();
			return super.getCapability(capability, facing);
		}

		@Override
		public void remove() {
			super.remove();
			for (LazyOptional<? extends IItemHandler> handler : handlers)
				handler.invalidate();
		}

		@Override
		public void tick() {
			World world = this.getWorld();

			if (world.isRemote()) {
				return;
			}

			BlockPos pos = this.getPos();
			BlockState state = this.getBlockState();
			BlockState nextState = state;

			boolean requireNotify = false;
			requireNotify |= this.consumeIngredient();
			requireNotify |= this.burnFuel();
			requireNotify |= this.fillOutput();

			boolean activated = this.isActivated();

			if (state.get(CustomBlock.ACTIAVATED).booleanValue() != activated) {
				nextState = nextState.with(CustomBlock.ACTIAVATED, activated);
				world.setBlockState(pos, nextState);
			}

			if (requireNotify) {
				world.notifyBlockUpdate(pos, nextState, nextState, 3);
			}

		}

		public boolean canOperate() {
			FluidTank fluidTank = this.getFluidTank();
			int fluidAmount = fluidTank.getFluidAmount();
			int fluidCapacity = fluidTank.getCapacity();
			IEnergyStorage energyStorage = this.getEnergyStorage();
			return fluidAmount < fluidCapacity && energyStorage.getEnergyStored() >= ENERGY_CONSUME_PER_TICK;
		}

		public boolean consumeIngredient() {
			int fuel = this.getFuel();
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack ingredient = itemHandler.getStackInSlot(SLOT_INGREDIENT);

			if (fuel < FUEL_CONSUME_PER_TICK && this.canOperate() && ingredient.getItem() == Items.LAVA_BUCKET && ingredient.getCount() == 1) {
				itemHandler.setStackInSlot(SLOT_INGREDIENT, new ItemStack(Items.BUCKET));
				this.setFuel(fuel + LAVA_TO_FUEL);
				return true;
			}

			return false;
		}

		public boolean burnFuel() {
			int fuel = this.getFuel();

			if (fuel >= FUEL_CONSUME_PER_TICK && this.canOperate()) {
				this.setFuel(fuel - FUEL_CONSUME_PER_TICK);
				this.getEnergyStorage().extractEnergy(ENERGY_CONSUME_PER_TICK, false);
			this.getFluidTank().fill(new FluidStack(ModInnet.FUEL_STILL.get(), FLUID_GEN_PER_TICK), FluidAction.EXECUTE);
				this.setActivated(true);
				return true;
			} else if (this.isActivated()) {
				this.setActivated(false);
				return true;
			}
			return false;
		}

		public boolean fillOutput() {
			if (this.tryFillOutput(Items.BUCKET, BUCKET_SIZE, ModInnet.FUEL_BUCKET.get())) {
				return true;
			} else if (this.tryFillOutput(ModInnet.BARREL.get(), BARREL_SIZE, ModInnet.FUEL_BARREL.get())) {
				return true;
			}

			return false;
		}

		public boolean tryFillOutput(Item inputItem, int requireSize, Item outputItem) {
			FluidTank fluidTank = this.getFluidTank();
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT);

			if (output.getItem() == inputItem && output.getCount() == 1 && fluidTank.getFluidAmount() >= requireSize) {
				itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(outputItem));
				fluidTank.drain(requireSize, IFluidHandler.FluidAction.EXECUTE).getAmount();
				return true;
			}

			return false;
		}

		public IItemHandlerModifiable getItemHandler() {
			return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get();
		}

		public EnergyStorage getEnergyStorage() {
			return this.energyStorage;
		}

		public FluidTank getFluidTank() {
			return this.fluidTank;
		}

		public boolean isActivated() {
			return this.getTileData().getBoolean(KEY_ACTIVATED);
		}

		public void setActivated(boolean activated) {
			this.getTileData().putBoolean(KEY_ACTIVATED, activated);
		}

		public int getFuel() {
			return this.getTileData().getInt(KEY_FUEL);
		}

		public void setFuel(int fuel) {
			this.getTileData().putInt(KEY_FUEL, fuel);
		}
	}
}