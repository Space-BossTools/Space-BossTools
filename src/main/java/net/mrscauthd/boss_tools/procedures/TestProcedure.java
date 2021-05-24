package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

public class TestProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Test!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)
				|| (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)
						|| ((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)))) {
			(entity.getRidingEntity()).rotationYaw = (float) ((((entity.getRidingEntity()).rotationYaw) - 1));
			(entity.getRidingEntity()).setRenderYawOffset((entity.getRidingEntity()).rotationYaw);
			(entity.getRidingEntity()).prevRotationYaw = (entity.getRidingEntity()).rotationYaw;
			if ((entity.getRidingEntity()) instanceof LivingEntity) {
				((LivingEntity) (entity.getRidingEntity())).prevRenderYawOffset = (entity.getRidingEntity()).rotationYaw;
			}
		}
	}
}
