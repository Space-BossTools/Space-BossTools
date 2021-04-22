package net.mrscauthd.boss_tools.mixin;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeMaker;
import net.mrscauthd.boss_tools.ModConfiguredStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BiomeMaker.class)
public class BiomeMakerMixin {

    @ModifyVariable(method = "makePlainsBiome", ordinal = 0, at = @At(value = "STORE", ordinal = 0))
    private static BiomeGenerationSettings.Builder addCustomStructure(BiomeGenerationSettings.Builder builder, boolean sunflower) {
        if (!sunflower) {
            builder.withStructure(ModConfiguredStructure.TUTORIAL_STRUCTURE);
        }

        return builder;
    }

}