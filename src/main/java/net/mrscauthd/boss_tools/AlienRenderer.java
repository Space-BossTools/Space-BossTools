package net.mrscauthd.boss_tools;


import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.entity.AlienEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AlienRenderer extends MobRenderer<AlienEntity, AlienModel<AlienEntity>> {
    public AlienRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AlienModel<>(), 0.5f);
    }

    //protected static final ResourceLocation TEXTURE = new ResourceLocation("boss_tools","textures/entity/alien.png");
    //protected static final ResourceLocation TEXTURE2 = new ResourceLocation("boss_tools","textures/entity/alien.png");

    @Override
    public ResourceLocation getEntityTexture(AlienEntity entity) {
    return entity.getTexture();
    }
}