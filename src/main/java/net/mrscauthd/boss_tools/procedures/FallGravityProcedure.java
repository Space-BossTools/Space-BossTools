package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Comparator;

public class FallGravityProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure FallGravity!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure FallGravity!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure FallGravity!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure FallGravity!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure FallGravity!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:moon"))))
				|| ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
						.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))))) {
			if ((((((PlayerEntity) entity).abilities.isFlying) == (false)) && (((((PlayerEntity) entity).isElytraFlying()) == (false))
					&& (((entity.isInWater()) == (false)) && (((entity.isInLava()) == (false))
							&& (((entity instanceof LivingEntity) ? (entity.hasNoGravity()) : false) == (false))))))) {
				entity.setMotion((entity.getMotion().getX()), (((((entity.getMotion().getY()) / 0.98) + 0.08) - 0.03) * 1),
						(entity.getMotion().getZ()));
				if (((entity.getMotion().getY()) <= (-0.1))) {
					entity.fallDistance = (float) (((entity.getMotion().getY()) * (-2.5)));
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof ItemEntity)) {
						if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
							if (((entityiterator.getMotion().getY()) <= (-0.1))) {
								if (((entityiterator.getPersistentData().getDouble("ItemGravity")) <= 1)) {
									entityiterator.getPersistentData().putDouble("ItemGravity", 2);
									entityiterator.setMotion((entityiterator.getMotion().getX()), ((entityiterator.getMotion().getY()) + 0.04),
											(entityiterator.getMotion().getZ()));
									entityiterator.getPersistentData().putDouble("ItemGravity", 0);
								}
							}
						}
					}
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((!(entityiterator instanceof PlayerEntity))) {
						if ((!(entityiterator instanceof LanderEntity.CustomEntity))) {
							if ((!(entityiterator instanceof RocketTier1Entity))) {
								if ((!(entityiterator instanceof RocketTier2Entity))) {
									if ((!(entityiterator instanceof RocketTier3Entity.CustomEntity))) {
										if ((!(entityiterator instanceof RoverEntity.CustomEntity))) {
											if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
												if (((entityiterator.getMotion().getY()) <= (-0.1))) {
													if (((entityiterator.getPersistentData().getDouble("EntityGravity")) <= 1)) {
														entityiterator.getPersistentData().putDouble("EntityGravity", 2);
														entityiterator.setMotion((entityiterator.getMotion().getX()),
																((entityiterator.getMotion().getY()) + 0.05), (entityiterator.getMotion().getZ()));
														entityiterator.fallDistance = (float) ((-0.2));
														entityiterator.getPersistentData().putDouble("EntityGravity", 0);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} // Orbit Gravity
		if ((((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:orbit_overworld"))))
				|| (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
						.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon"))))
						|| (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
								.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars"))))
								|| (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
										.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury"))))
										|| ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
												.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_venus"))))))))) {
			if ((((((PlayerEntity) entity).abilities.isFlying) == (false)) && (((((PlayerEntity) entity).isElytraFlying()) == (false))
					&& (((entity.isInWater()) == (false)) && (((entity.isInLava()) == (false))
							&& (((entity instanceof LivingEntity) ? (entity.hasNoGravity()) : false) == (false))))))) {
				entity.setMotion((entity.getMotion().getX()), (((((entity.getMotion().getY()) / 0.98) + 0.08) - 0.02) * 1),
						(entity.getMotion().getZ()));
				if (((entity.getMotion().getY()) <= (-0.1))) {
					entity.fallDistance = (float) (((entity.getMotion().getY()) * (-2.5)));
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof ItemEntity)) {
						if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
							if (((entityiterator.getMotion().getY()) <= (-0.1))) {
								if (((entityiterator.getPersistentData().getDouble("ItemGravity")) <= 1)) {
									entityiterator.getPersistentData().putDouble("ItemGravity", 2);
									entityiterator.setMotion((entityiterator.getMotion().getX()), ((entityiterator.getMotion().getY()) + 0.04),
											(entityiterator.getMotion().getZ()));
									entityiterator.getPersistentData().putDouble("ItemGravity", 0);
								}
							}
						}
					}
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((!(entityiterator instanceof PlayerEntity))) {
						if ((!(entityiterator instanceof LanderEntity.CustomEntity))) {
							if ((!(entityiterator instanceof RocketTier1Entity))) {
								if ((!(entityiterator instanceof RocketTier2Entity))) {
									if ((!(entityiterator instanceof RocketTier3Entity.CustomEntity))) {
										if ((!(entityiterator instanceof RoverEntity.CustomEntity))) {
											if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
												if (((entityiterator.getMotion().getY()) <= (-0.1))) {
													if (((entityiterator.getPersistentData().getDouble("EntityGravity")) <= 1)) {
														entityiterator.getPersistentData().putDouble("EntityGravity", 2);
														entityiterator.setMotion((entityiterator.getMotion().getX()),
																((entityiterator.getMotion().getY()) + 0.05), (entityiterator.getMotion().getZ()));
														entityiterator.fallDistance = (float) ((-0.2));
														entityiterator.getPersistentData().putDouble("EntityGravity", 0);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if ((((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:mars"))))
				|| ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
						.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus")))))) {
			if ((((((PlayerEntity) entity).abilities.isFlying) == (false)) && (((((PlayerEntity) entity).isElytraFlying()) == (false))
					&& (((entity.isInWater()) == (false)) && (((entity.isInLava()) == (false))
							&& (((entity instanceof LivingEntity) ? (entity.hasNoGravity()) : false) == (false))))))) {
				entity.setMotion((entity.getMotion().getX()), (((((entity.getMotion().getY()) / 0.98) + 0.08) - 0.04) * 1),
						(entity.getMotion().getZ()));
				if (((entity.getMotion().getY()) <= (-0.1))) {
					entity.fallDistance = (float) (((entity.getMotion().getY()) * (-2)));
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof ItemEntity)) {
						if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
							if (((entityiterator.getMotion().getY()) <= (-0.1))) {
								if (((entityiterator.getPersistentData().getDouble("ItemGravity")) <= 1)) {
									entityiterator.getPersistentData().putDouble("ItemGravity", 2);
									entityiterator.setMotion((entityiterator.getMotion().getX()), ((entityiterator.getMotion().getY()) + 0.04),
											(entityiterator.getMotion().getZ()));
									entityiterator.getPersistentData().putDouble("ItemGravity", 0);
								}
							}
						}
					}
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, y, z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((!(entityiterator instanceof PlayerEntity))) {
						if ((!(entityiterator instanceof LanderEntity.CustomEntity))) {
							if ((!(entityiterator instanceof RocketTier1Entity))) {
								if ((!(entityiterator instanceof RocketTier2Entity))) {
									if ((!(entityiterator instanceof RocketTier3Entity.CustomEntity))) {
										if ((!(entityiterator instanceof RoverEntity.CustomEntity))) {
											if ((((entityiterator instanceof LivingEntity) ? (entityiterator.hasNoGravity()) : false) == (false))) {
												if (((entityiterator.getMotion().getY()) <= (-0.1))) {
													if (((entityiterator.getPersistentData().getDouble("EntityGravity")) <= 1)) {
														entityiterator.getPersistentData().putDouble("EntityGravity", 2);
														entityiterator.setMotion((entityiterator.getMotion().getX()),
																((entityiterator.getMotion().getY()) + 0.05), (entityiterator.getMotion().getZ()));
														entityiterator.fallDistance = (float) ((-0.2));
														entityiterator.getPersistentData().putDouble("EntityGravity", 0);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
