package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;
import net.mrscauthd.boss_tools.BossToolsModElements;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class AlienVillageAdditionalGenerationConditionProcedure extends BossToolsModElements.ModElement {
	public AlienVillageAdditionalGenerationConditionProcedure(BossToolsModElements instance) {
		super(instance, 396);
	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		return ((BossToolsModVariables.Configalienhouse) == 1);
	}
}
