package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RoverEntity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;

public class RoverMovementProcedure {
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
		Entity entity = (Entity) dependencies.get("entity");
	/*	if (entity.getRidingEntity() instanceof RoverEntity.CustomEntity) {
			if (entity instanceof LivingEntity) {
				float forward = ((LivingEntity) entity).moveForward;
				entity.getRidingEntity().stepHeight = 1.0F;
				//entity.getRidingEntity().rotationYaw = entity.getRidingEntity().rotationYaw;
				//entity.getRidingEntity().prevRotationYaw = entity.getRidingEntity().rotationYaw;
				//entity.getRidingEntity().rotationPitch = entity.getRidingEntity().rotationPitch * 0.5F;
				//entity.getRidingEntity().setRotationYawHead(entity.getRidingEntity().rotationYaw);
				((LivingEntity) entity.getRidingEntity()).jumpMovementFactor = ((LivingEntity) entity.getRidingEntity()).getAIMoveSpeed() * 0.15F;
				//((LivingEntity) entity.getRidingEntity()).renderYawOffset = entity.getRidingEntity().rotationYaw;
				//((LivingEntity) entity.getRidingEntity()).rotationYawHead = entity.getRidingEntity().rotationYaw;
				if (forward >= 0) {
					float strafe = 0;
					if (((LivingEntity) entity.getRidingEntity()).getAIMoveSpeed() <= 0.4) {
						((LivingEntity) entity.getRidingEntity())
								.setAIMoveSpeed(((LivingEntity) entity.getRidingEntity()).getAIMoveSpeed() + (float) 0.02);
					}
					if (forward == 0) {
						((LivingEntity) entity.getRidingEntity()).setAIMoveSpeed(0f);
					}
					((LivingEntity) entity.getRidingEntity()).travel(new Vector3d(strafe, 0, forward));
				}
			}
		}*/
	}
}