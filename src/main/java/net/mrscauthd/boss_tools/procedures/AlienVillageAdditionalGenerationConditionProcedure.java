package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;

import java.util.Map;

public class AlienVillageAdditionalGenerationConditionProcedure {
	public static boolean executeProcedure(Map<String, Object> dependencies) {
		return ((BossToolsModVariables.Configalienhouse) == 1);
	}
}
