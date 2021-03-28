/**
 * This mod element is always locked. Enter your code in the methods below.
 * If you don't need some of these methods, you can remove them as they
 * are overrides of the base class BossToolsModElements.ModElement.
 *
 * You can register new events in this class too.
 *
 * As this class is loaded into mod element list, it NEEDS to extend
 * ModElement class. If you remove this extend statement or remove the
 * constructor, the compilation will fail.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser - New... and make sure to make the class
 * outside net.mrscauthd.boss_tools as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 */
package net.mrscauthd.boss_tools;

import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsSpawnEggsItemGroup;
import net.mrscauthd.boss_tools.entity.AlienEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureEntity;

@BossToolsModElements.ModElement.Tag
public class MobInnet extends BossToolsModElements.ModElement {
	public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, "boss_tools");
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "boss_tools");
	public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder
			.create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
	public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg",
			() -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(SpaceBosstoolsSpawnEggsItemGroup.tab)));
	/**
	 * Do not remove this constructor
	 */
	public MobInnet(BossToolsModElements instance) {
		super(instance, 673);
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		// RenderingRegistry.registerEntityRenderingHandler(STEntitys.ROCKET.get(),
		// ((IRenderFactory) RocketRenderer::new));
		// RenderingRegistry.registerEntityRenderingHandler(ALIEN.get(),
		// ((IRenderFactory) AlienRenderer::new));
	}

	@Override
	public void initElements() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ENTITYS.register(bus);
		ITEMS.register(bus);
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN.get(), AlienEntity.setCustomAttributes().create());
		});
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
	}
}
