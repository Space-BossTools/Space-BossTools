package net.mrscauthd.boss_tools.entity.renderer.mogler;

import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerModel;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

@OnlyIn(Dist.CLIENT)
public class MoglerRenderer extends MobRenderer<HoglinEntity, MoglerModel.Modelmoglerium<HoglinEntity>> {
    private static final ResourceLocation field_239382_a_ = new ResourceLocation("boss_tools:textures/moglerium.png");

    public MoglerRenderer(EntityRendererManager p_i232470_1_) {
        super(p_i232470_1_, new MoglerModel.Modelmoglerium<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(HoglinEntity entity) {
        return field_239382_a_;
    }

    protected boolean func_230495_a_(HoglinEntity p_230495_1_) {
        return p_230495_1_.func_234364_eK_();
    }
}