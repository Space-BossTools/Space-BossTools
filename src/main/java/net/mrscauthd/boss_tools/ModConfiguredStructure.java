package net.mrscauthd.boss_tools;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class ModConfiguredStructure {

    public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> TUTORIAL_STRUCTURE = ModStructure.STRUCTURE.withConfiguration(new VillageConfig(() -> {
        return StructurePool.startPool;
    }, 7));

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation("boss_tools", "configured_tutorial_structure"), TUTORIAL_STRUCTURE);
    }

}