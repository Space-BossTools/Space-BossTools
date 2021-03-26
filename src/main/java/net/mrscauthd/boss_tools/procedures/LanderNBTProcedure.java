package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.FuelBucketBigItem;
import net.mrscauthd.boss_tools.item.FuelBuckedItem;
import net.mrscauthd.boss_tools.item.BucketBigItem;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class LanderNBTProcedure extends BossToolsModElements.ModElement {
	public LanderNBTProcedure(BossToolsModElements instance) {
		super(instance, 619);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LanderNBT!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 0);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Items.BUCKET, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 1);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBuckedItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 2);
			}
		}
		if (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 0);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(BucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 1);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 2);
			}
		}
		if (((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 0);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(BucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 1);
			}
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 2);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
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
			this.executeProcedure(dependencies);
		}
	}
}
