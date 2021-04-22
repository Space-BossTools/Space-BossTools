package net.mrscauthd.boss_tools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
//import fr.lmf.structuretutorial.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;

public class StructurePool {

    public static final JigsawPattern startPool;

    public static void init() {
    }

    static {

        startPool = JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("boss_tools/alien_village1"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.func_242849_a(new ResourceLocation("boss_tools", "alien_village1").toString()), 2)), JigsawPattern.PlacementBehaviour.RIGID));
        JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation("boss_tools", "boss_tools/alien_village1"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.func_242849_a(new ResourceLocation("boss_tools", "alien_village1").toString()), 1)), JigsawPattern.PlacementBehaviour.RIGID));

    }

}