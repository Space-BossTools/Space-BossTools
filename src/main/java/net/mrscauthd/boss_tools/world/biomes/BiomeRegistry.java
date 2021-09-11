package net.mrscauthd.boss_tools.world.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.block.*;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistry {
    public static Biome moon;

    public static Biome mars;
    public static Biome mars_ice_spike;

    public static Biome venus;
    public static Biome venus_hills;
    public static Biome infernal_venus_barrens;

    public static Biome mercury;
    public static Biome mercury_magma;

    public static Biome orbit;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        //Moon Biome
        if (moon == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MoonsandBlock.block.getDefaultState(), MoonsandBlock.block.getDefaultState(), MoonsandBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            moon = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(moon.setRegistryName("boss_tools:moon"));
        }

        //Mars Biome
        if (mars == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MarssandBlock.block.getDefaultState(), MarssandBlock.block.getDefaultState(), MarssandBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            mars = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars.setRegistryName("boss_tools:mars"));
        }

        //Mars ice Spike Biome
        if (mars_ice_spike == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), MarsStoneBlock.block.getDefaultState(), MarsStoneBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            mars_ice_spike = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.ICY).depth(0.1f).scale(0.12f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars_ice_spike.setRegistryName("boss_tools:mars_ice_spike"));
        }

        //Venus Biome
        if (venus == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            venus = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.2f).scale(0.02f).temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(venus.setRegistryName("boss_tools:venus"));
        }

        //Infernal Venus Barrens Biome
        if (infernal_venus_barrens == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
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

            infernal_venus_barrens = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.2f).scale(0.12f).temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(infernal_venus_barrens.setRegistryName("boss_tools:infernal_venus_barrens"));
        }

        //Venus Hills Biome
        if (venus_hills == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-3044526).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-3044526).withFoliageColor(7842607).withGrassColor(9551193).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(VenusSandBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState(), VenusSandStoneBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            venus_hills = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.3f).scale(0.42f).temperature(1.5f).downfall(1f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(venus_hills.setRegistryName("boss_tools:venus_hills"));
        }

        //Mercury Biome
        if (mercury == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState())));
            DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            mercury = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(1f).scale(0.2f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mercury.setRegistryName("boss_tools:mercury"));
        }

        //Mercury Magama Biome
        if (mercury_magma == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(MercurystoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState(), MrrcuryCobblestoneBlock.block.getDefaultState())));
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

            mercury_magma = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(1f).scale(0.2f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mercury_magma.setRegistryName("boss_tools:mercury_magma"));
        }

        //Orbit
        if (orbit == null) {
            BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-16777216).setWaterColor(4159204).setWaterFogColor(329011).withSkyColor(-16777216).withFoliageColor(7842607).withGrassColor(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState())));
            MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

            orbit = new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1f).scale(0.2f).temperature(1.6f).downfall(0f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy()).withGenerationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(orbit.setRegistryName("boss_tools:orbit"));
        }

    }
}
