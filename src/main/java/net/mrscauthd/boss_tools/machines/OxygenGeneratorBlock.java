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
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
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
import net.minecraft.tileentity.TileEntityType;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ObjectHolder;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.gui.OxygenBulletGeneratorGUIGui;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.OxygenUsingTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

@BossToolsModElements.ModElement.Tag
public class OxygenGeneratorBlock extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:oxygen_bullet_generator")
	public static final Block block = null;
	@ObjectHolder("boss_tools:oxygen_bullet_generator")
	public static final TileEntityType<CustomTileEntity> tileEntityType = null;

	public static final int SLOT_ACTIVATING = 0;
	public static final int ENERGY_PER_TICK = 1;
	public static final int OXYGEN_PER_TICK = 8;
	public static final String KEY_TIMER = "timer";
	public static final String KEY_LARGE = "large";

	/**
	 * Interval Ticks, 2 = every 2 ticks
	 */
	public static final int MAX_TIMER = 2;

	public OxygenGeneratorBlock(BossToolsModElements instance) {
		super(instance, 76);
		FMLJavaModLoadingContext.get().getModEventBus().register(new TileEntityRegisterHandler());
		BossToolsMod.addNetworkMessage(SetLargeMessage.class, SetLargeMessage::encode, SetLargeMessage::decode, SetLargeMessage::handle);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(BossToolsItemGroups.tab_machines)).setRegistryName(block.getRegistryName()));
	}

	private static class TileEntityRegisterHandler {
		@SubscribeEvent
		public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("oxygen_bullet_generator"));
		}
	}

	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;

		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIAVATED, Boolean.valueOf(false)));
			setRegistryName("oxygen_bullet_generator");
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

	public static class CustomTileEntity extends OxygenUsingTileEntity {

		protected CustomTileEntity() {
			super(tileEntityType);
		}

		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			return new AxisAlignedBB(-160, -160, -160, 160, 160, 160);
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("oxygen_bullet_generator");
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new OxygenBulletGeneratorGUIGui.GuiContainerMod(id, player, this);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Oxygen Bullet Generator");
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		@Override
		protected void tickProcessing() {
			super.tickProcessing();

			this.setTimer(this.getTimer() + 1);
		}

		@Override
		public int getActivatingSlot() {
			return SLOT_ACTIVATING;
		}

		/**
		 * timer will 0, 1, 0, 1
		 */
		@Override
		protected boolean canUsingOxygen() {
			return this.getTimer() >= MAX_TIMER;
		}

		@Override
		protected void onUsingOxygen(int consumed) {
			this.setTimer(0);

			World world = this.getWorld();
			BlockPos pos = this.getPos();
			double range = this.getRange();
			List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).grow(range), null);

			for (PlayerEntity player : players) {
				CompoundNBT persistentData = player.getPersistentData();
				persistentData.putBoolean("Oxygen_Bullet_Generator", true);
				persistentData.putDouble("timer_oxygen", 0);
			}

			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				Vector3d center = new AxisAlignedBB(pos).getCenter();
				serverWorld.spawnParticle(ParticleTypes.CLOUD, center.x, center.y + 0.5D, center.z, 1, 0.1D, 0.1D, 0.1D, 0.001D);
			}

		}

		@Override
		protected boolean canActivated() {
			return this.isPowerEnoughForOperation();
		}

		@Override
		public boolean hasSpaceInOutput() {
			return true;
		}

		public int getTimer() {
			return this.getTileData().getInt(KEY_TIMER);
		}

		public void setTimer(int timer) {
			timer = Math.max(timer, 0);

			if (this.getTimer() != timer) {
				this.getTileData().putInt(KEY_TIMER, timer);
				this.markDirty();
			}
		}

		public double getRange() {
			return this.isLarge() ? 6.0D : 3.0D;
		}

		public boolean isLarge() {
			return this.getTileData().getBoolean(KEY_LARGE);
		}

		public void setLarge(boolean large) {
			if (this.isLarge() != large) {
				this.getTileData().putBoolean(KEY_LARGE, large);
				this.markDirty();
			}
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

		@Override
		public int getBaseOxygenForOperation() {
			return OXYGEN_PER_TICK;
		}
	}

	public static class SetLargeMessage {

		private BlockPos blockPos = BlockPos.ZERO;
		private boolean large = false;

		public SetLargeMessage() {

		}

		public SetLargeMessage(BlockPos pos, boolean large) {
			this.setBlockPos(pos);
			this.setLarge(large);
		}

		public SetLargeMessage(PacketBuffer buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setLarge(buffer.readBoolean());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public boolean isLarge() {
			return this.large;
		}

		public void setLarge(boolean large) {
			this.large = large;
		}

		public static SetLargeMessage decode(PacketBuffer buffer) {
			return new SetLargeMessage(buffer);
		}

		public static void encode(SetLargeMessage message, PacketBuffer buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeBoolean(message.isLarge());
		}

		public static void handle(SetLargeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			Context context = contextSupplier.get();
			CustomTileEntity tileEntity = (CustomTileEntity) context.getSender().getServerWorld().getTileEntity(message.getBlockPos());
			tileEntity.setLarge(message.isLarge());
			context.setPacketHandled(true);
		}
	}
}
