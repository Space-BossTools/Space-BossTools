package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.OrbitPlacerItem;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

public class Spacestationcheck1Procedure {
	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Spacestationcheck1!");
			return false;
		}
		Entity entity = (Entity) dependencies.get("entity");
		return ((entity instanceof PlayerEntity)
				? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(OrbitPlacerItem.block, (int) (1)))
				: false);
	}
}
