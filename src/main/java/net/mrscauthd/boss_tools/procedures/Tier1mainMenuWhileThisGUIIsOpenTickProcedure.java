package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class Tier1mainMenuWhileThisGUIIsOpenTickProcedure extends BossToolsModElements.ModElement {
	public Tier1mainMenuWhileThisGUIIsOpenTickProcedure(BossToolsModElements instance) {
		super(instance, 670);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Tier1mainMenuWhileThisGUIIsOpenTick!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("Oxygen_Bullet_Generator", 3);
	}
}
