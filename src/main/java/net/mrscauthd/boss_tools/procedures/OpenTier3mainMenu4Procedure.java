package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class OpenTier3mainMenu4Procedure extends BossToolsModElements.ModElement {
	public OpenTier3mainMenu4Procedure(BossToolsModElements instance) {
		super(instance, 655);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OpenTier3mainMenu4!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("Tier_3_open_main_menu_back", 0);
		entity.getPersistentData().putDouble("Tier_3_open_main_menu", 0);
		entity.getPersistentData().putDouble("Tier_3_space_station_open", 0);
		entity.getPersistentData().putDouble("Tier_3_open_main_menu_2", 0);
		entity.getPersistentData().putDouble("Tier_3_open_main_menu_3", 0);
		entity.getPersistentData().putDouble("Tier_3_open_main_menu_4", 1);
	}
}
