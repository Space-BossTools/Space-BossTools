package net.mrscauthd.boss_tools.procedures;

import org.lwjgl.glfw.GLFW;

import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class LanderSpaceProcedure extends BossToolsModElements.ModElement {
	public LanderSpaceProcedure(BossToolsModElements instance) {
		super(instance, 694);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LanderSpace!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_SPACE))) {
			if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
				if ((((entity.getRidingEntity()).isOnGround()) == (false))) {
					(entity.getRidingEntity()).setMotion(((entity.getRidingEntity()).getMotion().getX()),
							(((entity.getRidingEntity()).getMotion().getY()) * 0.91), ((entity.getRidingEntity()).getMotion().getZ()));
				}
				(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
				(entity.getRidingEntity()).getPersistentData().putDouble("Lander2", 1);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			(entity.getRidingEntity()).fallDistance = (float) (((((entity.getRidingEntity()).getMotion().getY()) * (-1)) * 4.5));
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
