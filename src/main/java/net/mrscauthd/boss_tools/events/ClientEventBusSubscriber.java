package net.mrscauthd.boss_tools.events;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
import net.mrscauthd.boss_tools.entity.renderer.alienzombie.AlienZombieRenderer;
import net.mrscauthd.boss_tools.entity.renderer.starcrawler.StarCrawlerRenderer;
import net.mrscauthd.boss_tools.spawneggs.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.renderer.alien.AlienRenderer;
import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerRenderer;
import net.mrscauthd.boss_tools.entity.renderer.pygro.PygroRenderer;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	public static KeyBinding key1;
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN.get(), ((IRenderFactory) AlienRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.PYGRO.get(), ((IRenderFactory) PygroRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.MOGLER.get(), ((IRenderFactory) MoglerRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN_ZOMBIE.get(), ((IRenderFactory) AlienZombieRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.STAR_CRAWLER.get(), ((IRenderFactory) StarCrawlerRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN_SPIT_ENTITY.get(), renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));

		//Key Binding Registrys
		key1 = new KeyBinding("key.boss_tools.rocket_start", GLFW.GLFW_KEY_SPACE, "key.categories.boss_tools");
		ClientRegistry.registerKeyBinding(key1);

		//Fluid Translucent Renderer
		RenderTypeLookup.setRenderLayer(ModInnet.FLOWING_FUEL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModInnet.FUEL_STILL.get(), RenderType.getTranslucent());
	}

	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}
}
