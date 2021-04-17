package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class StarCrawlerOnEntityTickUpdateProcedure extends BossToolsModElements.ModElement {
	public StarCrawlerOnEntityTickUpdateProcedure(BossToolsModElements instance) {
		super(instance, 395);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure StarCrawlerOnEntityTickUpdate!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		// Config
		if (((BossToolsModVariables.StarCrawler) == 2)) {
			if (!entity.world.isRemote())
				entity.remove();
		}
	}
}
