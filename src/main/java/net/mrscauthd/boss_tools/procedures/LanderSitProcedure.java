package net.mrscauthd.boss_tools.procedures;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;

public class LanderSitProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				executeProcedure(event.player);
			}
		}
	}

	// TODO : Should be rework
	public static Item getRocketItem(CompoundNBT compound) {
		double rocketTier = compound.getDouble("Landersit");

		if (rocketTier == 1) {
			return Tier1RocketItemItem.block;
		} else if (rocketTier == 2) {
			return Tier2RocketItemItem.block;
		} else if (rocketTier == 3) {
			return Tier3RocketItemItem.block;
		}

		return Items.AIR;
	}

	public static void executeProcedure(PlayerEntity player) {
		CompoundNBT persistentData = player.getPersistentData();
		double landerSpawn = persistentData.getDouble("LanderSpawn");

		if (landerSpawn == 1) {
			LanderEntity.CustomEntity lander = new LanderEntity.CustomEntity(LanderEntity.entity, player.world);
			lander.setPositionAndUpdate(player.getPosX(), player.getPosY(), player.getPosZ());
			lander.rotationYaw = 0.0F;
			lander.setRenderYawOffset(lander.rotationYaw);
			lander.prevRotationYaw = lander.rotationYaw;
			lander.prevRotationYawHead = lander.rotationYaw;

			IItemHandlerModifiable landerInventory = lander.getInventory();
			ItemHandlerHelper.insertItem(landerInventory, new ItemStack(getRocketItem(persistentData)), false);

			for (ItemStack itemStack : LanderNBTProcedure.getLastRocketInventory(player)) {
				ItemHandlerHelper.insertItem(landerInventory, itemStack, false);
			}

			player.world.addEntity(lander);
			player.startRiding(lander);

			persistentData.putDouble("LanderSpawn", 0);
			persistentData.putDouble("Landersit", 0);
		}
	}
}
