/*package net.mrscauthd.boss_tools;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
    public class STConfiguredStructures {
        public static StructureFeature<?, ?> CONFIGURED_RUN_DOWN_HOUSE = STStructures.RUN_DOWN_HOUSE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static void registerConfiguredStructures() {
            Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
            Registry.register(registry, new ResourceLocation("boss_tools", "configured_run_down_house"), CONFIGURED_RUN_DOWN_HOUSE);
            //FlatGenerationSettings.field_236932_a_.put(STStructures.RUN_DOWN_HOUSE.get(), CONFIGURED_RUN_DOWN_HOUSE);
        }
    }
*/
package net.mrscauthd.boss_tools;


import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class STConfiguredStructures {

        public static StructureFeature<?, ?> CONFIGURED_RUN_DOWN_HOUSE = STStructures.RUN_DOWN_HOUSE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static StructureFeature<?, ?> METEOR_CONFIGURED_RUN_DOWN_HOUSE = STStructures.METEOR.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static StructureFeature<?, ?> VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE = STStructures2.VENUS_BULLET.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

        public static void registerConfiguredStructures() {
            Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
            Registry.register(registry, new ResourceLocation("boss_tools", "configured_run_down_house"), CONFIGURED_RUN_DOWN_HOUSE);
            Registry.register(registry, new ResourceLocation("boss_tools", "meteor_configured_run_down_house"), METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            Registry.register(registry, new ResourceLocation("boss_tools", "venus_bullet_configured"), VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE);
           // Registry.register(registry, new ResourceLocation("boss_tools", "meteor_dungeon_configured_run_down_house"), METEOR_CONFIGURED_RUN_DOWN_HOUSE);

        //    FlatGenerationSettings.field_236932_a_.put(STStructures.RUN_DOWN_HOUSE, CONFIGURED_RUN_DOWN_HOUSE);
            FlatGenerationSettings.STRUCTURES.put(STStructures.RUN_DOWN_HOUSE.get(), CONFIGURED_RUN_DOWN_HOUSE);
            FlatGenerationSettings.STRUCTURES.put(STStructures.METEOR.get(), METEOR_CONFIGURED_RUN_DOWN_HOUSE);
            FlatGenerationSettings.STRUCTURES.put(STStructures2.VENUS_BULLET.get(), VENUS_BULLET_CONFIGURED_RUN_DOWN_HOUSE);
        }
    }
