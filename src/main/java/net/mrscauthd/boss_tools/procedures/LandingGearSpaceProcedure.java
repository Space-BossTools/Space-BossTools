package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class LandingGearSpaceProcedure extends BossToolsModElements.ModElement {
	public LandingGearSpaceProcedure(BossToolsModElements instance) {
		super(instance, 168);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LandingGearSpace!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if ((entity.getRidingEntity()) instanceof LivingEntity)
				((LivingEntity) (entity.getRidingEntity()))
						.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 99999, (int) 3, (false), (false)));
			(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
			(entity.getRidingEntity()).getPersistentData().putDouble("Lander2", 1);
		}
	}
}
