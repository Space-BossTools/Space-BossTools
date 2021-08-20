package net.mrscauthd.boss_tools.entity.renderer.alien;


import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.entity.AlienEntity;

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