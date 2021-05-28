package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

public class LanderSpaceProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LanderSpace!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if ((((entity.getRidingEntity()).isOnGround()) == (false))) {
				if ((((entity.getRidingEntity()).getMotion().getY()) <= (-0.05))) {
					(entity.getRidingEntity()).setMotion(((entity.getRidingEntity()).getMotion().getX()),
							(((entity.getRidingEntity()).getMotion().getY()) * 0.85), ((entity.getRidingEntity()).getMotion().getZ()));
				}
				(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
				(entity.getRidingEntity()).getPersistentData().putDouble("Lander2", 1);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			(entity.getRidingEntity()).fallDistance = (float) (((((entity.getRidingEntity()).getMotion().getY()) * (-1)) * 4.5));
		}
	}
}
