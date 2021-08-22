package net.mrscauthd.boss_tools.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.mrscauthd.boss_tools.entity.*;

public class DisableVehicleFishingProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void FishingBobberTick(ProjectileImpactEvent.FishingBobber event) {
			if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
				if (((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof RocketTier1Entity.CustomEntity || ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof RocketTier2Entity.CustomEntity || ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof RocketTier3Entity.CustomEntity || ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof RoverEntity.CustomEntity || ((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof LandingGearEntity.CustomEntity) {
					event.setCanceled(true);
				}
			}
		}
	}
}
