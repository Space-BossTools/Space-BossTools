package net.mrscauthd.boss_tools;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PiglinModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.entity.PygroEntity;
import net.mrscauthd.boss_tools.PygroModel;

@OnlyIn(Dist.CLIENT)
public class PygroRenderer extends BipedRenderer<MobEntity, PygroModel<MobEntity>> {

    public PygroRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, func_239395_a_(false), (float) 0.5, 1.0019531F, 1.0F, 1.0019531F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.02F)));
    }

    private static PygroModel<MobEntity> func_239395_a_(boolean p_239395_0_) {
        PygroModel<MobEntity> piglinmodel = new PygroModel<>(0.0F, 64, 64);
        if (p_239395_0_) {
            piglinmodel.field_239116_b_.showModel = false;
        }
        return piglinmodel;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(MobEntity entity) {
       // return new ResourceLocation("textures/entity/piglin/piglin.png");
        return new ResourceLocation("boss_tools:textures/pygro.png");
    }

    protected boolean func_230495_a_(MobEntity p_230495_1_) {
        return p_230495_1_ instanceof PygroEntity.CustomEntity && ((PygroEntity.CustomEntity)p_230495_1_).func_242336_eL();
    }
}