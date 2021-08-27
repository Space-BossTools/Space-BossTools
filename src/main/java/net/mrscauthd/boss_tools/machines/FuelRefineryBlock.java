package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;
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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.fluid.FuelFluid;
import net.mrscauthd.boss_tools.gui.FuelRefineryGUIGui;
import net.mrscauthd.boss_tools.machines.machinetileentities.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.machinetileentities.PowerSystem;
import net.mrscauthd.boss_tools.machines.machinetileentities.PowerSystemCommonEnergy;

public class FuelRefineryBlock {
	public static final int BUCKET_SIZE = 1000;
	public static final int BARREL_SIZE = 3000;
	public static final int LAVA_TO_FUEL = 100;
	public static final int FUEL_CONSUME_PER_TICK = 1;
	public static final int FLUID_PER_FUEL = 10;
	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_OUTPUT = 1;

	public static final String KEY_FUEL = "fuel";

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
	public static class CustomTileEntity extends AbstractMachineTileEntity {
		public CustomTileEntity() {
			super(ModInnet.FUEL_REFINERY.get());
		}

		@Override
		protected PowerSystem createPowerSystem() {
			return new PowerSystemCommonEnergy(this) {
				@Override
				public int getBasePowerForOperation() {
					return 1;
				}
			};
		}

		@Override
		public void read(BlockState blockState, CompoundNBT compound) {
			super.read(blockState, compound);

			CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(this.fluidTank, null, compound.get("fluidTank"));
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);

			compound.put("fluidTank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(this.fluidTank, null));

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
			return new FuelRefineryGUIGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Fuel Refinery");
		}

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
			if (super.canInsertItem(index, stack, direction)) {
				return true;
			}

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
			if (super.canExtractItem(index, stack, direction)) {
				return true;
			}

			Item item = stack != null ? stack.getItem() : null;

			if (index == SLOT_INGREDIENT) {
				return this.isSpentItem(item); // Eject empty bucket
			} else if (index == SLOT_OUTPUT) {
				return !this.isFuelFillableItem(item); // Wait for fill
			}

			return false;
		}

		private final FluidTank fluidTank = new FluidTank(3000, fs -> fs.getFluid() instanceof FuelFluid) {
			@Override
			protected void onContentsChanged() {
				super.onContentsChanged();
				markDirty();
			}
		};

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
				return LazyOptional.of(() -> this.fluidTank).cast();
			}

			return super.getCapability(capability, facing);
		}

		@Override
		protected void tickProcessing() {
			this.consumeIngredient();
			this.burnFuel();
			this.fillOutput();
		}

		public boolean hasSpaceInOutput() {
			FluidTank fluidTank = this.getFluidTank();
			int fluidAmount = fluidTank.getFluidAmount();
			int fluidCapacity = fluidTank.getCapacity();
			return fluidAmount < fluidCapacity;
		}

		public boolean consumeIngredient() {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack ingredient = itemHandler.getStackInSlot(SLOT_INGREDIENT);

			if (this.hasSpaceInOutput() && this.getPowerSystem().getStored() >= this.getPowerForOperation() && ingredient.getItem() == Items.LAVA_BUCKET && ingredient.getCount() == 1) {
				int fuel = this.getFuel();

				if (fuel < FUEL_CONSUME_PER_TICK) {
					itemHandler.setStackInSlot(SLOT_INGREDIENT, new ItemStack(Items.BUCKET));
					this.setFuel(fuel + LAVA_TO_FUEL);
					this.markDirty();
					return true;
				}

			}

			return false;
		}

		public boolean burnFuel() {

			if (this.hasSpaceInOutput()) {
				int fuel = this.getFuel();

				if (fuel >= FUEL_CONSUME_PER_TICK && this.getPowerSystem().consume(this.getPowerForOperation())) {
					this.setFuel(fuel - FUEL_CONSUME_PER_TICK);
					this.getFluidTank().fill(new FluidStack(ModInnet.FUEL_STILL.get(), FLUID_PER_FUEL * FUEL_CONSUME_PER_TICK), FluidAction.EXECUTE);
					this.setProcessedInThisTick();
					this.markDirty();
					return true;
				}

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
				this.markDirty();
				return true;
			}

			return false;
		}

		public FluidTank getFluidTank() {
			return this.fluidTank;
		}

		public int getFuel() {
			return this.getTileData().getInt(KEY_FUEL);
		}

		public void setFuel(int fuel) {
			fuel = Math.max(fuel, 0);

			if (this.getFuel() != fuel) {
				this.getTileData().putInt(KEY_FUEL, fuel);
				this.markDirty();
			}
		}

		@Override
		protected int getInitialInventorySize() {
			return 2;
		}

		@Override
		public boolean isIngredientReady() {
			return this.hasSpaceInOutput();
		}
	}
}