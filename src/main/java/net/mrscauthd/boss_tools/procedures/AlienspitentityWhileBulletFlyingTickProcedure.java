package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.world.IWorld;
import net.minecraft.particles.ParticleTypes;

import java.util.Map;

public class AlienspitentityWhileBulletFlyingTickProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure AlienspitentityWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure AlienspitentityWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure AlienspitentityWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure AlienspitentityWhileBulletFlyingTick!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		world.addParticle(ParticleTypes.SPIT, x, y, z, 0, 0.001, 0);
		world.addParticle(ParticleTypes.ITEM_SNOWBALL, x, y, z, 0, 0.001, 0);
	}
}
