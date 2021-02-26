package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.OrbitPlacerItem;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class SpaceStationGuiLogicProcedure extends BossToolsModElements.ModElement {
	public SpaceStationGuiLogicProcedure(BossToolsModElements instance) {
		super(instance, 388);
	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure SpaceStationGuiLogic!");
			return false;
		}
		Entity entity = (Entity) dependencies.get("entity");
		return (!((entity instanceof PlayerEntity)
				? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(OrbitPlacerItem.block, (int) (1)))
				: false));
	}
}
