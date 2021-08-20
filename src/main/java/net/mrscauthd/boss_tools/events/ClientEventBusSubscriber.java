package net.mrscauthd.boss_tools.events;

import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.entity.EntityType;
import net.minecraft.client.Minecraft;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.alien.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.AlienSpitEntity;
import net.mrscauthd.boss_tools.entity.PygroEntity;
import net.mrscauthd.boss_tools.entity.MoglerEntity;
import net.mrscauthd.boss_tools.entity.renderer.alien.AlienRenderer;
import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerRenderer;
import net.mrscauthd.boss_tools.entity.renderer.pygro.PygroRenderer;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN.get(), ((IRenderFactory) AlienRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(PygroEntity.entity, ((IRenderFactory) PygroRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(MoglerEntity.entity, ((IRenderFactory) MoglerRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(AlienSpitEntity.arrow, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
	}

	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}
}
