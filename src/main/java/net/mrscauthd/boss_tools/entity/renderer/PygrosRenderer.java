package net.mrscauthd.boss_tools.entity.renderer;

import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.util.math.MathHelper;
import net.mrscauthd.boss_tools.entity.PygrosEntity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.IRenderTypeBuffer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class PygrosRenderer {
    public static class ModelRegisterHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(PygrosEntity.entity, renderManager -> {
                return new MobRenderer(renderManager, new Modelpygro(0), 0.5f) {
                    {
                        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.02F)));
                    }
                    @Override
                    public ResourceLocation getEntityTexture(Entity entity) {
                       // return new ResourceLocation("boss_tools:textures/pygro.png");
                        return new ResourceLocation("textures/entity/piglin/piglin.png");
                    }
                };
            });
        }
    }

    public static class Modelpygro<T extends MobEntity> extends PlayerModel<T> {
        public final ModelRenderer field_239115_a_;
        public final ModelRenderer field_239116_b_;
        private final ModelRenderer field_241660_y_;
        private final ModelRenderer field_241661_z_;
        private final ModelRenderer field_241658_A_;
        private final ModelRenderer field_241659_B_;

        public Modelpygro(float p_i232336_1_) {
            super(p_i232336_1_, false);
            textureWidth = 64;
            textureHeight = 64;
            this.bipedBody = new ModelRenderer(this, 16, 16);
            this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i232336_1_);
            this.bipedHead = new ModelRenderer(this);
            this.bipedHead.setTextureOffset(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, p_i232336_1_);
            this.bipedHead.setTextureOffset(31, 1).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, p_i232336_1_);
            this.bipedHead.setTextureOffset(2, 4).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_i232336_1_);
            this.bipedHead.setTextureOffset(2, 0).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_i232336_1_);
            this.field_239115_a_ = new ModelRenderer(this);
            this.field_239115_a_.setRotationPoint(4.5F, -6.0F, 0.0F);
            this.field_239115_a_.setTextureOffset(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_i232336_1_);
            this.bipedHead.addChild(this.field_239115_a_);
            this.field_239116_b_ = new ModelRenderer(this);
            this.field_239116_b_.setRotationPoint(-4.5F, -6.0F, 0.0F);
            this.field_239116_b_.setTextureOffset(39, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_i232336_1_);
            this.bipedHead.addChild(this.field_239116_b_);
            this.bipedHeadwear = new ModelRenderer(this);
            this.field_241660_y_ = this.bipedBody.getModelAngleCopy();
            this.field_241661_z_ = this.bipedHead.getModelAngleCopy();
            this.field_241658_A_ = this.bipedLeftArm.getModelAngleCopy();
            this.field_241659_B_ = this.bipedLeftArm.getModelAngleCopy();
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                           float alpha) {
         //   field_239115_a_.render(matrixStack, buffer, packedLight, packedOverlay);
         //   field_239116_b_.render(matrixStack, buffer, packedLight, packedOverlay);
            field_241660_y_.render(matrixStack, buffer, packedLight, packedOverlay);
            field_241661_z_.render(matrixStack, buffer, packedLight, packedOverlay);
            field_241658_A_.render(matrixStack, buffer, packedLight, packedOverlay);
            field_241659_B_.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedHead.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedBody.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedHeadwear.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedRightArmwear.render(matrixStack, buffer, packedLight, packedOverlay);
            bipedLeftArmwear.render(matrixStack, buffer, packedLight, packedOverlay);
            leftArmPose.func_241657_a_();
            rightArmPose.func_241657_a_();
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
        public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.bipedBody.copyModelAngles(this.field_241660_y_);
            this.bipedHead.copyModelAngles(this.field_241661_z_);
            this.bipedLeftArm.copyModelAngles(this.field_241658_A_);
            this.bipedRightArm.copyModelAngles(this.field_241659_B_);
            super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            float f = ((float)Math.PI / 6F);
            float f1 = ageInTicks * 0.1F + limbSwing * 0.5F;
            float f2 = 0.08F + limbSwingAmount * 0.4F;
            this.field_239115_a_.rotateAngleZ = (-(float)Math.PI / 6F) - MathHelper.cos(f1 * 1.2F) * f2;
            this.field_239116_b_.rotateAngleZ = ((float)Math.PI / 6F) + MathHelper.cos(f1) * f2;
            if (entityIn instanceof PygrosEntity.CustomEntity) {
                PygrosEntity.CustomEntity abstractpiglinentity = (PygrosEntity.CustomEntity)entityIn;
                PiglinAction piglinaction = abstractpiglinentity.func_234424_eM_();
                if (piglinaction == PiglinAction.DANCING) {
                    float f3 = ageInTicks / 60.0F;
                    this.field_239116_b_.rotateAngleZ = ((float)Math.PI / 6F) + ((float)Math.PI / 180F) * MathHelper.sin(f3 * 30.0F) * 10.0F;
                    this.field_239115_a_.rotateAngleZ = (-(float)Math.PI / 6F) - ((float)Math.PI / 180F) * MathHelper.cos(f3 * 30.0F) * 10.0F;
                    this.bipedHead.rotationPointX = MathHelper.sin(f3 * 10.0F);
                    this.bipedHead.rotationPointY = MathHelper.sin(f3 * 40.0F) + 0.4F;
                    this.bipedRightArm.rotateAngleZ = ((float)Math.PI / 180F) * (70.0F + MathHelper.cos(f3 * 40.0F) * 10.0F);
                    this.bipedLeftArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ * -1.0F;
                    this.bipedRightArm.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.5F + 1.5F;
                    this.bipedLeftArm.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.5F + 1.5F;
                    this.bipedBody.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.35F;
                } else if (piglinaction == PiglinAction.ATTACKING_WITH_MELEE_WEAPON && this.swingProgress == 0.0F) {
                    this.func_239117_a_(entityIn);
                } else if (piglinaction == PiglinAction.CROSSBOW_HOLD) {
                    ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, !entityIn.isLeftHanded());
                } else if (piglinaction == PiglinAction.CROSSBOW_CHARGE) {
                    ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, entityIn, !entityIn.isLeftHanded());
                } else if (piglinaction == PiglinAction.ADMIRING_ITEM) {
                    this.bipedHead.rotateAngleX = 0.5F;
                    this.bipedHead.rotateAngleY = 0.0F;
                    if (entityIn.isLeftHanded()) {
                        this.bipedRightArm.rotateAngleY = -0.5F;
                        this.bipedRightArm.rotateAngleX = -0.9F;
                    } else {
                        this.bipedLeftArm.rotateAngleY = 0.5F;
                        this.bipedLeftArm.rotateAngleX = -0.9F;
                    }
                }
            } else if (entityIn.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                ModelHelper.func_239105_a_(this.bipedLeftArm, this.bipedRightArm, entityIn.isAggressive(), this.swingProgress, ageInTicks);
            }

            this.bipedLeftLegwear.copyModelAngles(this.bipedLeftLeg);
            this.bipedRightLegwear.copyModelAngles(this.bipedRightLeg);
            this.bipedLeftArmwear.copyModelAngles(this.bipedLeftArm);
            this.bipedRightArmwear.copyModelAngles(this.bipedRightArm);
            this.bipedBodyWear.copyModelAngles(this.bipedBody);
            this.bipedHeadwear.copyModelAngles(this.bipedHead);
        }

        protected void func_230486_a_(T p_230486_1_, float p_230486_2_) {
            if (this.swingProgress > 0.0F && p_230486_1_ instanceof PygrosEntity.CustomEntity && ((PygrosEntity.CustomEntity)p_230486_1_).func_234424_eM_() == PiglinAction.ATTACKING_WITH_MELEE_WEAPON) {
                ModelHelper.func_239103_a_(this.bipedRightArm, this.bipedLeftArm, p_230486_1_, this.swingProgress, p_230486_2_);
            } else {
                super.func_230486_a_(p_230486_1_, p_230486_2_);
            }
        }

        private void func_239117_a_(T p_239117_1_) {
            if (p_239117_1_.isLeftHanded()) {
                this.bipedLeftArm.rotateAngleX = -1.8F;
            } else {
                this.bipedRightArm.rotateAngleX = -1.8F;
            }

        }
    }
}
