package net.mrscauthd.boss_tools.procedures;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.mrscauthd.boss_tools.item.NetheriteSpaceArmorItem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

public class DisableFireProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityAttacked(LivingAttackEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				if (((((entity instanceof PlayerEntity)
						? ((PlayerEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.helmet, (int) (1)).getItem())
						&& ((((entity instanceof PlayerEntity)
						? ((PlayerEntity) entity)
						.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.body, (int) (1)).getItem())
						&& ((((entity instanceof PlayerEntity)
						? ((PlayerEntity) entity)
						.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.legs, (int) (1)).getItem())
						&& (((entity instanceof PlayerEntity)
						? ((PlayerEntity) entity).getItemStackFromSlot(
						EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0))
						: ItemStack.EMPTY)
						.getItem() == new ItemStack(NetheriteSpaceArmorItem.boots, (int) (1)).getItem()))))) {
					if (event.getSource().isFireDamage()) {
						entity.forceFireTicks(0);
						event.setCanceled(true);
					}
				}
			}
		}

		@SubscribeEvent
		public static void disablerender(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				if (((((entity instanceof LivingEntity)
						? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.helmet, (int) (1)).getItem())
						&& ((((entity instanceof LivingEntity)
						? ((LivingEntity) entity)
						.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.body, (int) (1)).getItem())
						&& ((((entity instanceof LivingEntity)
						? ((LivingEntity) entity)
						.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1))
						: ItemStack.EMPTY).getItem() == new ItemStack(NetheriteSpaceArmorItem.legs, (int) (1)).getItem())
						&& (((entity instanceof LivingEntity)
						? ((LivingEntity) entity).getItemStackFromSlot(
						EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0))
						: ItemStack.EMPTY)
						.getItem() == new ItemStack(NetheriteSpaceArmorItem.boots, (int) (1)).getItem()))))) {
					event.player.forceFireTicks(0);
				}
			}
		}
	}
}
