package net.mrscauthd.boss_tools;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.mrscauthd.boss_tools.world.biomes.BiomeRegisrtyEvents;
import net.mrscauthd.boss_tools.events.SyncEvents;
import net.mrscauthd.boss_tools.keybind.KeyBindings;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures2;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.EntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod("boss_tools")
public class BossToolsMod {
	public static final Logger LOGGER = LogManager.getLogger(BossToolsMod.class);
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "boss_tools"),
			() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID;
	public BossToolsModElements elements;
	public BossToolsMod() {
		elements = new BossToolsModElements();
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientLoad);
		MinecraftForge.EVENT_BUS.register(new BossToolsModFMLBusEvents(this));

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		//SyncEvent Registers
		SyncEvents.OxygenBulletGeneratorSyncEvent.NetworkLoader.registerMessages();
		SyncEvents.PlayerMovementSyncEvent.NetworkLoader.registerMessages();

		//MobInnet
		MinecraftForge.EVENT_BUS.register(this);
		ModInnet.ENTITYS.register(bus);
		ModInnet.ITEMS.register(bus);
		ModInnet.BLOCKS.register(bus);
		ModInnet.TILE_ENTITYS.register(bus);
		ModInnet.SOUNDS.register(bus);
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		ModInnet.SENSOR.register(bus);
		ModInnet.FLUIDS.register(bus);
		ModInnet.RECIPE_SERIALIZERS.register(bus);
		bus.addListener(ModInnet::setup2);
		forgeBus.addListener(EventPriority.NORMAL, ModInnet::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, ModInnet::biomeModification);
		//Structures
		STStructures2.DEFERRED_REGISTRY_STRUCTURE.register(bus);
		STStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);

		forgeBus.addListener(EventPriority.HIGH, ModInnet::biomesLoading);
		//Biome Registery Event
		bus.register(new BiomeRegisrtyEvents.BiomeRegisterHandler());

		//KeyBindings
		KeyBindings.registerMessages();
	}

	private void init(FMLCommonSetupEvent event) {
		elements.getElements().forEach(element -> element.init(event));
	}

	public void clientLoad(FMLClientSetupEvent event) {
		elements.getElements().forEach(element -> element.clientLoad(event));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(elements.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(elements.getItems().stream().map(Supplier::get).toArray(Item[]::new));
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(elements.getEntities().stream().map(Supplier::get).toArray(EntityType[]::new));
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().registerAll(elements.getEnchantments().stream().map(Supplier::get).toArray(Enchantment[]::new));
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
		elements.registerSounds(event);
	}
	private static class BossToolsModFMLBusEvents {
		private final BossToolsMod parent;
		BossToolsModFMLBusEvents(BossToolsMod parent) {
			this.parent = parent;
		}

		@SubscribeEvent
		public void serverLoad(FMLServerStartingEvent event) {
			this.parent.elements.getElements().forEach(element -> element.serverLoad(event));
		}
	}
	
	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

}
