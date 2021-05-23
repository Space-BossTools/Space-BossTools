package net.mrscauthd.boss_tools.procedures;

import org.lwjgl.glfw.GLFW;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.HashMap;

public class PressHotkeysProcedure {
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
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure PressHotkeys!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		// Rocket Rotate
		if ((((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)
				|| (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)
						|| ((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)))) {
			if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_A))) {
				(entity.getRidingEntity()).getPersistentData().putDouble("Rotation", 1);
			} else {
				(entity.getRidingEntity()).getPersistentData().putDouble("Rotation", 0);
			}
			if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_D))) {
				(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 1);
			} else {
				(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 0);
			}
			if (Minecraft.getInstance().currentScreen != null) {
				(entity.getRidingEntity()).getPersistentData().putDouble("RotationB", 0);
				(entity.getRidingEntity()).getPersistentData().putDouble("Rotation", 0);
			}
		}
	}
}
