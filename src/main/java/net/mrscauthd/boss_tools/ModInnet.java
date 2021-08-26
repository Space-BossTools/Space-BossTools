
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
import net.minecraft.tileentity.TileEntityType;
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
import net.mrscauthd.boss_tools.armor.SpaceSuit;
import net.mrscauthd.boss_tools.block.*;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.blasting.BlastingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.compressing.CompressingRecipeSerializer;
import net.mrscauthd.boss_tools.entity.AlienSpitEntity;
import net.mrscauthd.boss_tools.entity.AlienZombieEntity;
import net.mrscauthd.boss_tools.entity.MoglerEntity;
import net.mrscauthd.boss_tools.fluid.OilFluid;
import net.mrscauthd.boss_tools.spawneggs.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.pygro.PygroEntity;
import net.mrscauthd.boss_tools.flag.*;
import net.mrscauthd.boss_tools.fluid.FuelFluid;
import net.mrscauthd.boss_tools.item.HammerItem;
import net.mrscauthd.boss_tools.armor.NetheriteSpaceSuit;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock;
import net.mrscauthd.boss_tools.machines.FuelRefineryBlock;
import net.mrscauthd.boss_tools.world.biomes.BiomeRegisrtyEvents;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.feature.MarsIceSpikeFeature;
import net.mrscauthd.boss_tools.feature.VenusDeltas;
import net.mrscauthd.boss_tools.item.CoalTorchItem;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

import net.minecraftforge.registries.ForgeRegistries;
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
    //Entity
    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, "boss_tools");
    //Tile Entity DeferredRegister
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITYS = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "boss_tools");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "boss_tools");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "boss_tools");

    //Entitys
    public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder.create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
    public static RegistryObject<EntityType<?>> PYGRO = ENTITYS.register("pygro", () -> EntityType.Builder.create(PygroEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.6f, 1.8f).build(new ResourceLocation("boss_tools", "pygro").toString()));
    public static RegistryObject<EntityType<?>> MOGLER = ENTITYS.register("mogler", () -> EntityType.Builder.create(MoglerEntity::new, EntityClassification.MONSTER).size(1.4f, 1.4f).build(new ResourceLocation("boss_tools", "mogler").toString()));
    public static RegistryObject<EntityType<?>> ALIEN_ZOMBIE = ENTITYS.register("alien_zombie", () -> EntityType.Builder.create(AlienZombieEntity::new, EntityClassification.MONSTER).size(0.6f, 2.4f).build(new ResourceLocation("boss_tools", "alien_zombie").toString()));


    //Alien Spit Entity
    public static RegistryObject<EntityType<? extends AlienSpitEntity>> ALIEN_SPIT_ENTITY = ENTITYS.register("alien_spit_entity", () -> EntityType.Builder.<AlienSpitEntity>create(AlienSpitEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation("boss_tools", "alien_spit_entity").toString()));

    //pygro
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, "boss_tools");
    public static final RegistryObject<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", ()->new SensorType<>(PygroMobsSensor::new));

    //Blocks
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "boss_tools");

    public static RegistryObject<Block> COALTORCHBLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALLCOALTORCHBLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD).lootFrom(COALTORCHBLOCK.get())));

    //Flag Blocks
    public static RegistryObject<Block> FLAG_BLOCK = BLOCKS.register("flag",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAGB_BLUE_BLOCK = BLOCKS.register("flag_blue",() -> new FlagBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.STONE).notSolid().setLightLevel(s -> 1).setOpaque((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
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


    //Blocks
    public static RegistryObject<Block> FUEL_REFINERY_BLOCK = BLOCKS.register("fuel_refinery",() -> new FuelRefineryBlock.CustomBlock());
    public static RegistryObject<Block> BLAST_FURNACE_BLOCK = BLOCKS.register("blast_furnace",() -> new BlastingFurnaceBlock.CustomBlock());

    //Block Item
    public static final RegistryObject<BlockItem> FUEL_REFINERY_ITEM = ITEMS.register("fuel_refinery", () -> new BlockItem(ModInnet.FUEL_REFINERY_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> BLAST_FURNACE_ITEM = ITEMS.register("blast_furnace", () -> new BlockItem(ModInnet.BLAST_FURNACE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));


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
    public static final RegistryObject<Item> TORCHITEM = ITEMS.register("coal_torch", () -> new CoalTorchItem(COALTORCHBLOCK.get(), WALLCOALTORCHBLOCK.get(),new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    //Spawn Eggs
    public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg", () -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> PYGRO_SPAWN_EGG = ITEMS.register("pygro_spawn_egg",() -> new ModSpawnEggs(PYGRO, -3381760, -6750208, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> MOGLER_SPAWN_EGG = ITEMS.register("mogler_spawn_egg",() -> new ModSpawnEggs(MOGLER, -13312, -3407872, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> ALIEN_ZOMBIE_SPAWN_EGG = ITEMS.register("alien_zombie_spawn_egg",() -> new ModSpawnEggs(ALIEN_ZOMBIE, -14804199, -16740159, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));

    public static final RegistryObject<Item> BARREL = ITEMS.register("barrel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(8)));
    public static final RegistryObject<Item> FUEL_BARREL = ITEMS.register("fuel_barrel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1)));
    public static final RegistryObject<Item> CHESE = ITEMS.register("chesse", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_normal).food((new Food.Builder()).hunger(4).saturation(3f).build())));
    public static final RegistryObject<Item> COMPRESSED_DESH = ITEMS.register("compressed_desh", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_SILICON = ITEMS.register("compressed_silicon", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register("compressed_steel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DIAMOND_ENGINE = ITEMS.register("diamond_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(new Item.Properties().group(BossToolsItemGroups.tab_basics).maxDamage(9).setNoRepair()));
    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DESH_INGOT = ITEMS.register("desh_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> IRON_TANK = ITEMS.register("iron_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_TANK = ITEMS.register("golden_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_GEAR = ITEMS.register("oxygen_gear", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_CASING = ITEMS.register("engine_casing", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FAN = ITEMS.register("engine_fan", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DESH_PLATE = ITEMS.register("desh_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_INGOT = ITEMS.register("silicon_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> ROCKET_NOSE_CONE = ITEMS.register("rocket_nose_cone", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));


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
    public static final RegistryObject<Item> FLAG_BLUE_ITEM = ITEMS.register("flag_blue", () -> new TallBlockItem(FLAGB_BLUE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
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

    //Blasting Recpies
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "boss_tools");
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_BLASTING = RECIPE_SERIALIZERS.register("blasting", () -> new BlastingRecipeSerializer());
    public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_COMPRESSING = RECIPE_SERIALIZERS.register("compressing", () -> new CompressingRecipeSerializer());

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
        });
        event.enqueueWork(() -> {

            BossToolsRecipeTypes.init();

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