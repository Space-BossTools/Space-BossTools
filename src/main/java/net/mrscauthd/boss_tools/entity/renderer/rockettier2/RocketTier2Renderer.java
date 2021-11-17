package net.mrscauthd.boss_tools.entity.renderer.rockettier2;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier2Renderer extends MobRenderer<RocketTier2Entity, RocketTier2Model<RocketTier2Entity>> {
    public RocketTier2Renderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new RocketTier2Model(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(RocketTier2Entity entity) {
        return new ResourceLocation("boss_tools:textures/rockets/rocket_t2.png");
    }
}