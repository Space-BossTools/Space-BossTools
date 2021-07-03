package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RenderPlayerEvent;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;


@BossToolsModElements.ModElement.Tag
public class PlayerRendererProcedure extends BossToolsModElements.ModElement {
	public PlayerRendererProcedure(BossToolsModElements instance) {
		super(instance, 680);
		MinecraftForge.EVENT_BUS.register(this);
		// MinecraftForge.EVENT_BUS.post(evt);
		// return evt;
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void render(RenderPlayerEvent event) {
		/*if (((event.getEntity().getRidingEntity()) instanceof RocketEntity.CustomEntity)) {
			event.getMatrixStack().scale(0.85f, 0.85f, 0.85f);
			// event.getRenderer();
		}
		if (((event.getEntity().getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)) {
			event.getMatrixStack().scale(0.85f, 0.85f, 0.85f);
			// event.getRenderer();
		}
		if (((event.getEntity().getRidingEntity()) instanceof RocketTier3Entity.CustomEntity)) {
			event.getMatrixStack().scale(0.85f, 0.85f, 0.85f);
			// event.getRenderer();
		}*/
		if (((event.getEntity().getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			event.getMatrixStack().scale(0f, 0f, 0f);
			// event.getRenderer();
		}
	}
	/*	@SubscribeEvent
		public void renderPlayerPre(EntityEvent.Size size) {
			if (((size.getEntity().getRidingEntity()) instanceof RocketEntity.CustomEntity)) {
			size.setNewEyeHeight(1f);
			}//l
		}*/
	/*	@SubscribeEvent
		public void renderPlayerPre(EntityEvent.Size event) {
		Entity entity = event.getEntity();
			if (entity.getRidingEntity() instanceof RocketEntity.CustomEntity) {
				RocketEntity.CustomEntity rocket = (RocketEntity.CustomEntity) entity.getRidingEntity();
//rocket.do stuff here
//something like below
				//rocket.setNewSize(EntitySize.flexible(0.7f, 1.9f));
				event.setNewEyeHeight(1.f);
			}
		}*/
}
