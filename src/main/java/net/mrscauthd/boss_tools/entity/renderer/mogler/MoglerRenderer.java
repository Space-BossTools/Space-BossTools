package net.mrscauthd.boss_tools.entity.renderer.mogler;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

@OnlyIn(Dist.CLIENT)
public class MoglerRenderer extends MobRenderer<HoglinEntity, MoglerModel<HoglinEntity>> {
    private static final ResourceLocation field_239382_a_ = new ResourceLocation("boss_tools:textures/entities/mogler_entity.png");

    public MoglerRenderer(EntityRendererManager p_i232470_1_) {
        super(p_i232470_1_, new MoglerModel<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(HoglinEntity entity) {
        return field_239382_a_;
    }

    protected boolean func_230495_a_(HoglinEntity p_230495_1_) {
        return p_230495_1_.func_234364_eK_();
    }
}