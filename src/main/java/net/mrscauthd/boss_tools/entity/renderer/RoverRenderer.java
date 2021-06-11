package net.mrscauthd.boss_tools.entity.renderer;

import org.objectweb.asm.tree.analysis.Frame;

import net.mrscauthd.boss_tools.entity.RoverEntity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RoverRenderer {
	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(RoverEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelrover(), 0f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("boss_tools:textures/rover.png");
					}
				};
			});
		}
	}

	// Made with Blockbench 3.8.2
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelrover extends EntityModel<Entity> {
		private final ModelRenderer Frame;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer Wheel1;
		private final ModelRenderer Wheel2;
		private final ModelRenderer Wheel3;
		private final ModelRenderer Wheel4;
		private final ModelRenderer sat;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		public Modelrover() {
			textureWidth = 128;
			textureHeight = 128;
			Frame = new ModelRenderer(this);
			Frame.setRotationPoint(0.0F, 24.0F, 0.0F);
			Frame.setTextureOffset(0, 0).addBox(-7.0F, -4.0F, -21.0F, 14.0F, 1.0F, 29.0F, 0.0F, false);
			Frame.setTextureOffset(22, 32).addBox(5.0F, -6.0F, -15.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);
			Frame.setTextureOffset(22, 30).addBox(-5.0F, -6.0F, -15.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);
			Frame.setTextureOffset(33, 32).addBox(-7.0F, -3.0F, 5.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(33, 30).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(31, 49).addBox(-5.0F, -5.0F, -22.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(33, 36).addBox(-3.0F, -5.0F, -23.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(0, 5).addBox(-1.0F, -5.0F, -22.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(4, 7).addBox(-4.0F, -6.0F, -21.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(0, 7).addBox(-4.0F, -9.0F, -21.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(0, 51).addBox(3.0F, -20.0F, -22.0F, 1.0F, 17.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(46, 54).addBox(-5.0F, -8.0F, -22.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
			Frame.setTextureOffset(13, 54).addBox(-5.0F, -11.0F, -22.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
			Frame.setTextureOffset(0, 40).addBox(-5.0F, -14.0F, 7.0F, 10.0F, 10.0F, 1.0F, 0.0F, false);
			Frame.setTextureOffset(0, 30).addBox(-6.0F, -5.0F, -5.0F, 12.0F, 1.0F, 9.0F, 0.0F, false);
			Frame.setTextureOffset(0, 20).addBox(-4.0F, -8.0F, -17.0F, 8.0F, 4.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(0, 30).addBox(7.0F, -6.25F, -19.75F, 2.0F, 0.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(0, 0).addBox(-9.0F, -6.25F, -19.75F, 2.0F, 0.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(20, 20).addBox(7.0F, -6.25F, 4.25F, 2.0F, 0.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(16, 20).addBox(-9.0F, -6.25F, 4.25F, 2.0F, 0.0F, 5.0F, 0.0F, false);
			Frame.setTextureOffset(22, 44).addBox(7.0F, -6.25F, -14.75F, 2.0F, 5.0F, 0.0F, 0.0F, false);
			Frame.setTextureOffset(0, 0).addBox(-9.0F, -6.25F, -14.75F, 2.0F, 5.0F, 0.0F, 0.0F, false);
			Frame.setTextureOffset(0, 30).addBox(7.0F, -6.25F, 9.25F, 2.0F, 5.0F, 0.0F, 0.0F, false);
			Frame.setTextureOffset(0, 20).addBox(-9.0F, -6.25F, 9.25F, 2.0F, 5.0F, 0.0F, 0.0F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(0.0F, -4.8695F, 4.9914F);
			Frame.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.1309F, 0.0F, 0.0F);
			cube_r1.setTextureOffset(0, 9).addBox(-6.0F, -9.0F, -1.0F, 12.0F, 10.0F, 1.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(0.0F, -4.0F, -21.0F);
			Frame.addChild(cube_r2);
			setRotationAngle(cube_r2, -0.2182F, 0.0F, 0.0F);
			cube_r2.setTextureOffset(4, 51).addBox(-1.0F, -6.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(0.0F, -4.9992F, -22.9995F);
			Frame.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.5672F, 0.0F, 0.0F);
			cube_r3.setTextureOffset(31, 34).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 0.0F, 2.0F, 0.0F, false);
			Wheel1 = new ModelRenderer(this);
			Wheel1.setRotationPoint(7.0F, 21.5F, -18.5F);
			Wheel1.setTextureOffset(46, 44).addBox(0.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);
			Wheel2 = new ModelRenderer(this);
			Wheel2.setRotationPoint(8.0F, 21.5F, 5.5F);
			Wheel2.setTextureOffset(46, 34).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);
			Wheel3 = new ModelRenderer(this);
			Wheel3.setRotationPoint(-8.0F, 21.5F, -18.5F);
			Wheel3.setTextureOffset(37, 39).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);
			Wheel4 = new ModelRenderer(this);
			Wheel4.setRotationPoint(-8.0F, 21.5F, 5.5F);
			Wheel4.setTextureOffset(22, 44).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);
			sat = new ModelRenderer(this);
			sat.setRotationPoint(3.458F, 4.2756F, -21.5F);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(0.042F, -0.2756F, 0.0F);
			sat.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.2618F, 0.0F, 0.0F);
			cube_r4.setTextureOffset(0, 0).addBox(-4.5F, 0.15F, -4.5F, 9.0F, 0.0F, 9.0F, 0.0F, false);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(-0.042F, -1.7244F, -0.379F);
			sat.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.2618F, 0.0F, 0.0F);
			cube_r5.setTextureOffset(0, 35).addBox(-0.416F, -1.3511F, -0.531F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
						   float alpha) {
			matrixStack.scale(2.0f, 2.0f, 2.0f);
			matrixStack.translate(0.0D, -0.75D, 0.0D);
			Frame.render(matrixStack, buffer, packedLight, packedOverlay);
			Wheel1.render(matrixStack, buffer, packedLight, packedOverlay);
			Wheel2.render(matrixStack, buffer, packedLight, packedOverlay);
			Wheel3.render(matrixStack, buffer, packedLight, packedOverlay);
			Wheel4.render(matrixStack, buffer, packedLight, packedOverlay);
			sat.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			// Animation1
			this.Wheel1.rotateAngleX = f2 / (180F / (float) Math.PI);
			this.Wheel2.rotateAngleX = f2 / (180F / (float) Math.PI);
			this.Wheel3.rotateAngleX = f2 / (180F / (float) Math.PI);
			this.Wheel4.rotateAngleX = f2 / (180F / (float) Math.PI);
			if (e instanceof LivingEntity) {
				if (e.getPersistentData().getDouble("Wheel") == 1) {
					this.Wheel1.rotateAngleX = (float) (f / 4);
					this.Wheel2.rotateAngleX = (float) (f / 4);
					this.Wheel3.rotateAngleX = (float) (f / 4);
					this.Wheel4.rotateAngleX = (float) (f / 4);
				}
				if (e.getPersistentData().getDouble("Wheel") == 0) {
					this.Wheel1.rotateAngleX = (float) (f / 4);
					this.Wheel2.rotateAngleX = (float) (f / 4);
					this.Wheel3.rotateAngleX = (float) (f / 4);
					this.Wheel4.rotateAngleX = (float) (f / 4);
				}
				//System.out.println(f);
			}
			// sat
			this.sat.rotateAngleY = (float) f2 / 20f;
			// this.wellfront1.rotateAngleY = f2 / (180F / (float) Math.PI);
			// this.wellfront2.rotateAngleY = f2 / (180F / (float) Math.PI);
			if (e instanceof LivingEntity) {
				// this.wellfront1.rotateAngleY = (float) 0.3 /*((LivingEntity)
				// e).getPersistentData().getDouble("Wheel")*/;
				// this.wellfront2.rotateAngleY = (float) 0.3 /*((LivingEntity)
				// e).getPersistentData().getDouble("Wheel")*/;
			}
		}
	}
}