package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class RocketOverlyYDisplayOverlayIngameProcedure extends BossToolsModElements.ModElement {
	public RocketOverlyYDisplayOverlayIngameProcedure(BossToolsModElements instance) {
		super(instance, 442);
	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketOverlyYDisplayOverlayIngame!");
			return false;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure RocketOverlyYDisplayOverlayIngame!");
			return false;
		}
		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");
		if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:moon")))))) {
			if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:mars")))))) {
				if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
						.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_overworld")))))) {
					if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))))) {
						if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
								.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon")))))) {
							if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
									.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars")))))) {
								if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
										.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury")))))) {
									if ((((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)
											|| (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)
													|| ((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)))) {
										return (true);
									}
								}
							}
						}
					}
				}
			}
		}
		return (false);
	}
}
