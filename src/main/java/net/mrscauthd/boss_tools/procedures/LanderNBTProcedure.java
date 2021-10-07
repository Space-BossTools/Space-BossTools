package net.mrscauthd.boss_tools.procedures;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.inventory.ItemHandlerHelper2;

public class LanderNBTProcedure {
	public static final String LAST_ROCKET_INVENTORY = "LastRocketInventory";

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
	public static void executeProcedure(PlayerEntity player) {
		if (player.getRidingEntity() instanceof RocketAbstractEntity) {
			RocketAbstractEntity rocket = (RocketAbstractEntity) player.getRidingEntity();
			setLastRocketInventory(player, ItemHandlerHelper2.getStacks(rocket.getInventory()));
		}
	}

	public static void setLastRocketInventory(PlayerEntity player, NonNullList<ItemStack> stacks) {
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("size", stacks.size());
		compound.put("contents", ItemStackHelper.saveAllItems(new CompoundNBT(), stacks));
		player.getPersistentData().put(LAST_ROCKET_INVENTORY, compound);
	}

	public static NonNullList<ItemStack> getLastRocketInventory(PlayerEntity player) {
		CompoundNBT compound = player.getPersistentData().getCompound(LAST_ROCKET_INVENTORY);
		NonNullList<ItemStack> list = NonNullList.withSize(compound.getInt("size"), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound.getCompound("contents"), list);
		return list;
	}
}
