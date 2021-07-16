package net.mrscauthd.boss_tools.entity.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class OxygenGearModel <T extends LivingEntity> extends BipedModel<T> {
  //  private final ModelRenderer hat;
    private final ModelRenderer kopf;
    private final ModelRenderer Body;
    private final ModelRenderer armr;
    private final ModelRenderer arml;
    private final ModelRenderer Left_Foot;
    private final ModelRenderer Right_Foot;

    public OxygenGearModel(float modelSize) {
        super(modelSize);
        //his.te = 64;
        //this.textureHeight = 128;

        //WITCH HAT
        textureWidth = 64;
        textureHeight = 64;
        kopf = new ModelRenderer(this);
        kopf.setRotationPoint(0.0F, 0.0F, 0.0F);
        kopf.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
        kopf.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);
        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.setTextureOffset(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
        Body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
        Body.setTextureOffset(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, 0.25F, false);
        Body.setTextureOffset(0, 55).addBox(-2.5F, 1.0F, 2.75F, 5.0F, 8.0F, 1.0F, 0.75F, false);
        armr = new ModelRenderer(this);
        armr.setRotationPoint(-5.0F, 2.0F, 0.0F);
        armr.setTextureOffset(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
        arml = new ModelRenderer(this);
        arml.setRotationPoint(5.0F, 2.0F, 0.0F);
        arml.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
        Left_Foot = new ModelRenderer(this);
        Left_Foot.setRotationPoint(2.0F, 12.0F, 0.0F);
        Left_Foot.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
        Left_Foot.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
        Right_Foot = new ModelRenderer(this);
        Right_Foot.setRotationPoint(-2.0F, 12.0F, 0.0F);
        Right_Foot.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
        Right_Foot.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
		//System.out.println("test1");
        //this.bipedHead.addChild(hat);
        this.bipedHead.addChild(kopf);
    }

    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.kopf);
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	//	setRotationAngles(entityIn, limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
	        boolean flag = entityIn.getTicksElytraFlying() > 4;
        boolean flag1 = entityIn.isActualySwimming();
        kopf.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        if (flag) {
            kopf.rotateAngleX = (-(float)Math.PI / 4F);
        } else if (this.swimAnimation > 0.0F) {
            if (flag1) {
                kopf.rotateAngleX = this.rotLerpRad(this.swimAnimation, kopf.rotateAngleX, (-(float)Math.PI / 4F));
            } else {
                kopf.rotateAngleX = this.rotLerpRad(this.swimAnimation, kopf.rotateAngleX, headPitch * ((float)Math.PI / 180F));
            }
        } else {
            kopf.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        }
    }
}