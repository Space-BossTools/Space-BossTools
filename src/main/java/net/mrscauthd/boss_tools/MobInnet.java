
package net.mrscauthd.boss_tools;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.mrscauthd.boss_tools.block.CoalTorchBlock;
import net.mrscauthd.boss_tools.block.FlagBlock;
import net.mrscauthd.boss_tools.entity.AlienZombieEntity;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsBasicsItemGroup;
import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsFlagsItemGroup;
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
import net.mrscauthd.boss_tools.world.biome.MarsIceBiomeBiome;
import net.mrscauthd.boss_tools.world.biome.InfernalVenusBarrensBiome;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureEntity;
import net.mrscauthd.boss_tools.BossToolsModVariables;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@BossToolsModElements.ModElement.Tag
@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobInnet extends BossToolsModElements.ModElement {
    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, "boss_tools");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "boss_tools");
    public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder
            .create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
    public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg",
            () -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(SpaceBosstoolsSpawnEggsItemGroup.tab)));
    //Block
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "boss_tools");

    public static RegistryObject<Block> COALTORCHBLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALLCOALTORCHBLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD).lootFrom(COALTORCHBLOCK.get())));
    //Flag Blocks
    public static RegistryObject<Block> FLAGBLOCK = BLOCKS.register("flag",() -> new FlagBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false)));
    
    //Item
    public static final RegistryObject<Item> TORCHITEM = ITEMS.register("coal_torch",
            () -> new CoalTorchItem(COALTORCHBLOCK.get(), WALLCOALTORCHBLOCK.get(),new Item.Properties().group(SpaceBosstoolsBasicsItemGroup.tab)));
    
    //Flag Items
    public static final RegistryObject<Item> FLAGITEM = ITEMS.register("flag",
            () -> new TallBlockItem(FLAGBLOCK.get(), new Item.Properties().group(SpaceBosstoolsFlagsItemGroup.tab)));


    //Wrold Gen Things
    public static ConfiguredFeature<?, ?> ICE_SPIKE;
    public static MarsIceSpikeFeature MARS_ICE_SPIKE;
    //VenusDeltas
    public static ConfiguredFeature<?, ?> DELTAS;
    public static ConfiguredFeature<?, ?> DELTAS2;
    public static VenusDeltas VENUS_DELTAS;
    @SubscribeEvent
    public static void RegistryFeature(RegistryEvent.Register<Feature<?>> feature) {
        MARS_ICE_SPIKE = new MarsIceSpikeFeature(NoFeatureConfig.field_236558_a_);
        MARS_ICE_SPIKE.setRegistryName("boss_tools", "mars_ice_spike");
        feature.getRegistry().register(MARS_ICE_SPIKE);
        //VenusDeltas
        VENUS_DELTAS = new VenusDeltas(ColumnConfig.CODEC);
        VENUS_DELTAS.setRegistryName("boss_tools", "venus_deltas");
        feature.getRegistry().register(VENUS_DELTAS);
    }

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }



    /**
     * Do not remove this constructor
     */
    public MobInnet(BossToolsModElements instance) {
        super(instance, 901);
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
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        //bus.addGenericListener(Structure.class, this::onRegisterStructures);
        ENTITYS.register(bus);
        ITEMS.register(bus);
        BLOCKS.register(bus);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        //  forgeBus.addListener(EventPriority.HIGH, this::biomeModificationa);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //alien Village
        STStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        modEventBus.addListener(this::setup2);
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
        //Meteor
        STStructures.METEOR_DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        STStructures2.VENUS_BULLET_DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        //forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing3);
        forgeBus.addListener(EventPriority.HIGH, this::biomesLoading);
    }
    public void setup2(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            STStructures.setupStructures();
            STStructures2.setupStructures();
            STConfiguredStructures.registerConfiguredStructures();
            //Meteor
            //STStructures.setupStructures();
            //STConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if (event.getName().equals(new ResourceLocation("boss_tools:moon_biome")) && BossToolsModVariables.AlienVillageStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("plains")) && BossToolsModVariables.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            // event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE)
        }
        if (event.getName().equals(new ResourceLocation("snowy_tundra")) && BossToolsModVariables.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            // event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE)
        }
        //  if (event.getName().equals(new ResourceLocation("boss_tools:mars_biom"))) {
        //       event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        //   }
        if (event.getName().equals(new ResourceLocation("forest")) && BossToolsModVariables.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("desert")) && BossToolsModVariables.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //venus bullet
        if (event.getName().equals(new ResourceLocation("boss_tools:venus_biome")) && BossToolsModVariables.VenusBulletStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE);
        }
    }
    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                // StructureTutorialMain.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator ||
                    serverWorld.getDimensionType().equals(World.OVERWORLD)){
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.putIfAbsent(STStructures.RUN_DOWN_HOUSE.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.RUN_DOWN_HOUSE.get()));
            //  serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_() = tempMap;
            //meteor
            // tempMap.putIfAbsent(STStructures.METEOR.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.METEOR.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

            Map<Structure<?>, StructureSeparationSettings> tempMap1 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            //tempMap.putIfAbsent(STStructures.RUN_DOWN_HOUSE.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.RUN_DOWN_HOUSE.get()));
            //  serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_() = tempMap;
            //meteor
            tempMap1.putIfAbsent(STStructures.METEOR.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.METEOR.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap1;
            //venus bullet
            Map<Structure<?>, StructureSeparationSettings> tempMap2 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            //tempMap.putIfAbsent(STStructures.RUN_DOWN_HOUSE.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.RUN_DOWN_HOUSE.get()));
            //  serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_() = tempMap;
            //meteor
            tempMap2.putIfAbsent(STStructures2.VENUS_BULLET.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures2.VENUS_BULLET.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap2;
        }
    }
// Meteor


    @Override
    public void init(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN.get(), AlienEntity.setCustomAttributes().create());
        });
        event.enqueueWork(() -> {
            ICE_SPIKE = register("ice_spike1",
                    MobInnet.MARS_ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
            //Venus Deltas
            DELTAS = register("deltas1",
                    MobInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 8)))
                            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
            //Venus Deltas2
            DELTAS2 = register("deltas2",
                    MobInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 6)))
                            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
        });
    }
    public void biomesLoading(final BiomeLoadingEvent event){
        if(event.getName().getPath().equals(MarsIceBiomeBiome.biome.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ICE_SPIKE);
        }
        //Venus Deltas
        if(event.getName().getPath().equals(InfernalVenusBarrensBiome.biome.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS);
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS2);
        }
    }

    @Override
    public void serverLoad(FMLServerStartingEvent event) {
    }
}