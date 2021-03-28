package net.mrscauthd.boss_tools;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.entity.AlienEntity;

public class AlienRenderer extends MobRenderer<AlienEntity, AlienModel<AlienEntity>> {
    public AlienRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AlienModel<>(), 0.5f);
    }

    protected static final ResourceLocation TEXTURE = new ResourceLocation("boss_tools:textures/entity_alien.png");

    @Override
    public ResourceLocation getEntityTexture(AlienEntity entity) {
        return TEXTURE;
    }
}