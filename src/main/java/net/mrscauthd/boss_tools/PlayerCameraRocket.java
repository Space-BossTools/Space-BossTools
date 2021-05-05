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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.MathHelper;
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
		// System.out.println("EventFOVRegister");
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

	// camera test 1
	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashPositionr(float partialTicks) {
		Entity entity = Minecraft.getInstance().renderViewEntity;
		// double d0 = 0.22D * (entity.getPrimaryHand() == HandSide.RIGHT ? -1.0D :
		// 1.0D);
		float f = MathHelper.lerp(partialTicks * 0.5F, entity.rotationPitch, entity.prevRotationPitch) * ((float) Math.PI / 180F);
		float f1 = MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) * ((float) Math.PI / 180F);
		Vector3d vector3d = entity.getLook(partialTicks);
		Vector3d vector3d1 = entity.getMotion();
		double d1 = Entity.horizontalMag(vector3d1);
		double d2 = Entity.horizontalMag(vector3d);
		float f2;
		if (d1 > 0.0D && d2 > 0.0D) {
			double d3 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d1 * d2);
			double d4 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
			f2 = (float) (Math.signum(d4) * Math.acos(d3));
		} else {
			f2 = 0.0F;
		}
		return entity.func_242282_l(partialTicks).add((new Vector3d(1, -0.11D, 0.85D)).rotateRoll(-f2).rotatePitch(-f).rotateYaw(-f1));
	}

	/*public Vector3d getLeashPosition(float partialTicks) {
		Entity entity = Minecraft.getInstance().renderViewEntity;
		if (Minecraft.getInstance().gameSettings.getPointOfView().func_243192_a()) {
			float f = MathHelper.lerp(partialTicks * 0.5F, entity.rotationYaw, entity.prevRotationYaw) * ((float) Math.PI / 180F);
			float f1 = MathHelper.lerp(partialTicks * 0.5F, entity.rotationPitch, entity.prevRotationPitch) * ((float) Math.PI / 180F);
			// double d0 = entity.getPrimaryHand() == HandSide.RIGHT ? -1.0D : 1.0D;
			Vector3d vector3d = new Vector3d(0.39D * 1, -0.6D, 0.3D);
			return vector3d.rotatePitch(-f1).rotateYaw(-f).add(entity.getEyePosition(partialTicks));
		} else {
			return super.getLeashPosition(partialTicks);
		}
	}*/

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupFOVPlayer(EntityViewRenderEvent event) {
		Entity entity = event.getInfo().getRenderViewEntity();
		Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty();
		{
			PointOfView pointofview = Minecraft.getInstance().gameSettings.getPointOfView();
			Minecraft.getInstance().gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK_ROCKET);
			// net.mrscauthd.boss_tools.mixin.CameraMixin.setPointOfView(net.mrscauthd.boss_tools.PointOfView.THIRD_PERSON_BACK_ROCKET);
			// Minecraft.getInstance().gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);
			if (pointofview.func_243192_a() != Minecraft.getInstance().gameSettings.getPointOfView().func_243192_a()) {
				Minecraft.getInstance().gameRenderer
						.loadEntityShader(Minecraft.getInstance().gameSettings.getPointOfView().func_243192_a() ? entity : null);
			}
		}
	}
// camera test 1 end
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupFOVPlayer(EntityViewRenderEvent.FOVModifier event) {
		// test
		/*
		 * Entity entity = event.getInfo().getRenderViewEntity();
		 * Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty(); {
		 * PointOfView pointofview =
		 * Minecraft.getInstance().gameSettings.getPointOfView();
		 * Minecraft.getInstance().gameSettings.setPointOfView(/*Minecraft.getInstance()
		 * .gameSettings.getPointOfView().func_243194_c()PointOfView.THIRD_PERSON_FRONT)
		 * ; /* if (pointofview.func_243192_a() !=
		 * Minecraft.getInstance().gameSettings.getPointOfView().func_243192_a()) {
		 * Minecraft.getInstance().gameRenderer.loadEntityShader(Minecraft.getInstance()
		 * .gameSettings.getPointOfView().func_243192_a() ? entity : null); } }
		 */
		// if
		// (Minecraft.getInstance().player.getPersistentData().contains("isInRocket")) {
		// boolean isInRocket =
		// Minecraft.getInstance().player.getPersistentData().getBoolean("isInRocket");
		if (Minecraft.getInstance().player.getPersistentData().contains("rocketsit")) {
			boolean rocketsit = Minecraft.getInstance().player.getPersistentData().getBoolean("rocketsit");
			if (rocketsit) {
				// event.setFOV(event.getFOV() + 35);
				/*
				 * if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.
				 * FIRST_PERSON)) { event.setFOV(event.getFOV() + 0); }
				 */
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_FRONT)) {
					event.setFOV(100); // event.setFOV(event.getFOV() - 35);
				}
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_BACK)) {
					event.setFOV(100); // event.setFOV(event.getFOV() - 35);
				}
			} /*
				 * else { event.setFOV(event.getFOV()); }
				 */
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
