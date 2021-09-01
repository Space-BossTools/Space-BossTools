package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class RocketDismountProcedure {
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
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketDismount!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
	/*	if (((entity.getPersistentData().getBoolean("dismount")) == (true))) {
			{
				Entity _ent = entity;
				_ent.setPositionAndUpdate((entity.getPosX()), ((entity.getPosY()) - 0.5), (entity.getPosZ()));
				if (_ent instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), ((entity.getPosY()) - 0.5), (entity.getPosZ()),
							_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
				}
			}
			entity.getPersistentData().putBoolean("dismount", (false));
		}*/
	}
}
