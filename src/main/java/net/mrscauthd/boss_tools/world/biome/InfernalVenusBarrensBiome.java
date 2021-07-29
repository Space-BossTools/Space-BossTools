
package net.mrscauthd.boss_tools.world.biome;

import net.mrscauthd.boss_tools.block.VenusSandStoneBlock;
import net.mrscauthd.boss_tools.block.VenusSandBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.Biome;
import net.minecraft.particles.ParticleTypes;

@BossToolsModElements.ModElement.Tag
public class InfernalVenusBarrensBiome extends BossToolsModElements.ModElement {
	public static Biome biome;
	public InfernalVenusBarrensBiome(BossToolsModElements instance) {
		super(instance, 400);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegisterHandler());
	}
	private static class BiomeRegisterHandler {
		@SubscribeEvent
		public void registerBiomes(RegistryEvent.Register<Biome> event) {
			if (biome == null) {
				BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011)
						.withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193)
						.setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
				BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder()
						.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(),
								VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
				biomeGenerationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244170_b);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BASALT_BLOBS);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_DELTA);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);
				DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
				MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
				biome = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.2f).scale(0.12f)
						.temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy())
						.withGenerationSettings(biomeGenerationSettings.build()).build();
				event.getRegistry().register(biome.setRegistryName("boss_tools:infernal_venus_barrens_biome"));
			}
		}
	}
	@Override
	public void init(FMLCommonSetupEvent event) {
	}
}
