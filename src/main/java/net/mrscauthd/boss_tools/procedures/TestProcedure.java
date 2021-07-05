package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.Comparator;

public class TestProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Test!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure Test!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure Test!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure Test!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure Test!");
			return;
		}
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		{
			List<Entity> _entfound = world
					.getEntitiesWithinAABB(Entity.class,
							new AxisAlignedBB((Math.floor(x)) - (1 / 2d), (Math.floor(y)) - (1 / 2d), (Math.floor(z)) - (1 / 2d),
									(Math.floor(x)) + (1 / 2d), (Math.floor(y)) + (1 / 2d), (Math.floor(z)) + (1 / 2d)),
							null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
						}
					}.compareDistOf((Math.floor(x)), (Math.floor(y)), (Math.floor(z)))).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (((!(sourceentity instanceof RocketEntity.CustomEntity)) || ((!(sourceentity instanceof RocketTier2Entity.CustomEntity))
						|| ((!(sourceentity instanceof RocketTier3Entity.CustomEntity))
								|| ((!(sourceentity instanceof LandingGearEntity.CustomEntity))
										|| (!(sourceentity instanceof RoverEntity.CustomEntity))))))) {
					System.out.println("");
				}
			}
		}
	}
}
