package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.energy.CapabilityEnergy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.block.BlockState;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class OxygenDisplayProcedure extends BossToolsModElements.ModElement {
	public OxygenDisplayProcedure(BossToolsModElements instance) {
		super(instance, 161);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure OxygenDisplay!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure OxygenDisplay!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure OxygenDisplay!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure OxygenDisplay!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double firehaight = 0;
		if (((new Object() {
			public double getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getDouble(tag);
				return -1;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "fuel")) > 0)) {
			if (((new Object() {
				public int getEnergyStored(IWorld world, BlockPos pos) {
					AtomicInteger _retval = new AtomicInteger(0);
					TileEntity _ent = world.getTileEntity(pos);
					if (_ent != null)
						_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
					return _retval.get();
				}
			}.getEnergyStored(world, new BlockPos((int) x, (int) y, (int) z))) >= 1)) {
				firehaight = (double) (((new Object() {
					public double getValue(IWorld world, BlockPos pos, String tag) {
						TileEntity tileEntity = world.getTileEntity(pos);
						if (tileEntity != null)
							return tileEntity.getTileData().getDouble(tag);
						return -1;
					}
				}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "fuelRemaining")) / 100) * 0.0625);
				if (((new Object() {
					public Direction getDirection(BlockPos pos) {
						try {
							BlockState _bs = world.getBlockState(pos);
							DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
							if (property != null)
								return _bs.get(property);
							return Direction.getFacingFromAxisDirection(
									_bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
									Direction.AxisDirection.POSITIVE);
						} catch (Exception e) {
							return Direction.NORTH;
						}
					}
				}.getDirection(new BlockPos((int) x, (int) y, (int) z))) == Direction.NORTH)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, (x + 0.5), (y + 0.2), (z - 0.1), (int) 1, (Math.random() * 0.01),
								(firehaight), (Math.random() * 0.01), (Math.random() * 0.01));
					}
				} else if (((new Object() {
					public Direction getDirection(BlockPos pos) {
						try {
							BlockState _bs = world.getBlockState(pos);
							DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
							if (property != null)
								return _bs.get(property);
							return Direction.getFacingFromAxisDirection(
									_bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
									Direction.AxisDirection.POSITIVE);
						} catch (Exception e) {
							return Direction.NORTH;
						}
					}
				}.getDirection(new BlockPos((int) x, (int) y, (int) z))) == Direction.SOUTH)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, (x + 0.5), (y + 0.2), (z + 1.1), (int) 1, (Math.random() * 0.01),
								(firehaight), (Math.random() * 0.01), (Math.random() * 0.01));
					}
				} else if (((new Object() {
					public Direction getDirection(BlockPos pos) {
						try {
							BlockState _bs = world.getBlockState(pos);
							DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
							if (property != null)
								return _bs.get(property);
							return Direction.getFacingFromAxisDirection(
									_bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
									Direction.AxisDirection.POSITIVE);
						} catch (Exception e) {
							return Direction.NORTH;
						}
					}
				}.getDirection(new BlockPos((int) x, (int) y, (int) z))) == Direction.WEST)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, (x - 0.1), (y + 0.2), (z + 0.5), (int) 1, (Math.random() * 0.01),
								(firehaight), (Math.random() * 0.01), (Math.random() * 0.01));
					}
				} else if (((new Object() {
					public Direction getDirection(BlockPos pos) {
						try {
							BlockState _bs = world.getBlockState(pos);
							DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
							if (property != null)
								return _bs.get(property);
							return Direction.getFacingFromAxisDirection(
									_bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
									Direction.AxisDirection.POSITIVE);
						} catch (Exception e) {
							return Direction.NORTH;
						}
					}
				}.getDirection(new BlockPos((int) x, (int) y, (int) z))) == Direction.EAST)) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, (x + 1.1), (y + 0.2), (z + 0.5), (int) 1, (Math.random() * 0.01),
								(firehaight), (Math.random() * 0.01), (Math.random() * 0.01));
					}
				}
				if (world instanceof World && !world.isRemote()) {
					((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.furnace.fire_crackle")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1);
				} else {
					((World) world).playSound(x, y, z,
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.furnace.fire_crackle")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
				}
			}
		}
	}
}
