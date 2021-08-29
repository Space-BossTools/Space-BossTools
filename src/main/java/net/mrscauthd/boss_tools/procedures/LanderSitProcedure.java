package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;

public class LanderSitProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LanderSit!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.getPersistentData().getDouble("Landersit")) == 1)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				Entity entity2 = new LanderEntity.CustomEntity(LanderEntity.entity, entity.world);
				entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				entity2.rotationYaw = (float) (0);
				if (entity2 instanceof MobEntity)
					entity2.setRenderYawOffset(entity2.rotationYaw);
				entity2.prevRotationYaw = entity2.rotationYaw;
				((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
				entity.world.addEntity(entity2);
				entity.startRiding(entity2);
				Entity entityiterator = entity2;
				entity.getPersistentData().putDouble("Landersit", 0);
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					{
						final ItemStack _setstack = new ItemStack(Tier1RocketItemItem.block, (int) (1));
						final int _sltid = (int) (0);
						_setstack.setCount((int) 1);
						(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
						{
							final ItemStack _setstack = new ItemStack(Items.BUCKET, (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
						{
							final ItemStack _setstack = new ItemStack(ModInnet.FUEL_BUCKET.get(), (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Landersit")) == 2)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				Entity entity2 = new LanderEntity.CustomEntity(LanderEntity.entity, entity.world);
				entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				entity2.rotationYaw = (float) (0);
				if (entity2 instanceof MobEntity)
					entity2.setRenderYawOffset(entity2.rotationYaw);
				entity2.prevRotationYaw = entity2.rotationYaw;
				((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
				entity.world.addEntity(entity2);
				entity.startRiding(entity2);
				Entity entityiterator = entity2;
				entity.getPersistentData().putDouble("Landersit", 0);
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					{
						final ItemStack _setstack = new ItemStack(Tier2RocketItemItem.block, (int) (1));
						final int _sltid = (int) (0);
						_setstack.setCount((int) 1);
						(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
						{
							final ItemStack _setstack = new ItemStack(ModInnet.BARREL.get(), (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
						{
							final ItemStack _setstack = new ItemStack(ModInnet.FUEL_BARREL.get(), (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Landersit")) == 3)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				Entity entity2 = new LanderEntity.CustomEntity(LanderEntity.entity, entity.world);
				entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				entity2.rotationYaw = (float) (0);
				if (entity2 instanceof MobEntity)
					entity2.setRenderYawOffset(entity2.rotationYaw);
				entity2.prevRotationYaw = entity2.rotationYaw;
				((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
				entity.world.addEntity(entity2);
				entity.startRiding(entity2);
				Entity entityiterator = entity2;
				entity.getPersistentData().putDouble("Landersit", 0);
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					{
						final ItemStack _setstack = new ItemStack(Tier3RocketItemItem.block, (int) (1));
						final int _sltid = (int) (0);
						_setstack.setCount((int) 1);
						(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
						{
							final ItemStack _setstack = new ItemStack(ModInnet.BARREL.get(), (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
				if (((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity)) {
					if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
						{
							final ItemStack _setstack = new ItemStack(ModInnet.FUEL_BARREL.get(), (int) (1));
							final int _sltid = (int) (1);
							_setstack.setCount((int) 1);
							(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					}
				}
			}
		}
	}
}
