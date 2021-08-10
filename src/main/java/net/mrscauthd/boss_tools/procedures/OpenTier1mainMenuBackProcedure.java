package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

public class OpenTier1mainMenuBackProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OpenTier1mainMenuBack!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_2", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_3", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_4", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_5", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_back", 1);
	}
}
