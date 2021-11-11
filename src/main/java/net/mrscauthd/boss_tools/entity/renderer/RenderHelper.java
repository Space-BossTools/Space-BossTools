package net.mrscauthd.boss_tools.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class RenderHelper {

	public static final ResourceLocation TILE_SURFACE = new ResourceLocation("boss_tools", "entities/tile_surface");
	private static TextureAtlasSprite atlass = null;

	public static void renderBox(MatrixStack matrix, IRenderTypeBuffer bufferIn, IVertexConsumer builder, AxisAlignedBB bounds, int lineR, int lineG, int lineB, int surfaceR, int surfaceG, int surfaceB) {
		Matrix3f normal = matrix.getLast().getNormal();
		Matrix4f matrix4f = matrix.getLast().getMatrix();

		float startX = (float) bounds.minX + 0.005F;
		float topY = (float) bounds.minY + 0.005F;
		float startZ = (float) bounds.minZ + 0.005F;

		float endX = (float) bounds.maxX - 0.005F;
		float botY = (float) bounds.maxY - 0.005F;
		float endZ = (float) bounds.maxZ - 0.005F;

		// Bottom frame
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, endX, botY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, endX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, botY, endZ, lineR, lineG, lineB);

		// Top frame
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, endZ, endX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, topY, startZ, endX, topY, endZ, lineR, lineG, lineB);

		// Vertical lines
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, endZ, endX, topY, endZ, lineR, lineG, lineB);

		drawSurfaces(bufferIn, matrix4f, normal, startX, startZ, endX, endZ, botY, topY, surfaceR, surfaceG, surfaceB);
	}

	public static void drawShapeOutline(IVertexConsumer builder, Matrix4f matrix, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b) {
		float nX = (float) (x2 - x1);
		float nY = (float) (y2 - y1);
		float nZ = (float) (z2 - z1);
		float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
		nX = nX / sqrt;
		nY = nY / sqrt;
		nZ = nZ / sqrt;

		int alpha = 0xFF;
		builder.pos(matrix, x1, y1, z1).color(r, g, b, alpha).normal(normal, nX, nY, nZ).endVertex();
		builder.pos(matrix, x2, y2, z2).color(r, g, b, alpha).normal(normal, nX, nY, nZ).endVertex();
	}

	public static void drawSurfaces(IRenderTypeBuffer buffer, Matrix4f matrix, Matrix3f normal, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b) {
		IVertexConsumer builder;
		Minecraft minecraft = Minecraft.getInstance();
		GraphicsFanciness graphicsFanciness = minecraft.gameSettings.graphicFanciness;

		if (graphicsFanciness == GraphicsFanciness.FABULOUS) {
			builder = (IVertexConsumer) buffer.getBuffer(RenderType.getTranslucentMovingBlock());
		} else {
			builder = (IVertexConsumer) buffer.getBuffer(RenderType.getTranslucentNoCrumbling());
		}

		if (atlass == null) {
			atlass = minecraft.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TILE_SURFACE);
		}

		float maxU = atlass.getMaxU();
		float minU = atlass.getMinU();
		float maxV = atlass.getMaxV();
		float minV = atlass.getMinV();
		int alpha = 0xAA;
		int light = 240;

		// Down
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();

		// Inside

		// Down
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.pos(matrix, endX, botY, endZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, endX, topY, endZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, endX, topY, startZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();
		builder.pos(matrix, endX, botY, startZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.pos(matrix, startX, botY, startZ).color(r, g, b, alpha).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, startX, topY, startZ).color(r, g, b, alpha).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, startX, topY, endZ).color(r, g, b, alpha).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
		builder.pos(matrix, startX, botY, endZ).color(r, g, b, alpha).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal, 1, 0, 0).endVertex();
	}

	private RenderHelper() {

	}
}
