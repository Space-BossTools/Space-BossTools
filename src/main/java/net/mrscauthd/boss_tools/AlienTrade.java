package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.mrscauthd.boss_tools.AlienJobs;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class AlienTrade {
    public static Map<AlienJobs, Int2ObjectMap<VillagerTrades.ITrade[]>> TRADES = Util.make(Maps.newHashMap(), (p_221237_0_) -> {
        p_221237_0_.put(AlienJobs.JOB1, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.WHEAT, 20, 16, 2), new EmeraldForItemsTrade(Items.POTATO, 26, 16, 2), new EmeraldForItemsTrade(Items.CARROT, 22, 16, 2), new EmeraldForItemsTrade(Items.BEETROOT, 15, 16, 2), new ItemsForEmeraldsTrade(Items.BREAD, 1, 6, 16, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Blocks.PUMPKIN, 6, 12, 10), new ItemsForEmeraldsTrade(Items.PUMPKIN_PIE, 1, 4, 5), new ItemsForEmeraldsTrade(Items.APPLE, 1, 4, 16, 5)}, 3, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.COOKIE, 3, 18, 10), new EmeraldForItemsTrade(Blocks.MELON, 4, 12, 20)}, 4, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Blocks.CAKE, 1, 1, 12, 15), new SuspiciousStewForEmeraldTrade(Effects.NIGHT_VISION, 100, 15), new SuspiciousStewForEmeraldTrade(Effects.JUMP_BOOST, 160, 15), new SuspiciousStewForEmeraldTrade(Effects.WEAKNESS, 140, 15), new SuspiciousStewForEmeraldTrade(Effects.BLINDNESS, 120, 15), new SuspiciousStewForEmeraldTrade(Effects.POISON, 280, 15), new SuspiciousStewForEmeraldTrade(Effects.SATURATION, 7, 15)}, 5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.GOLDEN_CARROT, 3, 3, 30), new ItemsForEmeraldsTrade(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
        p_221237_0_.put(AlienJobs.JOB2, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.STRING, 20, 16, 2), new EmeraldForItemsTrade(Items.COAL, 10, 16, 2), new ItemsForEmeraldsAndItemsTrade(Items.COD, 6, Items.COOKED_COD, 6, 16, 1), new ItemsForEmeraldsTrade(Items.COD_BUCKET, 3, 1, 16, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COD, 15, 16, 10), new ItemsForEmeraldsAndItemsTrade(Items.SALMON, 6, Items.COOKED_SALMON, 6, 16, 5), new ItemsForEmeraldsTrade(Items.CAMPFIRE, 2, 1, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.SALMON, 13, 16, 20), new EnchantedItemForEmeraldsTrade(Items.FISHING_ROD, 3, 3, 10, 0.2F)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.TROPICAL_FISH, 6, 12, 30)}, 5, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.PUFFERFISH, 4, 12, 30), new EmeraldForVillageTypeItemTrade(1, 12, 30, ImmutableMap.<VillagerType, Item>builder().put(VillagerType.PLAINS, Items.OAK_BOAT).put(VillagerType.TAIGA, Items.SPRUCE_BOAT).put(VillagerType.SNOW, Items.SPRUCE_BOAT).put(VillagerType.DESERT, Items.JUNGLE_BOAT).put(VillagerType.JUNGLE, Items.JUNGLE_BOAT).put(VillagerType.SAVANNA, Items.ACACIA_BOAT).put(VillagerType.SWAMP, Items.DARK_OAK_BOAT).build())})));
        p_221237_0_.put(AlienJobs.JOB3, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Blocks.WHITE_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.BROWN_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.BLACK_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.GRAY_WOOL, 18, 16, 2), new ItemsForEmeraldsTrade(Items.SHEARS, 2, 1, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.WHITE_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.GRAY_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.BLACK_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.LIGHT_BLUE_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.LIME_DYE, 12, 16, 10), new ItemsForEmeraldsTrade(Blocks.WHITE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.YELLOW_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIME_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.PINK_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.CYAN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BROWN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.GREEN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.RED_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLACK_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.WHITE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIME_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.PINK_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.CYAN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BROWN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.GREEN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.RED_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLACK_CARPET, 1, 4, 16, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.YELLOW_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.LIGHT_GRAY_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.ORANGE_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.RED_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.PINK_DYE, 12, 16, 20), new ItemsForEmeraldsTrade(Blocks.WHITE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.YELLOW_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.RED_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BLACK_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BLUE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BROWN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.CYAN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.GRAY_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.GREEN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIME_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.MAGENTA_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.ORANGE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.PINK_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.PURPLE_BED, 3, 1, 12, 10)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.BROWN_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.PURPLE_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.BLUE_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.GREEN_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.MAGENTA_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.CYAN_DYE, 12, 16, 30), new ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 12, 15)}, 5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.PAINTING, 2, 3, 30)})));
        p_221237_0_.put(AlienJobs.JOB4, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.STICK, 32, 16, 2), new ItemsForEmeraldsTrade(Items.ARROW, 1, 16, 1), new ItemsForEmeraldsAndItemsTrade(Blocks.GRAVEL, 10, Items.FLINT, 10, 12, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new ItemsForEmeraldsTrade(Items.BOW, 2, 1, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.STRING, 14, 16, 20), new ItemsForEmeraldsTrade(Items.CROSSBOW, 3, 1, 10)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.FEATHER, 24, 16, 30), new EnchantedItemForEmeraldsTrade(Items.BOW, 2, 3, 15)}, 5, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.TRIPWIRE_HOOK, 8, 12, 30), new EnchantedItemForEmeraldsTrade(Items.CROSSBOW, 3, 3, 15), new ItemWithPotionForEmeraldsAndItemsTrade(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30)})));
        p_221237_0_.put(AlienJobs.JOB5, gatAsIntMap(ImmutableMap.<Integer, VillagerTrades.ITrade[]>builder().put(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new EnchantedBookForEmeraldsTrade(1), new ItemsForEmeraldsTrade(Blocks.BOOKSHELF, 9, 1, 12, 1)}).put(2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.BOOK, 4, 12, 10), new EnchantedBookForEmeraldsTrade(5), new ItemsForEmeraldsTrade(Items.LANTERN, 1, 1, 5)}).put(3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.INK_SAC, 5, 12, 20), new EnchantedBookForEmeraldsTrade(10), new ItemsForEmeraldsTrade(Items.GLASS, 1, 4, 10)}).put(4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.WRITABLE_BOOK, 2, 12, 30), new EnchantedBookForEmeraldsTrade(15), new ItemsForEmeraldsTrade(Items.CLOCK, 5, 1, 15), new ItemsForEmeraldsTrade(Items.COMPASS, 4, 1, 15)}).put(5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.NAME_TAG, 20, 1, 30)}).build()));
        p_221237_0_.put(AlienJobs.JOB6, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new ItemsForEmeraldsTrade(Items.MAP, 7, 1, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.GLASS_PANE, 11, 16, 10), new EmeraldForMapTrade(13, Structure.MONUMENT, MapDecoration.Type.MONUMENT, 12, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COMPASS, 1, 12, 20), new EmeraldForMapTrade(14, Structure.WOODLAND_MANSION, MapDecoration.Type.MANSION, 12, 10)}, 4, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.ITEM_FRAME, 7, 1, 15), new ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 15)}, 5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Items.GLOBE_BANNER_PATTERN, 8, 1, 30)})));
        p_221237_0_.put(AlienJobs.JOB7, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.ROTTEN_FLESH, 32, 16, 2), new ItemsForEmeraldsTrade(Items.REDSTONE, 1, 2, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.GOLD_INGOT, 3, 12, 10), new ItemsForEmeraldsTrade(Items.LAPIS_LAZULI, 1, 1, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_FOOT, 2, 12, 20), new ItemsForEmeraldsTrade(Blocks.GLOWSTONE, 4, 1, 12, 10)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new EmeraldForItemsTrade(Items.GLASS_BOTTLE, 9, 12, 30), new ItemsForEmeraldsTrade(Items.ENDER_PEARL, 5, 1, 15)}, 5, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.NETHER_WART, 22, 12, 30), new ItemsForEmeraldsTrade(Items.EXPERIENCE_BOTTLE, 3, 1, 30)})));
        p_221237_0_.put(AlienJobs.JOB8, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_LEGGINGS), 7, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_BOOTS), 4, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_HELMET), 5, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_CHESTPLATE), 9, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_BOOTS), 1, 1, 12, 5, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_LEGGINGS), 3, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.LAVA_BUCKET, 1, 12, 20), new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 20), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_HELMET), 1, 1, 12, 10, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 4, 1, 12, 10, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.SHIELD), 5, 1, 12, 10, 0.2F)}, 4, new VillagerTrades.ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_LEGGINGS, 14, 3, 15, 0.2F), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_BOOTS, 8, 3, 15, 0.2F)}, 5, new VillagerTrades.ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_HELMET, 8, 3, 30, 0.2F), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_CHESTPLATE, 16, 3, 30, 0.2F)})));
        p_221237_0_.put(AlienJobs.JOB9, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2F), new EnchantedItemForEmeraldsTrade(Items.IRON_SWORD, 2, 3, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 24, 12, 20)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2F)}, 5, new VillagerTrades.ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_SWORD, 8, 3, 30, 0.2F)})));
        p_221237_0_.put(AlienJobs.JOB10, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 30, 12, 20), new EnchantedItemForEmeraldsTrade(Items.IRON_AXE, 1, 3, 10, 0.2F), new EnchantedItemForEmeraldsTrade(Items.IRON_SHOVEL, 2, 3, 10, 0.2F), new EnchantedItemForEmeraldsTrade(Items.IRON_PICKAXE, 3, 3, 10, 0.2F), new ItemsForEmeraldsTrade(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2F)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2F), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2F)}, 5, new VillagerTrades.ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2F)})));
        p_221237_0_.put(AlienJobs.JOB11, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.CHICKEN, 14, 16, 2), new EmeraldForItemsTrade(Items.PORKCHOP, 7, 16, 2), new EmeraldForItemsTrade(Items.RABBIT, 4, 16, 2), new ItemsForEmeraldsTrade(Items.RABBIT_STEW, 1, 1, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new ItemsForEmeraldsTrade(Items.COOKED_CHICKEN, 1, 8, 16, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.MUTTON, 7, 16, 20), new EmeraldForItemsTrade(Items.BEEF, 10, 16, 20)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.DRIED_KELP_BLOCK, 10, 12, 30)}, 5, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.SWEET_BERRIES, 10, 12, 30)})));
        p_221237_0_.put(AlienJobs.JOB12, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.LEATHER, 6, 16, 2), new DyedArmorForEmeraldsTrade(Items.LEATHER_LEGGINGS, 3), new DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 5), new DyedArmorForEmeraldsTrade(Items.LEATHER_BOOTS, 4, 12, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_HIDE, 9, 12, 20), new DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new DyedArmorForEmeraldsTrade(Items.LEATHER_HORSE_ARMOR, 6, 12, 15)}, 5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2F), new DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 30)})));
        p_221237_0_.put(AlienJobs.JOB13, gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.CLAY_BALL, 10, 16, 2), new ItemsForEmeraldsTrade(Items.BRICK, 1, 10, 16, 1)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Blocks.STONE, 20, 16, 10), new ItemsForEmeraldsTrade(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5)}, 3, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Blocks.GRANITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.ANDESITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.DIORITE, 16, 16, 20), new ItemsForEmeraldsTrade(Blocks.POLISHED_ANDESITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_DIORITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_GRANITE, 1, 4, 16, 10)}, 4, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.QUARTZ, 12, 12, 30), new ItemsForEmeraldsTrade(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.RED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15)}, 5, new VillagerTrades.ITrade[]{new ItemsForEmeraldsTrade(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new ItemsForEmeraldsTrade(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30)})));
    });

    private static Int2ObjectMap<VillagerTrades.ITrade[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade {
        private final ItemStack sellingItem;
        private final int emeraldCount;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
        }

        public ItemsForEmeraldsTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, 12, xpValue);
        }

        public ItemsForEmeraldsTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
        }

        public ItemsForEmeraldsTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(sellingItem, emeraldCount, sellingItemCount, maxUses, xpValue, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingItem = sellingItem;
            this.emeraldCount = emeraldCount;
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return null;
        }
    }

    static class EmeraldForItemsTrade implements VillagerTrades.ITrade {
        private final Item tradeItem;
        private final int count;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
            this.tradeItem = tradeItemIn.asItem();
            this.count = countIn;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class DyedArmorForEmeraldsTrade implements VillagerTrades.ITrade {
        private final Item tradeItem;
        private final int price;
        private final int maxUses;
        private final int xpValue;

        public DyedArmorForEmeraldsTrade(Item itemIn, int priceIn) {
            this(itemIn, priceIn, 12, 1);
        }

        public DyedArmorForEmeraldsTrade(Item tradeItemIn, int priceIn, int maxUsesIn, int xpValueIn) {
            this.tradeItem = tradeItemIn;
            this.price = priceIn;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.EMERALD, this.price);
            ItemStack itemstack1 = new ItemStack(this.tradeItem);
            if (this.tradeItem instanceof DyeableArmorItem) {
                List<DyeItem> list = Lists.newArrayList();
                list.add(getRandomDyeItem(rand));
                if (rand.nextFloat() > 0.7F) {
                    list.add(getRandomDyeItem(rand));
                }

                if (rand.nextFloat() > 0.8F) {
                    list.add(getRandomDyeItem(rand));
                }

                itemstack1 = IDyeableArmorItem.dyeItem(itemstack1, list);
            }

            return new MerchantOffer(itemstack, itemstack1, this.maxUses, this.xpValue, 0.2F);
        }

        private static DyeItem getRandomDyeItem(Random p_221232_0_) {
            return DyeItem.getItem(DyeColor.byId(p_221232_0_.nextInt(16)));
        }
    }

    static class EnchantedBookForEmeraldsTrade implements VillagerTrades.ITrade {
        private final int xpValue;

        public EnchantedBookForEmeraldsTrade(int xpValueIn) {
            this.xpValue = xpValueIn;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::canVillagerTrade).collect(Collectors.toList());
            Enchantment enchantment = list.get(rand.nextInt(list.size()));
            int i = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemstack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i));
            int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
            if (enchantment.isTreasureEnchantment()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, 12, this.xpValue, 0.2F);
        }
    }

    static class ItemsForEmeraldsAndItemsTrade implements VillagerTrades.ITrade {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItemsTrade(IItemProvider buyingItem, int buyingItemCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
            this(buyingItem, buyingItemCount, 1, sellingItem, sellingItemCount, maxUses, xpValue);
        }

        public ItemsForEmeraldsAndItemsTrade(IItemProvider buyingItem, int buyingItemCount, int emeraldCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
            this.buyingItem = new ItemStack(buyingItem);
            this.buyingItemCount = buyingItemCount;
            this.emeraldCount = emeraldCount;
            this.sellingItem = new ItemStack(sellingItem);
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }
    static class EnchantedItemForEmeraldsTrade implements VillagerTrades.ITrade {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EnchantedItemForEmeraldsTrade(Item p_i50535_1_, int emeraldCount, int maxUses, int xpValue) {
            this(p_i50535_1_, emeraldCount, maxUses, xpValue, 0.05F);
        }

        public EnchantedItemForEmeraldsTrade(Item sellItem, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingStack = new ItemStack(sellItem);
            this.emeraldCount = emeraldCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            int i = 5 + rand.nextInt(15);
            ItemStack itemstack = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(this.sellingStack.getItem()), i, false);
            int j = Math.min(this.emeraldCount + i, 64);
            ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
            return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class SuspiciousStewForEmeraldTrade implements VillagerTrades.ITrade {
        final Effect effect;
        final int duration;
        final int xpValue;
        private final float priceMultiplier;

        public SuspiciousStewForEmeraldTrade(Effect effectIn, int durationIn, int xpValue) {
            this.effect = effectIn;
            this.duration = durationIn;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.addEffect(itemstack, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemstack, 12, this.xpValue, this.priceMultiplier);
        }
    }

    static class EmeraldForVillageTypeItemTrade implements VillagerTrades.ITrade {
        private final Map<VillagerType, Item> villagerTypeItems;
        private final int count;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForVillageTypeItemTrade(int count, int maxUsesIn, int xpValueIn, Map<VillagerType, Item> villagerTypeItemsIn) {
            Registry.VILLAGER_TYPE.stream().filter((villagerType) -> {
                return !villagerTypeItemsIn.containsKey(villagerType);
            }).findAny().ifPresent((villagerType) -> {
                throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getKey(villagerType));
            });
            this.villagerTypeItems = villagerTypeItemsIn;
            this.count = count;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, Random rand) {
            if (trader instanceof IVillagerDataHolder) {
                ItemStack itemstack = new ItemStack(this.villagerTypeItems.get(((IVillagerDataHolder)trader).getVillagerData().getType()), this.count);
                return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, 0.05F);
            } else {
                return null;
            }
        }
    }

    static class EmeraldForMapTrade implements VillagerTrades.ITrade {
        private final int count;
        private final Structure<?> structureName;
        private final MapDecoration.Type mapDecorationType;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForMapTrade(int count, Structure<?> structureName, MapDecoration.Type mapDecorationType, int maxUses, int xpValue) {
            this.count = count;
            this.structureName = structureName;
            this.mapDecorationType = mapDecorationType;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, Random rand) {
            if (!(trader.world instanceof ServerWorld)) {
                return null;
            } else {
                ServerWorld serverworld = (ServerWorld) trader.world;
                //BlockPos blockpos = serverworld.getStructureLocation(this.structureName, trader.getPosition(), 100, true);
                BlockPos blockpos = null;
                if (blockpos != null) {
                    ItemStack itemstack = FilledMapItem.setupNewMap(serverworld, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                    FilledMapItem.func_226642_a_(serverworld, itemstack);
                    MapData.addTargetDecoration(itemstack, blockpos, "+", this.mapDecorationType);
                    itemstack.setDisplayName(new TranslationTextComponent("filled_map." + this.structureName.getStructureName().toLowerCase(Locale.ROOT)));
                    return new MerchantOffer(new ItemStack(Items.EMERALD, this.count), new ItemStack(Items.COMPASS), itemstack, this.maxUses, this.xpValue, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }

    static class ItemWithPotionForEmeraldsAndItemsTrade implements VillagerTrades.ITrade {
        /** An ItemStack that can have potion effects written to it. */
        private final ItemStack potionStack;
        private final int potionCount;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final Item buyingItem;
        private final int buyingItemCount;
        private final float priceMultiplier;

        public ItemWithPotionForEmeraldsAndItemsTrade(Item buyingItem, int buyingItemCount, Item p_i50526_3_, int p_i50526_4_, int emeralds, int maxUses, int xpValue) {
            this.potionStack = new ItemStack(p_i50526_3_);
            this.emeraldCount = emeralds;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.buyingItem = buyingItem;
            this.buyingItemCount = buyingItemCount;
            this.potionCount = p_i50526_4_;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.EMERALD, this.emeraldCount);
            List<Potion> list = Registry.POTION.stream().filter((potion) -> {
                return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
            }).collect(Collectors.toList());
            Potion potion = list.get(rand.nextInt(list.size()));
            ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(this.potionStack.getItem(), this.potionCount), potion);
            return new MerchantOffer(itemstack, new ItemStack(this.buyingItem, this.buyingItemCount), itemstack1, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }
}