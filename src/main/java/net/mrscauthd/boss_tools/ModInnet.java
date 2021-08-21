
package net.mrscauthd.boss_tools;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.mrscauthd.boss_tools.alien.ModSpawnEggs;
import net.mrscauthd.boss_tools.block.*;
import net.mrscauthd.boss_tools.entity.AlienSpitEntity;
import net.mrscauthd.boss_tools.events.BiomeRegisrtyEvents;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.feature.MarsIceSpikeFeature;
import net.mrscauthd.boss_tools.feature.VenusDeltas;
import net.mrscauthd.boss_tools.item.BarrelItem;
import net.mrscauthd.boss_tools.item.CoalTorchItem;
import net.mrscauthd.boss_tools.item.SteahlItem;
import net.mrscauthd.boss_tools.entity.AlienEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.pygro.PygroMobsSensor;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureEntity;
import net.mrscauthd.boss_tools.world.structure.configuration.STConfiguredStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//@BossToolsModElements.ModElement.Tag
@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInnet {
    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, "boss_tools");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "boss_tools");

    public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder
            .create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));

    public static RegistryObject<EntityType<?>> ALIENSPITENTITY = ENTITYS.register("alien_spit_entity", () -> AlienSpitEntity.arrow);

    //pygro
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, "boss_tools");
    public static final RegistryObject<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", ()->new SensorType<>(PygroMobsSensor::new));

    //Block
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "boss_tools");

    public static RegistryObject<Block> COALTORCHBLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALLCOALTORCHBLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD).lootFrom(COALTORCHBLOCK.get())));
    //Flag Blocks
    public static RegistryObject<Block> FLAGBLOCK = BLOCKS.register("flag",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKblue = BLOCKS.register("flag_blue",() -> new FlagBlueBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKbrown = BLOCKS.register("flag_brown",() -> new FlagBrownBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKcyan = BLOCKS.register("flag_cyan",() -> new FlagCyanBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKgray = BLOCKS.register("flag_gray",() -> new FraggrayBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKgreen = BLOCKS.register("flag_green",() -> new FraggreenBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKlightblue = BLOCKS.register("flag_light_blue",() -> new FlaglightBlueBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKlime = BLOCKS.register("flag_lime",() -> new FlagLimeBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKmagenta = BLOCKS.register("flag_magenta",() -> new FlagmagentaBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKorange = BLOCKS.register("flag_orange",() -> new FlagorangeBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKpink = BLOCKS.register("flag_pink",() -> new FlagPinkBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKpurple = BLOCKS.register("flag_purple",() -> new FlagPurpleBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKred = BLOCKS.register("flag_red",() -> new FlagRedBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGBLOCKyellow = BLOCKS.register("flag_yellow",() -> new FlagYellowBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));

    //Item
    public static final RegistryObject<Item> TORCHITEM = ITEMS.register("coal_torch",
            () -> new CoalTorchItem(COALTORCHBLOCK.get(), WALLCOALTORCHBLOCK.get(),new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg",
            () -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));


    public static final RegistryObject<Item> BARREL = ITEMS.register("barrel", () -> new BarrelItem());

    //Steel Item Tier
    public static IItemTier SteelItemTier = new IItemTier() {
        public int getMaxUses() {
            return 1661;
        }

        public float getEfficiency() {
            return 7f;
        }

        public float getAttackDamage() {
            return 2f;
        }

        public int getHarvestLevel() {
            return 3;
        }

        public int getEnchantability() {
            return 14;
        }

        public Ingredient getRepairMaterial() {
            return Ingredient.fromStacks(new ItemStack(SteahlItem.block, (int) (1)));
        }
    };

    //Tools
    public static final RegistryObject<Item> SteelSword = ITEMS.register("steel_sword",
            () -> new SwordItem(SteelItemTier,3,-2.4f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> SteelShovel = ITEMS.register("steel_shovel",
            () -> new ShovelItem(SteelItemTier,1.5f,-3f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> SteelPickaxe = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(SteelItemTier,1,-2.8f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> SteelAxe = ITEMS.register("steel_axe",
            () -> new AxeItem(SteelItemTier,6,-3f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> SteelHoe = ITEMS.register("steel_hoe",
            () -> new HoeItem(SteelItemTier,-2,0f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    //Flag Items
    public static final RegistryObject<Item> FLAGITEM = ITEMS.register("flag", () -> new TallBlockItem(FLAGBLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMblue = ITEMS.register("flag_blue", () -> new TallBlockItem(FLAGBLOCKblue.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMbrown = ITEMS.register("flag_brown", () -> new TallBlockItem(FLAGBLOCKbrown.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMcyan = ITEMS.register("flag_cyan", () -> new TallBlockItem(FLAGBLOCKcyan.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMgray = ITEMS.register("flag_gray", () -> new TallBlockItem(FLAGBLOCKgray.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMgreen = ITEMS.register("flag_green", () -> new TallBlockItem(FLAGBLOCKgreen.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMlightblue = ITEMS.register("flag_light_blue", () -> new TallBlockItem(FLAGBLOCKlightblue.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMlime = ITEMS.register("flag_lime", () -> new TallBlockItem(FLAGBLOCKlime.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMmagenta = ITEMS.register("flag_magenta", () -> new TallBlockItem(FLAGBLOCKmagenta.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMorange = ITEMS.register("flag_orange", () -> new TallBlockItem(FLAGBLOCKorange.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMpink = ITEMS.register("flag_pink", () -> new TallBlockItem(FLAGBLOCKpink.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMpruple = ITEMS.register("flag_purple", () -> new TallBlockItem(FLAGBLOCKpurple.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMred = ITEMS.register("flag_red", () -> new TallBlockItem(FLAGBLOCKred.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAGITEMyellow = ITEMS.register("flag_yellow", () -> new TallBlockItem(FLAGBLOCKyellow.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));

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

    @SubscribeEvent
    public static void setup2(final FMLCommonSetupEvent event)
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
    //@SubscribeEvent
    public static void biomeModification(final BiomeLoadingEvent event) {
        RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if (event.getName().equals(new ResourceLocation("boss_tools:moon_biome")) && Config.AlienVillageStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("plains")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            // event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE)
        }
        if (event.getName().equals(new ResourceLocation("snowy_tundra")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            // event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE)
        }
        //  if (event.getName().equals(new ResourceLocation("boss_tools:mars_biom"))) {
        //       event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        //   }
        if (event.getName().equals(new ResourceLocation("forest")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("desert")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //venus bullet
        if (event.getName().equals(new ResourceLocation("boss_tools:venus_biome")) && Config.VenusBulletStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //venus tower
        if (event.getName().equals(new ResourceLocation("boss_tools:venus_biome")) && Config.VenusTowerStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_TOWER_CONFIGURED_RUN_DOWN_HOUSE);
        }
    }
    private static Method GETCODEC_METHOD;
    //@SubscribeEvent
    public static void addDimensionalSpacing(final WorldEvent.Load event) {
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
            //venus tower
            Map<Structure<?>, StructureSeparationSettings> tempMap3 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());

            tempMap3.putIfAbsent(STStructures2.VENUS_TOWER.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures2.VENUS_TOWER.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap3;
        }
    }

    //@Override
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN.get(), AlienEntity.setCustomAttributes().create());
        });
        event.enqueueWork(() -> {
            ICE_SPIKE = register("ice_spike1",
                    ModInnet.MARS_ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
            //Venus Deltas
            DELTAS = register("deltas1",
                    ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 8)))
                            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
            //Venus Deltas2
            DELTAS2 = register("deltas2",
                    ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 6)))
                            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
        });
    }
    //@SubscribeEvent
    public static void biomesLoading(final BiomeLoadingEvent event){
        if(event.getName().getPath().equals(BiomeRegisrtyEvents.mars_ice_spike_biome.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ICE_SPIKE);
        }
        //Venus Deltas
        if(event.getName().getPath().equals(BiomeRegisrtyEvents.infernal_venus_barrens_biome.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS);
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS2);
        }
    }
/*
    @Override
    public void serverLoad(FMLServerStartingEvent event) {
    }
    */
}