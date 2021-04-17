package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class OpenTier2SpaceStationMenu2Procedure extends BossToolsModElements.ModElement {
	public OpenTier2SpaceStationMenu2Procedure(BossToolsModElements instance) {
		super(instance, 629);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OpenTier2SpaceStationMenu2!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_back", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_2", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_3", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_4", 0);
		entity.getPersistentData().putDouble("Tier_2_space_station_open", 2);
	}
}
