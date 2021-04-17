package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class RotationBReleasedProcedure extends BossToolsModElements.ModElement {
	public RotationBReleasedProcedure(BossToolsModElements instance) {
		super(instance, 325);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RotationBReleased!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)) {
			(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 0);
		}
		if (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)) {
			(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 0);
		}
		if (((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)) {
			(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 0);
		}
	}
}
