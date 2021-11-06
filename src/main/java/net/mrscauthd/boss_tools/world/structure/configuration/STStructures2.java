package net.mrscauthd.boss_tools.world.structure.configuration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.world.structure.CrimsonStructure;
import net.mrscauthd.boss_tools.world.structure.VenusBulletStructure;
import net.mrscauthd.boss_tools.world.structure.VenusTowerStructure;

import java.util.HashMap;
import java.util.Map;

public class STStructures2 {

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, "boss_tools");

    public static final RegistryObject<Structure<NoFeatureConfig>> VENUS_BULLET = DEFERRED_REGISTRY_STRUCTURE.register("venus_bullet", () -> (new VenusBulletStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> VENUS_TOWER = DEFERRED_REGISTRY_STRUCTURE.register("venus_tower", () -> (new VenusTowerStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> CRIMSON = DEFERRED_REGISTRY_STRUCTURE.register("crimson_village", () -> (new CrimsonStructure(NoFeatureConfig.field_236558_a_)));

    public static void setupStructures() {
        setupMapSpacingAndLand(VENUS_BULLET.get(), new StructureSeparationSettings(29, 19, 1234567890), true);

        setupMapSpacingAndLand(VENUS_TOWER.get(), new StructureSeparationSettings(24, 17, 1234567890), true);

        setupMapSpacingAndLand(CRIMSON.get(), new StructureSeparationSettings(27, 14, 1234567890), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {

        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
             
            DimensionStructuresSettings.field_236191_b_ =
                    ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                            .putAll(DimensionStructuresSettings.field_236191_b_)
                            .put(structure, structureSeparationSettings)
                            .build();


            WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, structureSeparationSettings);
                } else {
                    structureMap.put(structure, structureSeparationSettings);
                }
            });
        }
    }
}
