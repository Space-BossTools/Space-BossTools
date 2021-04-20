package net.mrscauthd.boss_tools;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class PlayerCameraRocket extends BossToolsModElements.ModElement {
	public PlayerCameraRocket(BossToolsModElements elements) {
		super(elements, 600);
		MinecraftForge.EVENT_BUS.register(this);
	//	System.out.println("EventFOVRegister");
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Test!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((((entity.getRidingEntity()) instanceof RocketEntity.CustomEntity)
				|| (((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)
						|| (((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)
								|| ((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity))))) {
			entity.getPersistentData().putBoolean("rocketsit", (true));
		} else {
			entity.getPersistentData().putBoolean("rocketsit", (false));
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupFOVPlayer(EntityViewRenderEvent.FOVModifier event) {
		// if
		// (Minecraft.getInstance().player.getPersistentData().contains("isInRocket")) {
		// boolean isInRocket =
		// Minecraft.getInstance().player.getPersistentData().getBoolean("isInRocket");
		if (Minecraft.getInstance().player.getPersistentData().contains("rocketsit")) {
			boolean rocketsit = Minecraft.getInstance().player.getPersistentData().getBoolean("rocketsit");
			if (rocketsit) {
				// event.setFOV(event.getFOV() + 35);
			/*	if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON)) {
					event.setFOV(event.getFOV() + 0);
				}*/
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_FRONT)) {
					event.setFOV(100); // event.setFOV(event.getFOV() - 35);
				}
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_BACK)) {
					event.setFOV(100); // event.setFOV(event.getFOV() - 35);
				}
			}/* else {
				event.setFOV(event.getFOV());
			}*/
			// if(Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON))
			// {
			// event.setFOV(event.getFOV() -35);
			// }
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
