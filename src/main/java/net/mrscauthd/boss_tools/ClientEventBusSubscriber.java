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
import net.mrscauthd.boss_tools.entity.PygroEntity;


import java.util.Map;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(MobInnet.ALIEN.get(), ((IRenderFactory) AlienRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(PygroEntity.entity, ((IRenderFactory) PygroRenderer::new));
	}

	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}
}
