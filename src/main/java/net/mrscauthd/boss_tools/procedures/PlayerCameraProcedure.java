package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketEntity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.Minecraft;

public class PlayerCameraProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
			if (event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketEntity.CustomEntity) {
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_FRONT)) {
					event.getInfo().movePosition(-8d, 0d, 0);
					event.getInfo().calcCameraDistance(-8D);
				}
				if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_BACK)) {
					event.getInfo().movePosition(-8d, 0d, 0);
					event.getInfo().calcCameraDistance(-8D);
				}
			}
		}
	}
}
