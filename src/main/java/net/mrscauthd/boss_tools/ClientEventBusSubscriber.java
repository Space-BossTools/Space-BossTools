package net.mrscauthd.boss_tools;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.entity.EntityType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.Minecraft;

import java.util.Map;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(MobInnet.ALIEN.get(), ((IRenderFactory) AlienRenderer::new));
	}

	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}

	// Space Suit
	@SubscribeEvent
	public static void onClientSetup2(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
			PlayerRenderer render = skinMap.get("default");
			render.addLayer(new SpaceSuitRenderer(render));
			render = skinMap.get("slim");
			render.addLayer(new SpaceSuitRenderer(render));
			// Player Renderer
			/*
			 * { Map<String, PlayerRenderer> skinMap =
			 * Minecraft.getInstance().getRenderManager().getSkinMap();
			 * 
			 * PlayerRenderer render = skinMap.get("default"); render.addLayer(new
			 * Test(render));
			 * 
			 * render = skinMap.get("slim"); render.addLayer(new Test(render)); }
			 */
			{
				// BipedArmorLayer armorStandRenderer = (BipedArmorLayer)
				// Minecraft.getInstance().getRenderManager().renderers.get(EntityType.ARMOR_STAND);
				// armorStandRenderer.addLayer(new Test(armorStandRenderer));
			}
		});
	}
}
