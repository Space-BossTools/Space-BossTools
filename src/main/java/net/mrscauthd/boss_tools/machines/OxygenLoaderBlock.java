
package net.mrscauthd.boss_tools.machines;

import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.armor.oxygensystem.CapabilityOxygen;
import net.mrscauthd.boss_tools.armor.oxygensystem.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.machines.machinetileentities.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.machinetileentities.PowerSystem;
import net.mrscauthd.boss_tools.machines.machinetileentities.PowerSystemCommonEnergy;
import net.mrscauthd.boss_tools.gui.OxygenLoaderGuiGui;

import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.common.ToolType;

import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.network.PacketBuffer;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Collections;

import io.netty.buffer.Unpooled;

public class OxygenLoaderBlock {
	public static final int SLOT_ITEM = 0;
	public static final int SLOT_ACTIVATING = 1;
	
	public static final int OXYGEN_PER_TICK = 8;

	public static final String KEY_ACTIVATINGTIME = "activatingTime";
	public static final String KEY_MAXACTIVATINGTIME = "maxActivatingTime";

	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;
		public static double energy = 0;
		public static boolean itemcheck = false;

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
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("Oxygen Loader");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new OxygenLoaderGuiGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(pos));
					}
				}, pos);
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

	public static class CustomTileEntity extends AbstractMachineTileEntity {

		public CustomTileEntity() {
			super(ModInnet.OXYGEN_LOADER.get());
		}

		@Override
		protected int getInitialInventorySize() {
			return super.getInitialInventorySize() + 2;
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("oxygen_loader");
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new OxygenLoaderGuiGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Oxygen Loader");
		}
		
		public BossToolsRecipeType<? extends OxygenMakingRecipe> getRecipeType() {
			return BossToolsRecipeTypes.OXYGENMAKING;
		}
		
		public int getActivatingTime(ItemStack itemStack) {
			if (itemStack == null || itemStack.isEmpty()) {
				return -1;
			}
			
			int recipeSlot = OxygenMakingRecipe.SLOT_INGREDIENT;
			Inventory virtualInventory = new Inventory(recipeSlot + 1);
			virtualInventory.setInventorySlotContents(recipeSlot, itemStack);

			OxygenMakingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), virtualInventory);
			return recipe != null ? recipe.getActivaingTime() : -1;
		}
		
		public int getItemSlot() {
			return SLOT_ITEM;
		}
		
		public int getActivatingSlot() {
			return SLOT_ACTIVATING;
		}

		public int getOxygenPerTick() {
			return OXYGEN_PER_TICK;
		}

		@Override
		protected void tickProcessing() {
			int activatingTime = this.getActivatingTime();
			int activatingSlot = this.getActivatingSlot();
			IOxygenStorage oxygenStorage = this.getItemOxygenStorage();
			
			if (activatingTime >= 1 && this.hasSpaceInOutput(oxygenStorage) && this.isPowerEnoughAndConsume()) {
				activatingTime--;
				oxygenStorage.receiveOxygen(this.getOxygenPerTick(), false);
				this.setActivatingTime(activatingTime);
				this.markDirty();
				this.setProcessedInThisTick();
			}

			if (activatingTime < 1 && this.hasSpaceInOutput(oxygenStorage) && this.isPowerEnoughForOperation()) {
				IItemHandlerModifiable itemHandler = this.getItemHandler();
				ItemStack extra = itemHandler.getStackInSlot(activatingSlot);
				
				if (!extra.isEmpty()) {
					int newActivatingTime = this.getActivatingTime(extra);
					
					if (newActivatingTime > 0) {
						itemHandler.extractItem(activatingSlot, 1, false);
						this.setMaxActivatingTime(activatingTime + newActivatingTime);
						this.setActivatingTime(this.getMaxActivatingTime());
						this.markDirty();
					}
				}
			}
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
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			super.getSlotsForFace(direction, slots);
			slots.add(this.getItemSlot());
			slots.add(this.getActivatingSlot());
		}
		
		@Override
		public boolean canInsertItem(int index, ItemStack stack, Direction direction) {
			if (super.canInsertItem(index, stack, direction)) {
				return true;
			}
			
			if (index == this.getItemSlot()) {
				return this.getItemOxygenStorage(stack) != null;
			}
			else if (index == this.getActivatingSlot()) {
				return this.getActivatingTime(stack) > 0;
			}
			
			return false;
		}
		
		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			if (super.canExtractItem(index, stack, direction)) {
				return true;
			}

			if (index == this.getItemSlot()) {
				IOxygenStorage oxygenStorage = this.getItemOxygenStorage(stack);
				
				if (oxygenStorage != null) {
					return oxygenStorage.receiveOxygen(this.getOxygenPerTick(), true) == 0;
				}
			}
			
			return false;
		}

		public IOxygenStorage getItemOxygenStorage(ItemStack itemStack) {
			return !itemStack.isEmpty() ? itemStack.getCapability(CapabilityOxygen.OXYGEN).orElse(null) : null;
		}

		public IOxygenStorage getItemOxygenStorage() {
			ItemStack itemStack = this.getItemHandler().getStackInSlot(this.getItemSlot());
			return !itemStack.isEmpty() ? itemStack.getCapability(CapabilityOxygen.OXYGEN).orElse(null) : null;
		}

		@Override
		public boolean hasSpaceInOutput() {
			return this.hasSpaceInOutput(this.getItemOxygenStorage());
		}

		public boolean hasSpaceInOutput(IOxygenStorage oxygenStorage) {
			return oxygenStorage != null ? oxygenStorage.getOxygenStored() < oxygenStorage.getMaxOxygenStored() : false;
		}

		public int getActivatingTime() {
			return this.getTileData().getInt(KEY_ACTIVATINGTIME);
		}

		public void setActivatingTime(int activatingTime) {
			activatingTime = Math.max(Math.min(activatingTime, this.getMaxActivatingTime()), 0);

			if (this.getActivatingTime() != activatingTime) {
				this.getTileData().putInt(KEY_ACTIVATINGTIME, activatingTime);
				this.markDirty();
			}
		}

		public int getMaxActivatingTime() {
			return this.getTileData().getInt(KEY_MAXACTIVATINGTIME);
		}

		public void setMaxActivatingTime(int maxActivatingTime) {
			maxActivatingTime = Math.max(maxActivatingTime, 0);
			
			if (this.getMaxActivatingTime() != maxActivatingTime) {
				this.getTileData().putInt(KEY_MAXACTIVATINGTIME, maxActivatingTime);
				this.markDirty();
			}
		}
		
		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.ACTIAVATED;
		}

	}

}
