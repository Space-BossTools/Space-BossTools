package net.mrscauthd.boss_tools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;


//import java.rmi.registry.Registry;
import java.util.Locale;

public class ModStructure {

    public static final Structure<VillageConfig> STRUCTURE = register(new ResourceLocation("boss_tools", "tutorial_structure"), new AlienStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES, new StructureSeparationSettings(32, 8, 1075254), true);

    private static <F extends Structure<?>> F register(ResourceLocation p_236394_0_, F p_236394_1_, GenerationStage.Decoration p_236394_2_, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.NAME_STRUCTURE_BIMAP.put(p_236394_0_.toString(), p_236394_1_);
        Structure.STRUCTURE_DECORATION_STAGE_MAP.put(p_236394_1_, p_236394_2_);

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(p_236394_1_, structureSeparationSettings)
                        .build();

        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(p_236394_1_)
                            .build();
        }

        return Registry.register(Registry.STRUCTURE_FEATURE, p_236394_0_, p_236394_1_);
    }
}