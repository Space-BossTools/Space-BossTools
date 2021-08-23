
package net.mrscauthd.boss_tools.machines;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.gui.BlastFurnaceGUIGui;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.crafting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.ToolType;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Mirror;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.NetworkManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockItem;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import javax.annotation.Nullable;

import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import io.netty.buffer.Unpooled;

@BossToolsModElements.ModElement.Tag
public class BlastingFurnaceBlock extends BossToolsModElements.ModElement {
	public static final int SLOT_INGREDIENT = 0;
	public static final int SLOT_EXTRA = 1;
	public static final int SLOT_OUTPUT = 2;

	public static final String KEY_FUEL = "fuel";
	public static final String KEY_MAXFUEL = "maxFuel";
	public static final String KEY_TIMER = "timer";
	public static final String KEY_MAXTIMER = "maxTimer";
	public static final String KEY_ACTIVATED = "activated";

	public static final Map<Item, Integer> FUEL_MAP = new HashMap<>();
	
	static {
		FUEL_MAP.put(Items.COAL, 660);
		FUEL_MAP.put(Items.COAL_BLOCK, 960);
	}
	
	@ObjectHolder("boss_tools:blast_furnace")
	public static final Block block = null;
	@ObjectHolder("boss_tools:blast_furnace")
	public static final TileEntityType<CustomTileEntity> tileEntityType = null;
	public BlastingFurnaceBlock(BossToolsModElements instance) {
		super(instance, 73);
		FMLJavaModLoadingContext.get().getModEventBus().register(new TileEntityRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(BossToolsItemGroups.tab_machines))
				.setRegistryName(block.getRegistryName()));
	}
	private static class TileEntityRegisterHandler {
		@SubscribeEvent
		public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("blast_furnace"));
		}
	}

	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;
		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1)
					.harvestTool(ToolType.PICKAXE).setRequiresTool());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIAVATED, Boolean.valueOf(false)));
			setRegistryName("blast_furnace");
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
			;
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
		public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
			CustomTileEntity tileEntity = (CustomTileEntity) world.getTileEntity(pos);
			BlockState nextState = state;

			boolean requireNotify = false;
			requireNotify |= tileEntity.cookIngredient();
			requireNotify |= tileEntity.burnFuel();
			requireNotify |= tileEntity.udpateActivated();

			boolean activated = tileEntity.isActivated();

			if (tileEntity.isActivated() != activated) {
				nextState = state.with(ACTIAVATED, activated);
				world.setBlockState(pos, nextState, 3);
			}

			if (requireNotify) {
				world.notifyBlockUpdate(pos, nextState, nextState, 3);
			}

			super.tick(state, world, pos, random);

			world.getPendingBlockTicks().scheduleTick(pos, this, 1);
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
						return new StringTextComponent("Blast Furnace");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new BlastFurnaceGUIGui.GuiContainerMod(id, inventory,
								new PacketBuffer(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
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

	public static class CustomTileEntity extends LockableLootTileEntity implements ISidedInventory {
		private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
		protected CustomTileEntity() {
			super(tileEntityType);
		}

		@Override
		public void read(BlockState blockState, CompoundNBT compound) {
			super.read(blockState, compound);
			if (!this.checkLootAndRead(compound)) {
				this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
			}
			ItemStackHelper.loadAllItems(compound, this.stacks);
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);
			if (!this.checkLootAndWrite(compound)) {
				ItemStackHelper.saveAllItems(compound, this.stacks);
			}
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
			return new StringTextComponent("blast_furnace");
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new BlastFurnaceGUIGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Blast Furnace");
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
			if (side == Direction.UP) {
				return new int[] { SLOT_INGREDIENT };
			} else if (side == Direction.DOWN) {
				return new int[] { SLOT_OUTPUT };
			} else {
				return new int[] { SLOT_EXTRA };
			}

		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
			if (index == SLOT_INGREDIENT && direction == Direction.UP) {
				return true;
			} else if (index == SLOT_EXTRA && direction != Direction.DOWN) {
				return true;
			}

			return false;
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			return index == SLOT_OUTPUT && direction == Direction.DOWN;
		}

		private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return handlers[facing.ordinal()].cast();
			return super.getCapability(capability, facing);
		}

		@Override
		public void remove() {
			super.remove();
			for (LazyOptional<? extends IItemHandler> handler : handlers)
				handler.invalidate();
		}

		public boolean canRecipeOperate(ItemStack recipeOutput) {
			ItemStack output = this.getItemHandler().getStackInSlot(SLOT_OUTPUT);
			return canRecipeOperate(recipeOutput, output);
		}

		public boolean canRecipeOperate(ItemStack recipeOutput, ItemStack output) {
			Item outputItem = output.getItem();

			if (outputItem == Items.AIR) {
				return true;
			} else if (output.isItemEqual(recipeOutput)) {
				int limit = Math.min(recipeOutput.getMaxStackSize(), this.getInventoryStackLimit());
				return (output.getCount() + recipeOutput.getCount()) <= limit;
			}

			return false;
		}

		public boolean resetTimer() {
			if (this.getTimer() > 0) {
				this.setTimer(0);
				return true;
			} else {
				return false;
			}

		}

		public boolean burnFuel() {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack ingredient = itemHandler.getStackInSlot(SLOT_INGREDIENT);
			ItemStack extra = itemHandler.getStackInSlot(SLOT_EXTRA);

			int prevFuel = this.getFuel();
			int fuel = prevFuel;

			if (fuel > 0) {
				fuel--;
				this.setFuel(fuel);
			}

			if (fuel == 0) {
				int newFuel = 0;

				if (!ingredient.isEmpty() && !extra.isEmpty()) {

					World world = this.getWorld();
					BlastingRecipe recipe = BossToolsRecipeTypes.BLASTING.findFirst(world, this);

					if (this.canRecipeOperate(recipe.getCraftingResult(this)) == true) {
						newFuel = FUEL_MAP.get(extra.getItem());
						itemHandler.extractItem(SLOT_EXTRA, 1, false);
					}

				}

				fuel = newFuel;
				this.setFuel(newFuel);
				this.setMaxFuel(newFuel);
			}

			return prevFuel != fuel;
		}

		public boolean cookIngredient() {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			ItemStack ingredient = itemHandler.getStackInSlot(SLOT_INGREDIENT);

			if (ingredient.isEmpty()) {
				return this.resetTimer();
			}

			int fuel = this.getFuel();

			if (fuel == 0) {
				return this.resetTimer();
			}

			World world = this.getWorld();
			BlastingRecipe recipe = BossToolsRecipeTypes.BLASTING.findFirst(world, this);

			if (recipe == null) {
				return this.resetTimer();
			}

			ItemStack recipeOutput = recipe.getCraftingResult(this);

			if (this.canRecipeOperate(recipeOutput) == true) {
				int timer = this.getTimer() + 1;
				this.setTimer(timer);

				if (timer >= recipe.getCookTime()) {
					itemHandler.insertItem(SLOT_OUTPUT, recipeOutput, false);
					itemHandler.extractItem(SLOT_INGREDIENT, 1, false);
					this.setTimer(0);
					return true;
				} else if (this.getFuel() > 0) {
					this.setMaxTimer(recipe.getCookTime());
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		}

		public boolean udpateActivated() {
			boolean activated = this.getFuel() > 0;

			if (this.isActivated() != activated) {
				this.setActivated(activated);
				return true;
			}

			return false;
		}

		public IItemHandlerModifiable getItemHandler() {
			return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
		}

		public int getFuel() {
			return this.getTileData().getInt(KEY_FUEL);
		}

		public void setFuel(int fuel) {
			this.getTileData().putInt(KEY_FUEL, fuel);
		}

		public int getMaxFuel() {
			return this.getTileData().getInt(KEY_MAXFUEL);
		}

		public void setMaxFuel(int maxFuel) {
			this.getTileData().putInt(KEY_MAXFUEL, maxFuel);
		}

		public double getFuelRemainPercentage() {
			return this.getFuel() / (this.getMaxFuel() / 100.0D);
		}

		public int getTimer() {
			return this.getTileData().getInt(KEY_TIMER);
		}

		public void setTimer(int timer) {
			this.getTileData().putDouble(KEY_TIMER, timer);
		}

		public int getMaxTimer() {
			return this.getTileData().getInt(KEY_MAXTIMER);
		}

		public void setMaxTimer(int maxTimer) {
			this.getTileData().putDouble(KEY_MAXTIMER, maxTimer);
		}

		public double getTimerPercentage() {
			return this.getTimer() / (this.getMaxTimer() / 100.0D);
		}
		public boolean isActivated() {
			return this.getTileData().getBoolean(KEY_ACTIVATED);
		}

		public void setActivated(boolean activated) {
			this.getTileData().putBoolean(KEY_ACTIVATED, activated);
		}

	}
}
