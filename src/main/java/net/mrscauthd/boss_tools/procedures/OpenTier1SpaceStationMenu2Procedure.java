package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

public class OpenTier1SpaceStationMenu2Procedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OpenTier1SpaceStationMenu2!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_back", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu", 0);
		entity.getPersistentData().putDouble("Tier_1_open_main_menu_2", 0);
		entity.getPersistentData().putDouble("Tier_1_space_station_open", 2);
	}
}
