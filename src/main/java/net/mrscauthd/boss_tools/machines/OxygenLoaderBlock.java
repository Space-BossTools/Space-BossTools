package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;

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
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gui.OxygenLoaderGuiGui;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.OxygenUsingTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystem;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class OxygenLoaderBlock {
	public static final int SLOT_ITEM = 0;
	public static final int SLOT_ACTIVATING = 1;

	public static final int ENERGY_PER_TICK = 1;
	public static final int OXYGEN_PER_TICK = 8;

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
				return Collections.singletonList(new ItemStack(this));
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

	public static class CustomTileEntity extends OxygenUsingTileEntity {

		public CustomTileEntity() {
			super(ModInnet.OXYGEN_LOADER.get());
		}

		@Override
		protected int getInitialInventorySize() {
			return super.getInitialInventorySize() + 1;
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("oxygen_loader");
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new OxygenLoaderGuiGui.GuiContainerMod(id, player, this);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Oxygen Loader");
		}

		public int getItemSlot() {
			return SLOT_ITEM;
		}

		@Override
		public int getBaseOxygenForOperation() {
			return OXYGEN_PER_TICK;
		}

		public int getActivatingSlot() {
			return SLOT_ACTIVATING;
		}

		@Override
		protected boolean canUsingOxygen() {
			return this.getOxygenPowerSystem().getPowerForOperation() > 0;
		}

		@Override
		protected void onUsingOxygen(int consumedOxygen) {
			IOxygenStorage oxygenStorage = this.getItemOxygenStorage();

			if (oxygenStorage != null) {
				oxygenStorage.receiveOxygen(consumedOxygen, false);
			}
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		@Override
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			super.getSlotsForFace(direction, slots);
			slots.add(this.getItemSlot());
		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, Direction direction) {
			if (index == this.getItemSlot()) {
				return this.getItemOxygenStorage(stack) != null;
			}

			return super.canInsertItem(index, stack, direction);
		}

		@Override
		public int getPowerForOperation(PowerSystem powerSystem, int base) {
			if (powerSystem == this.getOxygenPowerSystem()) {
				IOxygenStorage oxygenStorage = this.getItemOxygenStorage();

				if (oxygenStorage == null) {
					return 0;
				} else {
					int storageRecivable = oxygenStorage.receiveOxygen(base, true);
					return MathHelper.clamp(powerSystem.getStored(), 1, storageRecivable);
				}
			}

			return super.getPowerForOperation(powerSystem, base);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			if (index == this.getItemSlot()) {
				IOxygenStorage oxygenStorage = this.getItemOxygenStorage(stack);

				if (oxygenStorage != null) {
					return oxygenStorage.receiveOxygen(1, true) == 0;
				}
			}

			return super.canExtractItem(index, stack, direction);
		}

		public IOxygenStorage getItemOxygenStorage() {
			return this.getItemOxygenStorage(this.getItemHandler().getStackInSlot(this.getItemSlot()));
		}

		@Override
		public boolean hasSpaceInOutput() {
			return this.hasSpaceInOutput(this.getItemOxygenStorage());
		}

		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.ACTIAVATED;
		}

		@Override
		protected void createPowerSystems(PowerSystemRegistry map) {
			super.createPowerSystems(map);

			map.put(new PowerSystemEnergyCommon(this) {
				@Override
				public int getBasePowerForOperation() {
					return CustomTileEntity.this.getBasePowerForOperation();
				}
			});
		}

		public int getBasePowerForOperation() {
			return ENERGY_PER_TICK;
		}
	}
}
