package net.mrscauthd.boss_tools;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
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
import net.mrscauthd.boss_tools.armor.SpaceSuit;
import net.mrscauthd.boss_tools.block.*;
import net.mrscauthd.boss_tools.crafting.BlastingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;
import net.mrscauthd.boss_tools.fluid.OilFluid;
import net.mrscauthd.boss_tools.machines.*;
import net.mrscauthd.boss_tools.spawneggs.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.pygro.PygroEntity;
import net.mrscauthd.boss_tools.flag.*;
import net.mrscauthd.boss_tools.fluid.FuelFluid;
import net.mrscauthd.boss_tools.item.HammerItem;
import net.mrscauthd.boss_tools.armor.NetheriteSpaceSuit;
import net.mrscauthd.boss_tools.world.biomes.BiomeRegistry;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.feature.MarsIceSpikeFeature;
import net.mrscauthd.boss_tools.feature.VenusDeltas;
import net.mrscauthd.boss_tools.item.CoalTorchItem;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.entity.pygro.PygroMobsSensor;

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

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInnet {

    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, "boss_tools");

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITYS = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "boss_tools");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "boss_tools");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "boss_tools");

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "boss_tools");

    //Entitys
    public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder.<AlienEntity>create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
    public static RegistryObject<EntityType<?>> PYGRO = ENTITYS.register("pygro", () -> EntityType.Builder.create(PygroEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.6f, 1.8f).build(new ResourceLocation("boss_tools", "pygro").toString()));
    public static RegistryObject<EntityType<?>> MOGLER = ENTITYS.register("mogler", () -> EntityType.Builder.create(MoglerEntity::new, EntityClassification.MONSTER).size(1.4f, 1.4f).build(new ResourceLocation("boss_tools", "mogler").toString()));
    public static RegistryObject<EntityType<?>> ALIEN_ZOMBIE = ENTITYS.register("alien_zombie", () -> EntityType.Builder.create(AlienZombieEntity::new, EntityClassification.MONSTER).size(0.6f, 2.4f).build(new ResourceLocation("boss_tools", "alien_zombie").toString()));
    public static RegistryObject<EntityType<?>> STAR_CRAWLER = ENTITYS.register("star_crawler", () -> EntityType.Builder.create(StarCrawlerEntity::new, EntityClassification.MONSTER).size(1.3f, 1f).build(new ResourceLocation("boss_tools", "star_crawler").toString()));

    //Rockets
    public static RegistryObject<EntityType<?>> TIER_1_ROCKET = ENTITYS.register("rocket_t1", () -> EntityType.Builder.create(RocketTier1Entity::new, EntityClassification.CREATURE).size(1.1f, 4.4f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t1").toString()));
    public static RegistryObject<EntityType<?>> TIER_2_ROCKET = ENTITYS.register("rocket_t2", () -> EntityType.Builder.create(RocketTier2Entity::new, EntityClassification.CREATURE).size(1.1f, 4.6f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t2").toString()));
    public static RegistryObject<EntityType<?>> TIER_3_ROCKET = ENTITYS.register("rocket_t3", () -> EntityType.Builder.create(RocketTier3Entity::new, EntityClassification.CREATURE).size(1.1f, 4.8f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t3").toString()));


    //Alien Spit Entity
    public static RegistryObject<EntityType<? extends AlienSpitEntity>> ALIEN_SPIT_ENTITY = ENTITYS.register("alien_spit_entity", () -> EntityType.Builder.<AlienSpitEntity>create(AlienSpitEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation("boss_tools", "alien_spit_entity").toString()));

    //pygro
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, "boss_tools");
    public static final RegistryObject<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", ()->new SensorType<>(PygroMobsSensor::new));

    //Sounds
    public static RegistryObject<SoundEvent> ROCKET_SOUND = SOUNDS.register("rocket_fly",() -> new SoundEvent(new ResourceLocation("boss_tools", "rocket_fly")));

    //Blocks
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "boss_tools");

    public static RegistryObject<Block> COAL_TORCH_BLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALL_COAL_TORCH_BLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD).lootFrom(COAL_TORCH_BLOCK.get())));
    public static RegistryObject<Block> COAL_LANTERN_BLOCK = BLOCKS.register("coal_lantern",() -> new CoalLanternBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(3.5F).sound(SoundType.LANTERN).notSolid()));


    //Flag Blocks
    public static RegistryObject<Block> FLAG_BLOCK = BLOCKS.register("flag",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_BLUE_BLOCK = BLOCKS.register("flag_blue",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_BROWN_BLOCK = BLOCKS.register("flag_brown",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_CYAN_BLOCK = BLOCKS.register("flag_cyan",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_GRAY_BLOCK = BLOCKS.register("flag_gray",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_GREEN_BLOCK = BLOCKS.register("flag_green",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_LIGHT_BLUE_BLOCK = BLOCKS.register("flag_light_blue",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_LIME_BLOCK = BLOCKS.register("flag_lime",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_MAGENTA_BLOCk = BLOCKS.register("flag_magenta",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_ORANGE_BLOCK = BLOCKS.register("flag_orange",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_PINK_BLOCK = BLOCKS.register("flag_pink",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_PURPLE_BLOCK = BLOCKS.register("flag_purple",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_RED_BLOCK = BLOCKS.register("flag_red",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_YELLOW_BLOCK = BLOCKS.register("flag_yellow",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));

    //Tile Entity RegistryObject
    public static final RegistryObject<TileEntityType<?>> FUEL_REFINERY = TILE_ENTITYS.register("fuel_refinery", () -> TileEntityType.Builder.create(FuelRefineryBlock.CustomTileEntity::new,ModInnet.FUEL_REFINERY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BLAST_FURNACE = TILE_ENTITYS.register("blast_furnace", () -> TileEntityType.Builder.create(BlastingFurnaceBlock.CustomTileEntity::new,ModInnet.BLAST_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> COMPRESSOR = TILE_ENTITYS.register("compressor", () -> TileEntityType.Builder.create(CompressorBlock.CustomTileEntity::new,ModInnet.COMPRESSOR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> COAL_GENERATOR = TILE_ENTITYS.register("coal_generator", () -> TileEntityType.Builder.create(CoalGeneratorBlock.CustomTileEntity::new,ModInnet.COAL_GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> OXYGEN_LOADER = TILE_ENTITYS.register("oxygen_loader", () -> TileEntityType.Builder.create(OxygenLoaderBlock.CustomTileEntity::new,ModInnet.OXYGEN_LOADER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SOLAR_PANEL = TILE_ENTITYS.register("solar_panel", () -> TileEntityType.Builder.create(SolarPanelBlock.CustomTileEntity::new,ModInnet.SOLAR_PANEL_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> NASA_WORKBENCH = TILE_ENTITYS.register("nasa_workbench", () -> TileEntityType.Builder.create(NASAWorkbenchBlock.CustomTileEntity::new,ModInnet.NASA_WORKBENCH_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<OxygenBubbleDistributorBlock.CustomTileEntity>> OXYGEN_BUBBLE_DISTRIBUTOR = TILE_ENTITYS.register("oxygen_bubble_distributor", () -> TileEntityType.Builder.create(OxygenBubbleDistributorBlock.CustomTileEntity::new,ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()).build(null));


    //Tile Entitys Flags
    public static final RegistryObject<TileEntityType<FlagTileEntity>> FLAG = TILE_ENTITYS.register("flag", () -> TileEntityType.Builder.create(FlagTileEntity::new,ModInnet.FLAG_BLOCK.get(), ModInnet.FLAG_BLUE_BLOCK.get(), ModInnet.FLAG_BROWN_BLOCK.get(), ModInnet.FLAG_CYAN_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GREEN_BLOCK.get(),ModInnet.FLAG_LIGHT_BLUE_BLOCK.get(),ModInnet.FLAG_LIME_BLOCK.get(),ModInnet.FLAG_MAGENTA_BLOCk.get(),ModInnet.FLAG_ORANGE_BLOCK.get(),ModInnet.FLAG_PINK_BLOCK.get(),ModInnet.FLAG_PURPLE_BLOCK.get(),ModInnet.FLAG_RED_BLOCK.get(),ModInnet.FLAG_YELLOW_BLOCK.get()).build(null));

    //Machines
    public static RegistryObject<Block> FUEL_REFINERY_BLOCK = BLOCKS.register("fuel_refinery",() -> new FuelRefineryBlock.CustomBlock());
    public static RegistryObject<Block> BLAST_FURNACE_BLOCK = BLOCKS.register("blast_furnace",() -> new BlastingFurnaceBlock.CustomBlock());
    public static RegistryObject<Block> COMPRESSOR_BLOCK = BLOCKS.register("compressor",() -> new CompressorBlock.CustomBlock());
    public static RegistryObject<Block> COAL_GENERATOR_BLOCK = BLOCKS.register("coal_generator",() -> new CoalGeneratorBlock.CustomBlock());
    public static RegistryObject<Block> OXYGEN_LOADER_BLOCK = BLOCKS.register("oxygen_loader",() -> new OxygenLoaderBlock.CustomBlock());
    public static RegistryObject<Block> SOLAR_PANEL_BLOCK = BLOCKS.register("solar_panel",() -> new SolarPanelBlock.CustomBlock());
    public static RegistryObject<Block> NASA_WORKBENCH_BLOCK = BLOCKS.register("nasa_workbench",() -> new NASAWorkbenchBlock.CustomBlock());
    public static RegistryObject<Block> OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK = BLOCKS.register("oxygen_bubble_distributor",() -> new OxygenBubbleDistributorBlock.CustomBlock());

    //Block Item
    public static final RegistryObject<BlockItem> FUEL_REFINERY_ITEM = ITEMS.register("fuel_refinery", () -> new BlockItem(ModInnet.FUEL_REFINERY_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> BLAST_FURNACE_ITEM = ITEMS.register("blast_furnace", () -> new BlockItem(ModInnet.BLAST_FURNACE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COMPRESSOR_ITEM = ITEMS.register("compressor", () -> new BlockItem(ModInnet.COMPRESSOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator", () -> new BlockItem(ModInnet.COAL_GENERATOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_LOADER_ITEM = ITEMS.register("oxygen_loader", () -> new BlockItem(ModInnet.OXYGEN_LOADER_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> SOLAR_PANEL_ITEM = ITEMS.register("solar_panel", () -> new BlockItem(ModInnet.SOLAR_PANEL_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> NASA_WORKBENCH_ITEM = ITEMS.register("nasa_workbench", () -> new BlockItem(ModInnet.NASA_WORKBENCH_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_BUBBLE_DISTRIBUTOR_ITEM = ITEMS.register("oxygen_bubble_distributor", () -> new BlockItem(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COAL_LANTERN_ITEM = ITEMS.register("coal_lantern", () -> new BlockItem(ModInnet.COAL_LANTERN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    //Fuel Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", ()-> new FuelFluid.Flowing());
    public static final RegistryObject<FlowingFluid> FUEL_STILL = FLUIDS.register("fuel", ()-> new FuelFluid.Source());
    public static RegistryObject<FlowingFluidBlock> FUEL_BLOCK = BLOCKS.register("fuel",() -> new FlowingFluidBlock(ModInnet.FUEL_STILL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));
    public static final RegistryObject<Item> FUEL_BUCKET = ITEMS.register("fuel_bucket", () -> new BucketItem(ModInnet.FUEL_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(BossToolsItemGroups.tab_normal)));

    //Oil Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", ()-> new OilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> OIL_STILL = FLUIDS.register("oil", ()-> new OilFluid.Source());
    public static RegistryObject<FlowingFluidBlock> OIL_BLOCK = BLOCKS.register("oil",() -> new FlowingFluidBlock(ModInnet.OIL_STILL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(ModInnet.OIL_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(BossToolsItemGroups.tab_normal)));

    //Item
    public static final RegistryObject<Item> TORCHITEM = ITEMS.register("coal_torch", () -> new CoalTorchItem(COAL_TORCH_BLOCK.get(), WALL_COAL_TORCH_BLOCK.get(),new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    //Spawn Eggs
    public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg", () -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> PYGRO_SPAWN_EGG = ITEMS.register("pygro_spawn_egg",() -> new ModSpawnEggs(PYGRO, -3381760, -6750208, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> MOGLER_SPAWN_EGG = ITEMS.register("mogler_spawn_egg",() -> new ModSpawnEggs(MOGLER, -13312, -3407872, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> ALIEN_ZOMBIE_SPAWN_EGG = ITEMS.register("alien_zombie_spawn_egg",() -> new ModSpawnEggs(ALIEN_ZOMBIE, -14804199, -16740159, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> STAR_CRAWLER_SPAWN_EGG = ITEMS.register("star_crawler_spawn_egg",() -> new ModSpawnEggs(STAR_CRAWLER, -13421773, -16724788, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));


    public static final RegistryObject<Item> CHESE = ITEMS.register("chesse", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_normal).food((new Food.Builder()).hunger(4).saturation(3f).build())));
    public static final RegistryObject<Item> COMPRESSED_DESH = ITEMS.register("compressed_desh", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_SILICON = ITEMS.register("compressed_silicon", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register("compressed_steel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(new Item.Properties().group(BossToolsItemGroups.tab_basics).maxDamage(9).setNoRepair()));
    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DESH_INGOT = ITEMS.register("desh_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> OXYGEN_GEAR = ITEMS.register("oxygen_gear", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_CASING = ITEMS.register("engine_casing", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FAN = ITEMS.register("engine_fan", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DESH_PLATE = ITEMS.register("desh_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_INGOT = ITEMS.register("silicon_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> ROCKET_NOSE_CONE = ITEMS.register("rocket_nose_cone", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_ENGINE = ITEMS.register("diamond_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_ENGINE = ITEMS.register("iron_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_ENGINE = ITEMS.register("golden_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_TANK = ITEMS.register("iron_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_TANK = ITEMS.register("golden_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_TANK = ITEMS.register("diamond_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));


    //Netherite Space Suit Items
    public static final RegistryObject<Item> NETHERITE_OXYGEN_MASK = ITEMS.register("netherite_oxygen_mask", () -> NetheriteSpaceSuit.NETHERITE_OXYGEN_MASK);
    public static final RegistryObject<Item> NETHERITE_SPACE_SUIT = ITEMS.register("netherite_space_suit", () -> NetheriteSpaceSuit.NETHERITE_SPACE_SUIT);
    public static final RegistryObject<Item> NETHERITE_SPACE_PANTS = ITEMS.register("netherite_space_pants", () -> NetheriteSpaceSuit.NETHERITE_SPACE_PANTS);
    public static final RegistryObject<Item> NETHERITE_SPACE_BOOTS = ITEMS.register("netherite_space_boots", () -> NetheriteSpaceSuit.NETHERITE_SPACE_BOOTS);

    //Space Suit Items
    public static final RegistryObject<Item> OXYGEN_MASK = ITEMS.register("oxygen_mask", () -> SpaceSuit.OXYGEN_MASK);
    public static final RegistryObject<Item> SPACE_SUIT = ITEMS.register("space_suit", () -> SpaceSuit.SPACE_SUIT);
    public static final RegistryObject<Item> SPACE_PANTS = ITEMS.register("space_pants", () -> SpaceSuit.SPACE_PANTS);
    public static final RegistryObject<Item> SPACE_BOOTS = ITEMS.register("space_boots", () -> SpaceSuit.SPACE_BOOTS);


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
            return Ingredient.fromStacks(new ItemStack(ModInnet.STEEL_INGOT.get(), (int) (1)));
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
    public static final RegistryObject<Item> FLAG_ITEM = ITEMS.register("flag", () -> new TallBlockItem(FLAG_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BLUE_ITEM = ITEMS.register("flag_blue", () -> new TallBlockItem(FLAG_BLUE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BROWN_ITEM = ITEMS.register("flag_brown", () -> new TallBlockItem(FLAG_BROWN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_CYAN_ITEM = ITEMS.register("flag_cyan", () -> new TallBlockItem(FLAG_CYAN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GRAY_ITEM = ITEMS.register("flag_gray", () -> new TallBlockItem(FLAG_GRAY_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GREEN_ITEM = ITEMS.register("flag_green", () -> new TallBlockItem(FLAG_GREEN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIGHT_BLUE_ITEM = ITEMS.register("flag_light_blue", () -> new TallBlockItem(FLAG_LIGHT_BLUE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIME_ITEM = ITEMS.register("flag_lime", () -> new TallBlockItem(FLAG_LIME_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_MAGENTA_ITEM = ITEMS.register("flag_magenta", () -> new TallBlockItem(FLAG_MAGENTA_BLOCk.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_ORANGE_ITEM = ITEMS.register("flag_orange", () -> new TallBlockItem(FLAG_ORANGE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PINK_ITEM = ITEMS.register("flag_pink", () -> new TallBlockItem(FLAG_PINK_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PURPLE_ITEM = ITEMS.register("flag_purple", () -> new TallBlockItem(FLAG_PURPLE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_RED_ITEM = ITEMS.register("flag_red", () -> new TallBlockItem(FLAG_RED_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_YELLOW_ITEM = ITEMS.register("flag_yellow", () -> new TallBlockItem(FLAG_YELLOW_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));

    //Recpies
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "boss_tools");
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_BLASTING = RECIPE_SERIALIZERS.register("blasting", () -> new BlastingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_COMPRESSING = RECIPE_SERIALIZERS.register("compressing", () -> new CompressingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_GENERATING = RECIPE_SERIALIZERS.register("generating", () -> new GeneratingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_OXYGENMAKING = RECIPE_SERIALIZERS.register("oxygenmaking", () -> new OxygenMakingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_WORKBENCHING = RECIPE_SERIALIZERS.register("workbenching", () -> new WorkbenchingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_FUELREFINING = RECIPE_SERIALIZERS.register("fuelrefining", () -> new FuelRefiningRecipeSerializer());
    
    //RocketParts
    public static final IForgeRegistry<RocketPart> ROCKET_PARTS_REGISTRY = new RegistryBuilder<RocketPart>().setType(RocketPart.class).setName(new ResourceLocation("boss_tools", "rocket_part")).create();
    public static final DeferredRegister<RocketPart> ROCKET_PARTS = DeferredRegister.create(ROCKET_PARTS_REGISTRY, "boss_tools");

    public static final RegistryObject<RocketPart> ROCKET_PART_EMPTY = ROCKET_PARTS.register("emtpy", () -> RocketPart.EMPTY);
    public static final RegistryObject<RocketPart> ROCKET_PART_NOSE = ROCKET_PARTS.register("nose", () -> new RocketPart(1));
	public static final RegistryObject<RocketPart> ROCKET_PART_BODY = ROCKET_PARTS.register("body", () -> new RocketPart(6));
	public static final RegistryObject<RocketPart> ROCKET_PART_TANK = ROCKET_PARTS.register("tank", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_FIN_LEFT = ROCKET_PARTS.register("fin_left", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_FIN_RIGHT = ROCKET_PARTS.register("fin_right", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_ENGINE = ROCKET_PARTS.register("engine", () -> new RocketPart(1));
	
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
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            STStructures.setupStructures();
            STStructures2.setupStructures();
            STConfiguredStructures.registerConfiguredStructures();
        });
    }

    public static void biomeModification(final BiomeLoadingEvent event) {
        RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if (event.getName().equals(new ResourceLocation("boss_tools:moon")) && Config.AlienVillageStructure == true) {
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
        //  if (event.getName().equals(new ResourceLocation("boss_tools:mars"))) {
        //       event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        //   }
        if (event.getName().equals(new ResourceLocation("forest")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("desert")) && Config.MeteorStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //venus bullet
        if (event.getName().equals(new ResourceLocation("boss_tools:venus")) && Config.VenusBulletStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //venus tower
        if (event.getName().equals(new ResourceLocation("boss_tools:venus")) && Config.VenusTowerStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_TOWER_CONFIGURED_RUN_DOWN_HOUSE);
        }
        //Oil
        if (event.getName().equals(new ResourceLocation("ocean")) && Config.OILWellStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("deep_cold_ocean")) && Config.OILWellStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("deep_ocean")) && Config.OILWellStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL_CONFIGURED_RUN_DOWN_HOUSE);
        }
        if (event.getName().equals(new ResourceLocation("lukewarm_ocean")) && Config.OILWellStructure == true) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL_CONFIGURED_RUN_DOWN_HOUSE);
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
            //OIl
            Map<Structure<?>, StructureSeparationSettings> tempMap4 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());

            tempMap4.putIfAbsent(STStructures.OIL.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.OIL.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap4;
        }
    }

    //@Override
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN.get(), AlienEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) PYGRO.get(), PygroEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) MOGLER.get(), MoglerEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN_ZOMBIE.get(), AlienZombieEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) STAR_CRAWLER.get(), StarCrawlerEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_1_ROCKET.get(), RocketTier1Entity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_2_ROCKET.get(), RocketTier2Entity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_3_ROCKET.get(), RocketTier3Entity.setCustomAttributes().create());
        });
        event.enqueueWork(() -> {

            BossToolsRecipeTypes.init();

            ICE_SPIKE = register("ice_spike1", ModInnet.MARS_ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
            //Venus Deltas
            DELTAS = register("deltas1", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 8))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
            //Venus Deltas2
            DELTAS2 = register("deltas2", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 6))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
        });
    }

    public static void biomesLoading(final BiomeLoadingEvent event){
        if(event.getName().getPath().equals(BiomeRegistry.mars_ice_spike.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ICE_SPIKE);
        }
        //Venus Deltas
        if(event.getName().getPath().equals(BiomeRegistry.infernal_venus_barrens.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS);
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS2);
        }
    }

}