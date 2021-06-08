
package net.mrscauthd.boss_tools.block;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Direction;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.StateContainer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockItem;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import javax.annotation.Nullable;

import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;
import java.util.List;
import java.util.Collections;

@BossToolsModElements.ModElement.Tag
public class EnergyCableBaseBlock extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:energy_cable")
	public static final Block block = null;
	@ObjectHolder("boss_tools:energy_cable")
	public static final TileEntityType<CustomTileEntity> tileEntityType = null;
	public EnergyCableBaseBlock(BossToolsModElements instance) {
		super(instance, 10);
		FMLJavaModLoadingContext.get().getModEventBus().register(new TileEntityRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(BossToolsItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	private static class TileEntityRegisterHandler {
		@SubscribeEvent
		public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("energy_cable"));
		}
	}
	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
	}
	public static class CustomBlock extends Block implements IWaterLoggable {
		public static final BooleanProperty NORTH = BooleanProperty.create("north");
		public static final BooleanProperty SOUTH = BooleanProperty.create("south");
		public static final BooleanProperty EAST = BooleanProperty.create("east");
		public static final BooleanProperty WEST = BooleanProperty.create("west");
		public static final BooleanProperty DOWN = BooleanProperty.create("down");
		public static final BooleanProperty UP = BooleanProperty.create("up");
		public static final BooleanProperty[] CONNECTIONS = new BooleanProperty[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
		public CustomBlock() {
			super(Block.Properties.create(Material.WOOD).sound(SoundType.STONE).hardnessAndResistance(0.5f, 10f).setLightLevel(s -> 0).notSolid()
					.setOpaque((bs, br, bp) -> false));
			this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false))
					.with(EAST, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(DOWN, Boolean.valueOf(false))
					.with(UP, Boolean.valueOf(false)).with(WATERLOGGED, false));
			setRegistryName("energy_cable");
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void addInformation(ItemStack itemstack, IBlockReader world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("\u00A79Capacity: \u00A771 FE/t"));
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
			Vector3d offset = state.getOffset(world, pos);
			return VoxelShapes.or(makeCuboidShape(4.5, 4.5, 4.5, 11.5, 11.5, 11.5)).withOffset(offset.x, offset.y, offset.z);
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
			return this.getDefaultState().with(WATERLOGGED, flag);
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(WATERLOGGED, NORTH, SOUTH, EAST, WEST, UP, DOWN);
		}

		@Override
		public FluidState getFluidState(BlockState state) {
			return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		}

		@Override
		public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos,
				BlockPos facingPos) {
			if (state.get(WATERLOGGED)) {
				world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
			}
			return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
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
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			// tick
			double energy = 0;
			double Energy2 = 0;
			double Energy3 = 0;
			double Energy4 = 0;
			double Energy5 = 0;
			double Energy6 = 0;
			double math = 0;
			double main = 0;
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.UP).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) x, (int) (y + 1), (int) z)))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("up", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("upe", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("up", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("upe", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) x, (int) (y - 1), (int) z)))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("down", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("downe", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("down", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("downe", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) (x + 1), (int) y, (int) z)))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("north", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("northe", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("north", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("northe", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) (x - 1), (int) y, (int) z)))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("south", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("southe", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("south", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("southe", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) x, (int) y, (int) (z + 1))))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("east", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("easte", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("east", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("easte", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if ((new Object() {
				public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
					AtomicBoolean _retval = new AtomicBoolean(false);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> _retval.set(capability.canReceive()));
					return _retval.get();
				}
			}.canReceiveEnergy(world, new BlockPos((int) x, (int) y, (int) (z - 1))))) {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("west", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("weste", 1);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("west", (false));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("weste", 0);
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			}
			if (!world.isRemote()) {
				BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
				TileEntity _tileEntity = world.getTileEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_tileEntity != null)
					_tileEntity.getTileData().putDouble("energyall", ((new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "upe")) + ((new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "downe")) + ((new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "northe")) + ((new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "southe")) + ((new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "easte")) + (new Object() {
						public double getValue(IWorld world, BlockPos pos, String tag) {
							TileEntity tileEntity = world.getTileEntity(pos);
							if (tileEntity != null)
								return tileEntity.getTileData().getDouble(tag);
							return -1;
						}
					}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "weste"))))))));
				if (world instanceof World)
					((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
			} // down and up
			if (((new Object() {
				public int getEnergyStored(IWorld world, BlockPos pos) {
					AtomicInteger _retval = new AtomicInteger(0);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
					return _retval.get();
				}
			}.getEnergyStored(world, new BlockPos((int) x, (int) y, (int) z))) >= (new Object() {
				public double getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getDouble(tag);
					return -1;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "energyall")))) {
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.UP).ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) x, (int) (y + 1), (int) z)))) {
					energy = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					energy = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.UP)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) x, (int) (y + 1), (int) z), (int) (energy)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (energy);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) (y + 1), (int) z));
						int _amount = (int) (energy);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.UP)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				}
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) x, (int) (y - 1), (int) z)))) {
					Energy2 = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					Energy2 = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) x, (int) (y - 1), (int) z), (int) (Energy2)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (Energy2);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) (y - 1), (int) z));
						int _amount = (int) (Energy2);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				} // side
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
									.ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) (x + 1), (int) y, (int) z)))) {
					Energy3 = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					Energy3 = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) (x + 1), (int) y, (int) z), (int) (Energy3)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (Energy3);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) (x + 1), (int) y, (int) z));
						int _amount = (int) (Energy3);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				}
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
									.ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) (x - 1), (int) y, (int) z)))) {
					Energy4 = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					Energy4 = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) (x - 1), (int) y, (int) z), (int) (Energy4)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (Energy4);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) (x - 1), (int) y, (int) z));
						int _amount = (int) (Energy4);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				} // side
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
									.ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) x, (int) y, (int) (z + 1))))) {
					Energy5 = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					Energy5 = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) x, (int) y, (int) (z + 1)), (int) (Energy5)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (Energy5);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) (z + 1)));
						int _amount = (int) (Energy5);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				}
				if ((new Object() {
					public boolean canReceiveEnergy(IWorld world, BlockPos pos) {
						AtomicBoolean _retval = new AtomicBoolean(false);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
									.ifPresent(capability -> _retval.set(capability.canReceive()));
						return _retval.get();
					}
				}.canReceiveEnergy(world, new BlockPos((int) x, (int) y, (int) (z - 1))))) {
					Energy6 = (double) (new Object() {
						public int extractEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, null)
										.ifPresent(capability -> _retval.set(capability.extractEnergy(_amount, true)));
							return _retval.get();
						}
					}.extractEnergySimulate(world, new BlockPos((int) x, (int) y, (int) z), (int) 1));
					Energy6 = (double) (new Object() {
						public int receiveEnergySimulate(IWorld world, BlockPos pos, int _amount) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null)
								_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
										.ifPresent(capability -> _retval.set(capability.receiveEnergy(_amount, true)));
							return _retval.get();
						}
					}.receiveEnergySimulate(world, new BlockPos((int) x, (int) y, (int) (z - 1)), (int) (Energy6)));
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
						int _amount = (int) (Energy6);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
					}
					{
						TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) (z - 1)));
						int _amount = (int) (Energy6);
						if (_ent != null)
							_ent.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH)
									.ifPresent(capability -> capability.receiveEnergy(_amount, false));
					}
				}
			}
			// tick
			// world.setBlockState(pos, state.with(DOWN, Boolean.valueOf(true)), 3);
			// world.setBlockState(pos, state.with(UP, Boolean.valueOf(true)), 3);
			world.setBlockState(pos, state.with(DOWN, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "down"))).with(UP, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "up"))).with(NORTH, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "north"))).with(SOUTH, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "south"))).with(EAST, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "east"))).with(WEST, (new Object() {
				public boolean getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getBoolean(tag);
					return false;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "west"))));
			// Cable tick
			super.tick(state, world, pos, random);
			// super.tick(state., world, pos, random);
			{
				// Map<String, Object> $_dependencies = new HashMap<>();
				// $_dependencies.put("x", x);
				// $_dependencies.put("y", y);
				// $_dependencies.put("z", z);
				// $_dependencies.put("world", world);
				// EnergyCableBaseUpdateTickProcedure.executeProcedure($_dependencies);
			}
			world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), this, 1);
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
			if (compound.get("energyStorage") != null)
				CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.get("energyStorage"));
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);
			if (!this.checkLootAndWrite(compound)) {
				ItemStackHelper.saveAllItems(compound, this.stacks);
			}
			compound.put("energyStorage", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
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
			return new StringTextComponent("energy_cable");
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return ChestContainer.createGeneric9X3(id, player, this);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Energy Cable");
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

		@Override
		public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
			return this.isItemValidForSlot(index, stack);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			return true;
		}
		private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
		private final EnergyStorage energyStorage = new EnergyStorage(20, 200, 200, 0) {
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
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return handlers[facing.ordinal()].cast();
			if (!this.removed && capability == CapabilityEnergy.ENERGY)
				return LazyOptional.of(() -> energyStorage).cast();
			return super.getCapability(capability, facing);
		}

		@Override
		public void remove() {
			super.remove();
			for (LazyOptional<? extends IItemHandler> handler : handlers)
				handler.invalidate();
		}
	}
}
