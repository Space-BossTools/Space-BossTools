package net.mrscauthd.boss_tools.skyrenderer;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.world.DimensionRenderInfo.FogType;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "boss_tools", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusOverworldOrbit {
	//star
	@Nullable
	public static VertexBuffer starVBO;
	public static final VertexFormat skyVertexFormat = DefaultVertexFormats.POSITION;
	private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation("boss_tools", "overworld_orbit");
	private static final ResourceLocation EARTH_TEXTURE = new ResourceLocation("boss_tools", "textures/sky/earth.png");
	private static final ResourceLocation SUN_TEXTURE = new ResourceLocation("boss_tools", "textures/sky/no_a_sun.png");
	private static final ResourceLocation MOON_TEXTURE = new ResourceLocation("boss_tools", "textures/sky/moon_phases.png");
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void clientSetup(FMLClientSetupEvent event) {
		DimensionRenderInfo.field_239208_a_.put(DIM_RENDER_INFO, new DimensionRenderInfo(999999999, false, FogType.NORMAL, false, false) {
					@Override
					public Vector3d func_230494_a_(Vector3d fogColor, float partialTicks) {
						return fogColor;
					}

					@Override
					public boolean func_230493_a_(int posX, int posY) {
						return false;
					}

					@Override
					public ICloudRenderHandler getCloudRenderHandler() {
						return new ICloudRenderHandler() {
							@Override
							public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {

							}
						};
					}

					@Override
					public ISkyRenderHandler getSkyRenderHandler() {
						return new ISkyRenderHandler() {
							@SuppressWarnings({"deprecation"})
							@Override
							public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
								RenderSystem.disableTexture();
								Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
								float f = (float) vector3d.x;
								float f1 = (float) vector3d.y;
								float f2 = (float) vector3d.z;
								FogRenderer.applyFog();
								BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
								RenderSystem.disableDepthTest();
								RenderSystem.depthMask(false);
								RenderSystem.enableFog();
								RenderSystem.color3f(f, f1, f2);
								mc.worldRenderer.skyVBO.bindBuffer();
								mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
								mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
								VertexBuffer.unbindBuffer();
								mc.worldRenderer.skyVertexFormat.clearBufferState();
								Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
								RenderSystem.enableAlphaTest();
								RenderSystem.enableTexture();
								RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
								RenderSystem.color4f(1f, 1f, 1f, 1f);
								//star gen
								generateStars();
								RenderSystem.disableTexture();
								RenderSystem.disableFog();
								RenderSystem.disableAlphaTest();
								RenderSystem.enableBlend();
								RenderSystem.defaultBlendFunc();
								float[] afloat = world.func_239132_a_().func_230492_a_(world.func_242415_f(partialTicks), partialTicks);
								if (afloat != null) {
									RenderSystem.disableTexture();
									RenderSystem.shadeModel(7425);// 7425
									matrixStack.push();
									matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
									float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F;
									matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));
									matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
									float f4 = afloat[0];
									float f5 = afloat[1];
									float f6 = afloat[2];
									Matrix4f matrix4f = matrixStack.getLast().getMatrix();
									bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
									bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
									for (int j = 0; j <= 16; ++j) {
										float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
										float f8 = MathHelper.sin(f7);
										float f9 = MathHelper.cos(f7);
										bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
									}
									bufferbuilder.finishDrawing();
									WorldVertexBufferUploader.draw(bufferbuilder);
									matrixStack.pop();
									RenderSystem.shadeModel(7424);// 7424
								}
								RenderSystem.enableTexture();
								RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
								matrixStack.push();

								RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
								matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
								matrixStack.rotate(Vector3f.XP.rotationDegrees(0.0F /* world.func_242415_f(partialTicks) * 360.0F */));

								//EARTH
								RenderSystem.enableDepthTest();
								RenderSystem.depthMask(true);

								float var20 = 40.0F + (float) Minecraft.getInstance().player.getPosY() / 2F;

								float scale = 100 * (0.3F - 0.25f - var20 / 10000.0F);
								scale = Math.max(scale, 0.2F);

								mc.getTextureManager().bindTexture(EARTH_TEXTURE);
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -scale, -var20 / 10, scale).tex(0.0F, 0.0F).endVertex(); // 350
								bufferbuilder.pos(matrix4f1, scale, -var20 / 10, scale).tex(1.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, scale, -var20 / 10, -scale).tex(1.0F, 1.0F).endVertex();
								bufferbuilder.pos(matrix4f1, -scale, -var20 / 10, -scale).tex(0.0F, 1.0F).endVertex();
								bufferbuilder.finishDrawing();
								WorldVertexBufferUploader.draw(bufferbuilder);

								RenderSystem.depthMask(false);

								matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));//Normal Day Circle

								float f12 = 20.0F;

								//MOON
								mc.getTextureManager().bindTexture(MOON_TEXTURE);
								int k = world.getMoonPhase();
								int l = k % 4;
								int i1 = k / 4 % 2;
								float f13 = (float) (l + 0) / 4.0F;
								float f14 = (float) (i1 + 0) / 2.0F;
								float f15 = (float) (l + 1) / 4.0F;
								float f16 = (float) (i1 + 1) / 2.0F;
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -f12, -100.0F,f12).tex(f15, f16).endVertex();
								bufferbuilder.pos(matrix4f1, f12, -100.0F, f12).tex(f13, f16).endVertex();
								bufferbuilder.pos(matrix4f1, f12, -100.0F, -f12).tex(f13, f14).endVertex();
								bufferbuilder.pos(matrix4f1, -f12,  -100.0F, -f12).tex(f15, f14).endVertex();
								bufferbuilder.finishDrawing();
								WorldVertexBufferUploader.draw(bufferbuilder);

								//SUN
								mc.getTextureManager().bindTexture(SUN_TEXTURE);
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -35, 100.0F, -35).tex(0.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, 35, 100.0F, -35).tex(1.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, 35, 100.0F, 35).tex(1.0F, 1.0F).endVertex();
								bufferbuilder.pos(matrix4f1, -35, 100.0F, 35).tex(0.0F, 1.0F).endVertex();
								bufferbuilder.finishDrawing();
								WorldVertexBufferUploader.draw(bufferbuilder);

								RenderSystem.disableTexture();

								float f10 = 1.0F;
								RenderSystem.color4f(f10, f10, f10, f10);
								starVBO.bindBuffer();
								mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
								starVBO.draw(matrixStack.getLast().getMatrix(), 7);
								VertexBuffer.unbindBuffer();
								mc.worldRenderer.skyVertexFormat.clearBufferState();

								RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
								RenderSystem.disableBlend();
								RenderSystem.enableAlphaTest();
								RenderSystem.enableFog();
								matrixStack.pop();
								RenderSystem.disableTexture();
								RenderSystem.color3f(0.0F, 0.0F, 0.0F);
								if (world.func_239132_a_().func_239216_b_()) {
									RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
								} else {
									RenderSystem.color3f(f, f1, f2);
								}
								RenderSystem.enableTexture();
								RenderSystem.depthMask(true);
								RenderSystem.disableFog();
							}
						};
					}

					//Star renderer
					private void generateStars() {
						Tessellator tessellator = Tessellator.getInstance();
						BufferBuilder bufferbuilder = tessellator.getBuffer();
						if (starVBO != null) {
							starVBO.close();
						}

						starVBO = new VertexBuffer(skyVertexFormat);
						this.renderStars(bufferbuilder);
						bufferbuilder.finishDrawing();
						starVBO.upload(bufferbuilder);
					}

					//star render start
					private void renderStars(BufferBuilder bufferBuilderIn) {
						Random random = new Random(10842L);
						bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);
						int stars = 0;
						if (Minecraft.getInstance().gameSettings.graphicFanciness == GraphicsFanciness.FANCY || Minecraft.getInstance().gameSettings.graphicFanciness == GraphicsFanciness.FABULOUS) {
							stars = 13000; //for Very good pcs 20000
						} else {
							stars = 6000;
						}

						for(int i = 0; i < stars; ++i) { //defoult star amount 1500
							double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
							double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
							double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
							double d3 = (double)(0.15F + random.nextFloat() * 0.1F);
							double d4 = d0 * d0 + d1 * d1 + d2 * d2;
							if (d4 < 1.0D && d4 > 0.01D) {
								d4 = 1.0D / Math.sqrt(d4);
								d0 = d0 * d4;
								d1 = d1 * d4;
								d2 = d2 * d4;
								double d5 = d0 * 150.0D; //default Minecraft 100
								double d6 = d1 * 150.0D;
								double d7 = d2 * 150.0D;
								double d8 = Math.atan2(d0, d2);
								double d9 = Math.sin(d8);
								double d10 = Math.cos(d8);
								double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
								double d12 = Math.sin(d11);
								double d13 = Math.cos(d11);
								double d14 = random.nextDouble() * Math.PI * 2.0D;
								double d15 = Math.sin(d14);
								double d16 = Math.cos(d14);

								for(int j = 0; j < 4; ++j) {
									double d17 = 0.0D;
									double d18 = (double)((j & 2) - 1) * d3;
									double d19 = (double)((j + 1 & 2) - 1) * d3;
									double d20 = 0.0D;
									double d21 = d18 * d16 - d19 * d15;
									double d22 = d19 * d16 + d18 * d15;
									double d23 = d21 * d12 + 0.0D * d13;
									double d24 = 0.0D * d12 - d21 * d13;
									double d25 = d24 * d9 - d22 * d10;
									double d26 = d22 * d9 + d24 * d10;
									bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
								}
							}
						}
					}

				});
	}
}
