package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;

import java.util.Map;

public class MeteorAdditionalGenerationConditionProcedure {
	public static boolean executeProcedure(Map<String, Object> dependencies) {
		return ((BossToolsModVariables.ConfigMeteor) == 1);
	}
}
