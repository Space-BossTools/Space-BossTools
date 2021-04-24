package net.mrscauthd.boss_tools;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class AlienStructure extends JigsawStructure {
    public AlienStructure(Codec<VillageConfig> p_i232001_1_) {
        super(p_i232001_1_, -20, true, true);
    }
}