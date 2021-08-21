package net.mrscauthd.boss_tools.events;

import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.block.*;

public class BiomeRegisrtyEvents {
    public static Biome moon_biome;
    public static Biome mars_biome;
    public static Biome mars_ice_spike_biome;
    public static Biome venus_biome;
    public static Biome infernal_venus_barrens_biome;
    public static Biome venus_hills_biome;
    public static Biome mercury_biome;
    public static Biome mercury_magma_biome;
    public static Biome orbit_biome;
    public static class BiomeRegisterHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            //Moon Biome
            if (moon_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MoonsandBlock.block.getDefaultState(), MoonsandBlock.block.getDefaultState(), MoonsandBlock.block.getDefaultState())));
                biomeGenerationSettings.withStructure(StructureFeatures.PILLAGER_OUTPOST);
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                moon_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(moon_biome.setRegistryName("boss_tools:moon_biome"));
            }
            //Mars Biome
            if (mars_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MarssandBlock.block.getDefaultState(), MarssandBlock.block.getDefaultState(), MarssandBlock.block.getDefaultState())));
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                mars_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(mars_biome.setRegistryName("boss_tools:mars_biome"));
            }
            //Mars ice Spike Biome
            if (mars_ice_spike_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), MarsStoneBlock.block.getDefaultState(), MarsStoneBlock.block.getDefaultState())));
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                mars_ice_spike_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.ICY).depth(0.1f).scale(0.12f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(mars_ice_spike_biome.setRegistryName("boss_tools:mars_ice_spike_biome"));
            }
            //Venus Biome
            if (venus_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                venus_biome = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(venus_biome.setRegistryName("boss_tools:venus_biome"));
            }
            //Infernal Venus Barrens Biome
            if (infernal_venus_barrens_biome == null) {
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
                infernal_venus_barrens_biome = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.2f).scale(0.12f)
                .temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy())
                .withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(infernal_venus_barrens_biome.setRegistryName("boss_tools:infernal_venus_barrens_biome"));
            }
            //Venus Hills Biome
            if (venus_hills_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                venus_hills_biome = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.3f).scale(0.42f).temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(venus_hills_biome.setRegistryName("boss_tools:venus_hills_biome"));
            }
            //Mercury Biome
            if (mercury_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState())));
                DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                mercury_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(1f).scale(0.2f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(mercury_biome.setRegistryName("boss_tools:mercury_biome"));
            }
            //Mercury Magama Biome
            if (mercury_magma_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011)
                .withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder()
                .withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MercurystoneBlock.block.getDefaultState(),
                MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState())));
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
                mercury_magma_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(1f).scale(0.2f).temperature(1.6f)
                .downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy())
                .withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(mercury_magma_biome.setRegistryName("boss_tools:mercury_magma_biome"));
            }
            if (orbit_biome == null) {
                BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
                BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState())));
                MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
                orbit_biome = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1f).scale(0.2f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
                event.getRegistry().register(orbit_biome.setRegistryName("boss_tools:orbit_biome"));
            }
        }
    }
}
