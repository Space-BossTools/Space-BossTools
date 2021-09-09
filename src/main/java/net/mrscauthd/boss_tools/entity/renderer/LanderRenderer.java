package net.mrscauthd.boss_tools.entity.renderer;

import net.mrscauthd.boss_tools.entity.LanderEntity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class LanderRenderer {
	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(LanderEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelLander(), 0f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("boss_tools:textures/lander.png");
					}
				};
			});
		}
	}

	// Made with Blockbench 3.8.4
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports
	public static class ModelLander extends EntityModel<Entity> {
		private final ModelRenderer leg1;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer leg2;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r9;
		private final ModelRenderer leg3;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer cube_r12;
		private final ModelRenderer cube_r13;
		private final ModelRenderer cube_r14;
		private final ModelRenderer leg4;
		private final ModelRenderer cube_r15;
		private final ModelRenderer cube_r16;
		private final ModelRenderer cube_r17;
		private final ModelRenderer cube_r18;
		private final ModelRenderer cube_r19;
		private final ModelRenderer bb_main;
		private final ModelRenderer cube_r20;
		private final ModelRenderer cube_r21;
		private final ModelRenderer cube_r22;
		private final ModelRenderer cube_r23;
		private final ModelRenderer cube_r24;
		private final ModelRenderer cube_r25;
		private final ModelRenderer cube_r26;
		private final ModelRenderer cube_r27;
		private final ModelRenderer cube_r28;
		private final ModelRenderer cube_r29;
		private final ModelRenderer cube_r30;
		private final ModelRenderer cube_r31;
		private final ModelRenderer cube_r32;
		private final ModelRenderer cube_r33;
		private final ModelRenderer cube_r34;
		private final ModelRenderer cube_r35;
		private final ModelRenderer cube_r36;
		public ModelLander() {
			textureWidth = 256;
			textureHeight = 256;
			leg1 = new ModelRenderer(this);
			leg1.setRotationPoint(-11.0F, 8.1414F, 10.7143F);
			leg1.setTextureOffset(0, 144).addBox(-10.0F, 14.8586F, 0.2857F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(11.0F, 11.8586F, -10.7143F);
			leg1.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.6981F, -0.7854F, 0.0F);
			cube_r1.setTextureOffset(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(11.0F, 11.8586F, -10.7143F);
			leg1.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.7854F, 0.0F);
			cube_r2.setTextureOffset(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(11.0F, 11.8586F, -10.7143F);
			leg1.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.6109F, -0.7854F, 0.0F);
			cube_r3.setTextureOffset(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
			cube_r3.setTextureOffset(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(11.0F, 11.8586F, -10.7143F);
			leg1.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.3927F, -0.7854F, 0.0F);
			cube_r4.setTextureOffset(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, -0.5F, false);
			cube_r4.setTextureOffset(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
			leg2 = new ModelRenderer(this);
			leg2.setRotationPoint(-10.0F, 8.1414F, -10.2857F);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(10.0F, 11.8586F, 10.2857F);
			leg2.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.6109F, -2.3562F, 0.0F);
			cube_r5.setTextureOffset(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, 0.0F, false);
			cube_r5.setTextureOffset(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(10.0F, 11.8586F, 10.2857F);
			leg2.addChild(cube_r6);
			setRotationAngle(cube_r6, 0.3927F, -2.3562F, 0.0F);
			cube_r6.setTextureOffset(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, -0.5F, false);
			cube_r6.setTextureOffset(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(10.0F, 11.8586F, 10.2857F);
			leg2.addChild(cube_r7);
			setRotationAngle(cube_r7, 0.0F, -1.5708F, 0.0F);
			cube_r7.setTextureOffset(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			cube_r8 = new ModelRenderer(this);
			cube_r8.setRotationPoint(10.0F, 11.8586F, 10.2857F);
			leg2.addChild(cube_r8);
			setRotationAngle(cube_r8, 0.0F, -0.7854F, 0.0F);
			cube_r8.setTextureOffset(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r9 = new ModelRenderer(this);
			cube_r9.setRotationPoint(10.0F, 11.8586F, 10.2857F);
			leg2.addChild(cube_r9);
			setRotationAngle(cube_r9, -0.6981F, -2.3562F, 0.0F);
			cube_r9.setTextureOffset(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			leg3 = new ModelRenderer(this);
			leg3.setRotationPoint(10.0F, 8.1414F, -10.2857F);
			cube_r10 = new ModelRenderer(this);
			cube_r10.setRotationPoint(-10.0F, 11.8586F, 10.2857F);
			leg3.addChild(cube_r10);
			setRotationAngle(cube_r10, 0.6109F, 2.3562F, 0.0F);
			cube_r10.setTextureOffset(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
			cube_r10.setTextureOffset(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, 0.0F, false);
			cube_r11 = new ModelRenderer(this);
			cube_r11.setRotationPoint(-10.0F, 11.8586F, 10.2857F);
			leg3.addChild(cube_r11);
			setRotationAngle(cube_r11, 0.3927F, 2.3562F, 0.0F);
			cube_r11.setTextureOffset(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, -0.5F, false);
			cube_r11.setTextureOffset(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
			cube_r12 = new ModelRenderer(this);
			cube_r12.setRotationPoint(-10.0F, 11.8586F, 10.2857F);
			leg3.addChild(cube_r12);
			setRotationAngle(cube_r12, 0.0F, 3.1416F, 0.0F);
			cube_r12.setTextureOffset(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			cube_r13 = new ModelRenderer(this);
			cube_r13.setRotationPoint(-10.0F, 11.8586F, 10.2857F);
			leg3.addChild(cube_r13);
			setRotationAngle(cube_r13, 0.0F, -2.3562F, 0.0F);
			cube_r13.setTextureOffset(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r14 = new ModelRenderer(this);
			cube_r14.setRotationPoint(-10.0F, 11.8586F, 10.2857F);
			leg3.addChild(cube_r14);
			setRotationAngle(cube_r14, -0.6981F, 2.3562F, 0.0F);
			cube_r14.setTextureOffset(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			leg4 = new ModelRenderer(this);
			leg4.setRotationPoint(10.0F, 8.1414F, 10.7143F);
			cube_r15 = new ModelRenderer(this);
			cube_r15.setRotationPoint(-10.0F, 11.8586F, -10.7143F);
			leg4.addChild(cube_r15);
			setRotationAngle(cube_r15, 0.6109F, 0.7854F, 0.0F);
			cube_r15.setTextureOffset(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
			cube_r15.setTextureOffset(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, 0.0F, false);
			cube_r16 = new ModelRenderer(this);
			cube_r16.setRotationPoint(-10.0F, 11.8586F, -10.7143F);
			leg4.addChild(cube_r16);
			setRotationAngle(cube_r16, 0.3927F, 0.7854F, 0.0F);
			cube_r16.setTextureOffset(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, -0.5F, false);
			cube_r16.setTextureOffset(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
			cube_r17 = new ModelRenderer(this);
			cube_r17.setRotationPoint(-10.0F, 11.8586F, -10.7143F);
			leg4.addChild(cube_r17);
			setRotationAngle(cube_r17, 0.0F, 1.5708F, 0.0F);
			cube_r17.setTextureOffset(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			cube_r18 = new ModelRenderer(this);
			cube_r18.setRotationPoint(-10.0F, 11.8586F, -10.7143F);
			leg4.addChild(cube_r18);
			setRotationAngle(cube_r18, 0.0F, 2.3562F, 0.0F);
			cube_r18.setTextureOffset(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			cube_r19 = new ModelRenderer(this);
			cube_r19.setRotationPoint(-10.0F, 11.8586F, -10.7143F);
			leg4.addChild(cube_r19);
			setRotationAngle(cube_r19, -0.6981F, 0.7854F, 0.0F);
			cube_r19.setTextureOffset(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(155, 59).addBox(-6.0F, -11.9F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-12.0F, -16.0F, -12.0F, 24.0F, 2.0F, 24.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 111).addBox(-5.0F, -19.0F, -8.0F, 10.0F, 3.0F, 10.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 46).addBox(-8.0F, -35.0F, -13.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 124).addBox(8.0F, -30.0F, -8.0F, 2.0F, 10.0F, 10.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 12).addBox(-8.995F, -36.0F, -12.0F, 18.0F, 18.0F, 2.0F, 0.0F, false);
			bb_main.setTextureOffset(155, 24).addBox(3.5F, -46.0F, -15.5F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(155, 24).addBox(-4.5F, -46.0F, -15.5F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(159, 24).addBox(1.5F, -41.0F, -15.5F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(163, 24).addBox(-2.5F, -43.0F, -15.5F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(167, 24).addBox(-0.5F, -44.0F, -15.5F, 1.0F, 13.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 0).addBox(1.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 0).addBox(-4.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(155, 39).addBox(-4.0F, -13.9F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
			bb_main.setTextureOffset(155, 48).addBox(-5.0F, -12.9F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 51).addBox(-6.0F, -25.0F, -14.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 51).addBox(-6.0F, -33.0F, -16.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(24, 134).addBox(10.0F, -28.0F, -5.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);
			bb_main.setTextureOffset(34, 134).addBox(10.0F, -27.0F, -6.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			cube_r20 = new ModelRenderer(this);
			cube_r20.setRotationPoint(-0.5F, -17.5F, 0.5F);
			bb_main.addChild(cube_r20);
			setRotationAngle(cube_r20, 0.0F, 3.1416F, 0.0F);
			cube_r20.setTextureOffset(24, 134).addBox(9.5F, -10.5F, 1.5F, 1.0F, 6.0F, 4.0F, 0.0F, false);
			cube_r20.setTextureOffset(34, 134).addBox(9.5F, -9.5F, 0.5F, 1.0F, 4.0F, 6.0F, 0.0F, false);
			cube_r21 = new ModelRenderer(this);
			cube_r21.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r21);
			setRotationAngle(cube_r21, 0.0F, 3.1416F, 0.0F);
			cube_r21.setTextureOffset(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r22 = new ModelRenderer(this);
			cube_r22.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r22);
			setRotationAngle(cube_r22, 0.0F, 1.5708F, 0.0F);
			cube_r22.setTextureOffset(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r23 = new ModelRenderer(this);
			cube_r23.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r23);
			setRotationAngle(cube_r23, 0.0F, -1.5708F, 0.0F);
			cube_r23.setTextureOffset(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r24 = new ModelRenderer(this);
			cube_r24.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r24);
			setRotationAngle(cube_r24, 0.2182F, 0.0F, 0.0F);
			cube_r24.setTextureOffset(104, 51).addBox(-6.0F, -38.0F, 5.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r24.setTextureOffset(104, 51).addBox(-6.0F, -38.0F, 0.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r25 = new ModelRenderer(this);
			cube_r25.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r25);
			setRotationAngle(cube_r25, 0.7854F, 0.0F, 0.0F);
			cube_r25.setTextureOffset(104, 51).addBox(-6.0F, -8.0F, 15.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r25.setTextureOffset(104, 51).addBox(-6.0F, -11.0F, 12.0F, 12.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r26 = new ModelRenderer(this);
			cube_r26.setRotationPoint(-0.5F, -15.5F, 19.5F);
			bb_main.addChild(cube_r26);
			setRotationAngle(cube_r26, 0.2182F, 0.0F, 0.0F);
			cube_r26.setTextureOffset(14, 126).addBox(-12.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			cube_r26.setTextureOffset(14, 126).addBox(-12.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			cube_r26.setTextureOffset(14, 126).addBox(9.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			cube_r26.setTextureOffset(14, 126).addBox(9.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			cube_r26.setTextureOffset(0, 92).addBox(-11.5F, -24.5F, -6.5F, 24.0F, 17.0F, 2.0F, 0.0F, false);
			cube_r27 = new ModelRenderer(this);
			cube_r27.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r27);
			setRotationAngle(cube_r27, 0.0F, 2.3562F, 0.0F);
			cube_r27.setTextureOffset(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r28 = new ModelRenderer(this);
			cube_r28.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r28);
			setRotationAngle(cube_r28, 0.0F, -2.3562F, 0.0F);
			cube_r28.setTextureOffset(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r29 = new ModelRenderer(this);
			cube_r29.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r29);
			setRotationAngle(cube_r29, 0.0F, -0.7854F, 0.0F);
			cube_r29.setTextureOffset(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r30 = new ModelRenderer(this);
			cube_r30.setRotationPoint(0.0F, 0.0F, 0.0F);
			bb_main.addChild(cube_r30);
			setRotationAngle(cube_r30, 0.0F, 0.7854F, 0.0F);
			cube_r30.setTextureOffset(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r31 = new ModelRenderer(this);
			cube_r31.setRotationPoint(-0.5F, -17.5F, 15.5F);
			bb_main.addChild(cube_r31);
			setRotationAngle(cube_r31, 0.2182F, 0.0F, 0.0F);
			cube_r31.setTextureOffset(155, 0).addBox(-4.5F, -15.5F, -12.5F, 10.0F, 10.0F, 14.0F, 0.0F, false);
			cube_r32 = new ModelRenderer(this);
			cube_r32.setRotationPoint(-0.5F, -15.5F, 23.5F);
			bb_main.addChild(cube_r32);
			setRotationAngle(cube_r32, 0.2182F, 0.0F, 0.0F);
			cube_r32.setTextureOffset(0, 78).addBox(-9.5F, -21.5F, -6.5F, 20.0F, 13.0F, 1.0F, 0.0F, false);
			cube_r33 = new ModelRenderer(this);
			cube_r33.setRotationPoint(-0.5F, -17.5F, -3.5F);
			bb_main.addChild(cube_r33);
			setRotationAngle(cube_r33, 0.3927F, 0.0F, 0.0F);
			cube_r33.setTextureOffset(116, 0).addBox(-5.0F, -16.5F, -6.5F, 11.0F, 9.0F, 2.0F, 0.0F, false);
			cube_r34 = new ModelRenderer(this);
			cube_r34.setRotationPoint(-0.5F, -17.5F, 1.5F);
			bb_main.addChild(cube_r34);
			setRotationAngle(cube_r34, 1.5708F, 0.0F, 0.0F);
			cube_r34.setTextureOffset(104, 12).addBox(-8.5F, -15.5F, 14.5F, 18.0F, 18.0F, 2.0F, 0.0F, false);
			cube_r35 = new ModelRenderer(this);
			cube_r35.setRotationPoint(-0.5F, -17.5F, 0.5F);
			bb_main.addChild(cube_r35);
			setRotationAngle(cube_r35, 0.2182F, 0.0F, 0.0F);
			cube_r35.setTextureOffset(0, 26).addBox(-6.5F, -19.5F, -8.5F, 14.0F, 6.0F, 14.0F, 0.0F, false);
			cube_r36 = new ModelRenderer(this);
			cube_r36.setRotationPoint(-9.5F, -21.5F, -3.5F);
			bb_main.addChild(cube_r36);
			setRotationAngle(cube_r36, 0.0F, 3.1416F, 0.0F);
			cube_r36.setTextureOffset(0, 124).addBox(-1.5F, -8.5F, -5.5F, 2.0F, 10.0F, 10.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			leg1.render(matrixStack, buffer, packedLight, packedOverlay);
			leg2.render(matrixStack, buffer, packedLight, packedOverlay);
			leg3.render(matrixStack, buffer, packedLight, packedOverlay);
			leg4.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
		}
	}
}
