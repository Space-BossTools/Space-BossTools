package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;
import net.mrscauthd.boss_tools.BossToolsModElements;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class MeteorAdditionalGenerationConditionProcedure extends BossToolsModElements.ModElement {
	public MeteorAdditionalGenerationConditionProcedure(BossToolsModElements instance) {
		super(instance, 397);
	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		return ((BossToolsModVariables.ConfigMeteor) == 1);
	}
}
