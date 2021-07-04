
package net.mrscauthd.boss_tools.gui.overlay;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber
public class DisableVehicleHealthOverlay {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void eventHandler(RenderGameOverlayEvent event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
			PlayerEntity entity = Minecraft.getInstance().player;
			if (entity.getRidingEntity() instanceof RocketEntity.CustomEntity || entity.getRidingEntity() instanceof RocketTier3Entity.CustomEntity|| entity.getRidingEntity() instanceof RocketTier2Entity.CustomEntity || entity.getRidingEntity() instanceof LandingGearEntity.CustomEntity || entity.getRidingEntity() instanceof RoverEntity.CustomEntity) {
				event.setCanceled(true);
			}
		}
	}
}
