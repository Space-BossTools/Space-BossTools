package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gui.OxygenBubbleDistributorGUI;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class OxygenBubbleDistributorBlock {

	public static final int ENERGY_PER_TICK = 1;
	public static final String KEY_RANGE = "range";
	public static final String KEY_WORKINGAREA_VISIBLE = "workingAreaVisible";

	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;

		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIAVATED, Boolean.valueOf(false)));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void addInformation(ItemStack itemstack, IBlockReader world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("\u00A77Make a Oxygen Bullet \u00A7c3x6"));
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

	public static class CustomTileEntity extends OxygenMakingTileEntity {

		public CustomTileEntity() {
			super(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR.get());
			this.setWorkingAreaVisible(false);
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public double getMaxRenderDistanceSquared() {
			return 256.0D;
		}

		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			return new AxisAlignedBB(560, 560, 560, -560, -560, -560);
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new OxygenBubbleDistributorGUI.GuiContainerMod(id, player, this);
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		@Override
		protected void tickProcessing() {
			super.tickProcessing();

			this.distribute();
		}

		private void distribute() {
			IOxygenStorage oxygenStorage = this.getOutputTank();
			double range = this.getRange();
			int oxygenUsing = this.getOxygenUsing(range);

			if (oxygenStorage.extractOxygen(oxygenUsing, true) == oxygenUsing && this.consumePowerForOperation() != null) {
				oxygenStorage.extractOxygen(oxygenUsing, false);

				this.spawnOxygenBubble(range);
			}

		}

		private void spawnOxygenBubble(double range) {
			World world = this.getWorld();
			List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, this.getWorkingArea(range), null);

			for (PlayerEntity player : players) {
				CompoundNBT persistentData = player.getPersistentData();
				persistentData.putBoolean("Oxygen_Bullet_Generator", true);
				persistentData.putDouble("timer_oxygen", 0);
			}

			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				Vector3d center = new AxisAlignedBB(this.getPos()).getCenter();
				serverWorld.spawnParticle(ParticleTypes.CLOUD, center.x, center.y + 0.5D, center.z, 1, 0.1D, 0.1D, 0.1D, 0.001D);
			}

			this.setProcessedInThisTick();
		}

		public int getOxygenUsing(double range) {
			return (int) range;
		}

		public int getRange() {
			return Math.max(this.getTileData().getInt(KEY_RANGE), 1);
		}

		public void setRange(int range) {
			range = Math.min(Math.max(range, 1), 15);

			if (this.getRange() != range) {
				this.getTileData().putInt(KEY_RANGE, range);
				this.markDirty();
			}
		}

		public boolean isWorkingAreaVisible() {
			return this.getTileData().getBoolean(KEY_WORKINGAREA_VISIBLE);
		}

		public void setWorkingAreaVisible(boolean visible) {
			if (this.isWorkingAreaVisible() != visible) {
				this.getTileData().putBoolean(KEY_WORKINGAREA_VISIBLE, visible);
				this.markDirty();
			}
		}

		public AxisAlignedBB getWorkingArea(double range) {
			return this.getWorkingArea(this.getPos(), range);
		}

		public AxisAlignedBB getWorkingArea(BlockPos pos, double range) {
			return new AxisAlignedBB(pos).grow(range).offset(0.0D, range, 0.0D);
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

	public static class ChangeRangeMessage {
		private BlockPos blockPos = BlockPos.ZERO;
		private boolean direction = false;

		public ChangeRangeMessage() {

		}

		public ChangeRangeMessage(BlockPos pos, boolean direction) {
			this.setBlockPos(pos);
			this.setDirection(direction);
		}

		public ChangeRangeMessage(PacketBuffer buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setDirection(buffer.readBoolean());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public boolean getDirection() {
			return this.direction;
		}

		public void setDirection(boolean direction) {
			this.direction = direction;
		}

		public static ChangeRangeMessage decode(PacketBuffer buffer) {
			return new ChangeRangeMessage(buffer);
		}

		public static void encode(ChangeRangeMessage message, PacketBuffer buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeBoolean(message.getDirection());
		}

		public static void handle(ChangeRangeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			Context context = contextSupplier.get();
			CustomTileEntity tileEntity = (CustomTileEntity) context.getSender().getServerWorld().getTileEntity(message.getBlockPos());
			int prev = tileEntity.getRange();
			int next = prev + (message.getDirection() ? +1 : -1);
			tileEntity.setRange(next);
			context.setPacketHandled(true);
		}
	}

	public static class ChangeWorkingAreaVisibleMessage {
		private BlockPos blockPos = BlockPos.ZERO;
		private boolean visible = false;

		public ChangeWorkingAreaVisibleMessage() {

		}

		public ChangeWorkingAreaVisibleMessage(BlockPos pos, boolean visible) {
			this.setBlockPos(pos);
			this.setVisible(visible);
		}

		public ChangeWorkingAreaVisibleMessage(PacketBuffer buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setVisible(buffer.readBoolean());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public boolean isVisible() {
			return this.visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public static ChangeWorkingAreaVisibleMessage decode(PacketBuffer buffer) {
			return new ChangeWorkingAreaVisibleMessage(buffer);
		}

		public static void encode(ChangeWorkingAreaVisibleMessage message, PacketBuffer buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeBoolean(message.isVisible());
		}

		public static void handle(ChangeWorkingAreaVisibleMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			Context context = contextSupplier.get();
			CustomTileEntity tileEntity = (CustomTileEntity) context.getSender().getServerWorld().getTileEntity(message.getBlockPos());
			tileEntity.setWorkingAreaVisible(message.isVisible());
			context.setPacketHandled(true);
		}
	}
}
