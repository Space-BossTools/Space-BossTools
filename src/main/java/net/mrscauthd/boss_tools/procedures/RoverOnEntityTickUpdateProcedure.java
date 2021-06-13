package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

public class RoverOnEntityTickUpdateProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RoverOnEntityTickUpdate!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (((new Object() {
			public ItemStack getItemStack(int sltid, Entity entity) {
				AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
					_retval.set(capability.getStackInSlot(sltid).copy());
				});
				return _retval.get();
			}
		}.getItemStack((int) (0), entity)).getItem() == new ItemStack(FuelBlock.bucket, (int) (1)).getItem())) {
			if (((entity.getPersistentData().getDouble("fuel")) == 0)) {
				{
					final ItemStack _setstack = new ItemStack(Items.BUCKET, (int) (1));
					final int _sltid = (int) (0);
					_setstack.setCount((int) 1);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						if (capability instanceof IItemHandlerModifiable) {
							((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
						}
					});
				}
				entity.getPersistentData().putDouble("Rocketfuel", 1);
			}
		}
		if (((entity.getPersistentData().getDouble("Rocketfuel")) == 1)) {
			if (((entity.getPersistentData().getDouble("fuel")) <= 15999)) {
				entity.getPersistentData().putDouble("fuel", ((entity.getPersistentData().getDouble("fuel")) + 32));
			} else {
				entity.getPersistentData().putDouble("Rocketfuel", 0);
			}
		}
		if (((entity.getPersistentData().getDouble("fuel")) >= 16000)) {
			entity.getPersistentData().putDouble("fuel", 16000);
		}
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).setAir((int) 300);
	}
}
