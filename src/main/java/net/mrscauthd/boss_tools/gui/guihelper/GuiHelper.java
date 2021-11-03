package net.mrscauthd.boss_tools.gui.guihelper;

import net.minecraft.client.renderer.WorldVertexBufferUploader;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class GuiHelper {

	public static final ResourceLocation FIRE_PATH = new ResourceLocation("boss_tools:textures/fire_on.png");
	public static final int FIRE_WIDTH = 14;
	public static final int FIRE_HEIGHT = 14;
	public static final ResourceLocation ARROW_PATH = new ResourceLocation("boss_tools:textures/animated_arrow_full.png");
	public static final int ARROW_WIDTH = 24;
	public static final int ARROW_HEIGHT = 17;
	public static final ResourceLocation OXYGEN_CONTENT_PATH = new ResourceLocation("boss_tools:textures/oxygen.png");
	public static final ResourceLocation OXYGEN_TANK_PATH = new ResourceLocation("boss_tools:textures/fluid_tank_fore.png");
	public static final int OXYGEN_TANK_WIDTH = 14;
	public static final int OXYGEN_TANK_HEIGHT = 48;
	public static final ResourceLocation ENERGY_PATH = new ResourceLocation("boss_tools:textures/energy_full.png");
	public static final int ENERGY_WIDTH = 24;
	public static final int ENERGY_HEIGHT = 48;
	public static final int FUEL_WIDTH = 48;
	public static final int FUEL_HEIGHT = 48;
	public static final ResourceLocation FLUID_TANK_PATH = new ResourceLocation("boss_tools:textures/fluid_tank_fore.png");
	public static final int FLUID_TANK_WIDTH = 14;
	public static final int FLUID_TANK_HEIGHT = 48;

	public static void drawArrow(MatrixStack matrixStack, int left, int top, double ratio) {
		GuiHelper.drawHorizontal(matrixStack, left, top, ARROW_WIDTH, ARROW_HEIGHT, ARROW_PATH, ratio);
	}

	public static Rectangle2d getArrowBounds(int left, int top) {
		return new Rectangle2d(left, top, ARROW_WIDTH, ARROW_HEIGHT);
	}

	public static void drawFire(MatrixStack matrixStack, int left, int top, double ratio) {
		drawVertical(matrixStack, left, top, FIRE_WIDTH, FIRE_HEIGHT, FIRE_PATH, ratio);
	}

	public static Rectangle2d getFireBounds(int left, int top) {
		return new Rectangle2d(left, top, FIRE_WIDTH, FIRE_HEIGHT);
	}

	public static void drawOxygenTank(MatrixStack matrixStack, int left, int top, IOxygenStorage oxygenStorage) {
		drawOxygenTank(matrixStack, left, top, oxygenStorage.getOxygenStoredRatio());
	}

	public static void drawOxygenTank(MatrixStack matrixStack, int left, int top, double ratio) {
		int maxHeight = FLUID_TANK_HEIGHT;
		int scaledHeight = (int) Math.ceil(maxHeight * ratio);
		int offset = maxHeight - scaledHeight;
		Minecraft.getInstance().getTextureManager().bindTexture(OXYGEN_CONTENT_PATH);
		drawTiledSprite(matrixStack, left, top + offset, OXYGEN_TANK_WIDTH, scaledHeight, 16, 16, 0.0F, 1.0F, 0.0F, 1.0F);
		drawFluidTankOverlay(matrixStack, left, top);
	}

	public static Rectangle2d getOxygenTankBounds(int left, int top) {
		return new Rectangle2d(left, top, OXYGEN_TANK_WIDTH, OXYGEN_TANK_HEIGHT);
	}

	public static void drawEnergy(MatrixStack matrixStack, int left, int top, IEnergyStorage energyStorage) {
		drawEnergy(matrixStack, left, top, (double) energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored());
	}

	public static void drawEnergy(MatrixStack matrixStack, int left, int top, double ratio) {
		drawVertical(matrixStack, left, top, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_PATH, ratio);
	}

	public static void drawFuel(MatrixStack matrixStack, int left, int top, double ratio) {
		ResourceLocation full = new ResourceLocation("boss_tools:textures/rocket_fuel_bar_full.png");
		drawVertical(matrixStack, left, top, FUEL_WIDTH, FUEL_HEIGHT, full, ratio);
	}

	public static Rectangle2d getEnergyBounds(int left, int top) {
		return new Rectangle2d(left, top, ENERGY_WIDTH, ENERGY_HEIGHT);
	}

	public static void drawFluidTank(MatrixStack matrixStack, int left, int top, IFluidTank tank) {
		drawFluidTank(matrixStack, left, top, tank.getFluid(), tank.getCapacity());
	}

	public static void drawFluidTank(MatrixStack matrixStack, int left, int top, FluidStack stack, int capacity) {
		if (stack != null && !stack.isEmpty() && capacity > 0) {
			int maxHeight = FLUID_TANK_HEIGHT;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
			int offset = maxHeight - scaledHeight;
			drawFluid(matrixStack, left, top + offset, FLUID_TANK_WIDTH, scaledHeight, stack);
		}

		drawFluidTankOverlay(matrixStack, left, top);
	}

	public static void drawFluidTankOverlay(MatrixStack matrixStack, int left, int top) {
		drawVertical(matrixStack, left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_PATH, 1.0D);
	}

	public static void drawFluid(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null) {
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);
		FluidAttributes attributes = fluid.getAttributes();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		setGLColorFromInt(attributes.getColor(stack));
		drawTiledSprite(matrixStack, left, top, width, height, fluidStillSprite, 16, 16);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
	}

	public static void drawRocketFluidTank(MatrixStack matrixStack, int left, int top, FluidStack stack, int capacity, int amount) {
		if (stack != null && !stack.isEmpty() && capacity > 0) {
			int maxHeight = 46;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) amount / (double) capacity));
			int offset = maxHeight - scaledHeight;
			GuiHelper.drawFluid(matrixStack, left, top + offset, 46, scaledHeight, stack);
		}

	}

	public static void drawFluidHorizontal(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null) {
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		drawFluid(matrixStack, left, top, (int) Math.ceil(width * ratio), height, stack);
	}

	public static void drawFluidVertical(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null) {
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		int scaledHeight = (int) Math.ceil(height * ratio);
		int offset = height - scaledHeight;
		drawFluid(matrixStack, left, top + offset, scaledHeight, height, stack);
	}

	public static void drawTiledSprite(MatrixStack matrixStack, int left, int top, int width, int height, TextureAtlasSprite sprite, int tileWidth, int tileHeight) {
		float uMin = sprite.getMinU();
		float uMax = sprite.getMaxU();
		float vMin = sprite.getMinV();
		float vMax = sprite.getMaxV();
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
		drawTiledSprite(matrixStack, left, top, width, height, tileWidth, tileHeight, uMin, uMax, vMin, vMax);
	}

	public static void drawTiledSprite(MatrixStack matrixStack, int left, int top, int width, int height, int tileWidth, int tileHeight, float uMin, float uMax, float vMin, float vMax) {
		Matrix4f matrix = matrixStack.getLast().getMatrix();

		int xTileCount = width / tileWidth;
		int xRemainder = width - (xTileCount * tileWidth);
		int yTileCount = height / tileWidth;
		int yRemainder = height - (yTileCount * tileWidth);

		int yStart = top + height;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int tiledWidth = (xTile == xTileCount) ? xRemainder : tileWidth;
				int tiledHeight = (yTile == yTileCount) ? yRemainder : tileWidth;
				int x = left + (xTile * tileWidth);
				int y = yStart - ((yTile + 1) * tileHeight);

				if (tiledWidth > 0 && tiledHeight > 0) {
					int maskRight = tileWidth - tiledWidth;
					int maskTop = tileHeight - tiledHeight;

					drawTextureWithMasking(matrix, x, y, tileWidth, tileHeight, maskTop, maskRight, 0, uMin, uMax, vMin, vMax);
				}
			}
		}
	}

	public static void drawTextureWithMasking(Matrix4f matrix, float left, float top, float tileWidth, float tileHeight, int maskTop, int maskRight, float zLevel, float uMin, float uMax, float vMin, float vMax) {
		uMax = uMax - (maskRight / tileWidth * (uMax - uMin));
		vMax = vMax - (maskTop / tileHeight * (vMax - vMin));

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(matrix, left, top + tileHeight, zLevel).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(matrix, left + tileWidth - maskRight, top + tileHeight, zLevel).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(matrix, left + tileWidth - maskRight, top + maskTop, zLevel).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(matrix, left, top + maskTop, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

	public static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.color4f(red, green, blue, alpha);
	}

	public static TextureAtlasSprite getStillFluidSprite(FluidStack stack) {
		Fluid fluid = stack.getFluid();
		FluidAttributes attributes = fluid.getAttributes();
		ResourceLocation fluidStill = attributes.getStillTexture(stack);
		return Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStill);
	}

	public static Rectangle2d getFluidTankBounds(int left, int top) {
		return new Rectangle2d(left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
	}

	public static void drawVertical(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
	}

	public static void drawVerticalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, width, remainHeight, width, height);
	}

	public static void drawHorizontal(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, ratioWidth, height, width, height);
	}

	public static void drawHorizontalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);
		int remainWidth = width - ratioWidth;
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		AbstractGui.blit(matrixStack, left + ratioWidth, top, ratioWidth, 0, remainWidth, height, width, height);
	}

	public static void innerBlit(Matrix4f matrix, float x1, float x2, float y1, float y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(matrix, (float)x1, (float)y2, (float)blitOffset).tex(minU, maxV).endVertex();
		bufferbuilder.pos(matrix, (float)x2, (float)y2, (float)blitOffset).tex(maxU, maxV).endVertex();
		bufferbuilder.pos(matrix, (float)x2, (float)y1, (float)blitOffset).tex(maxU, minV).endVertex();
		bufferbuilder.pos(matrix, (float)x1, (float)y1, (float)blitOffset).tex(minU, minV).endVertex();
		bufferbuilder.finishDrawing();
		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.draw(bufferbuilder);
	}

	public static void blit(MatrixStack matrixStack, float x, float y, float width, float height, float uOffset, float vOffset, float uWidth, float vHeight, int textureWidth, int textureHeight) {
		innerBlit(matrixStack, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
	}

	private static void innerBlit(MatrixStack matrixStack, float x1, float x2, float y1, float y2, int blitOffset, float uWidth, float vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
		innerBlit(matrixStack.getLast().getMatrix(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
	}

	public static void blit(MatrixStack matrixStack, float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		blit(matrixStack, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight);
	}

	private GuiHelper() {

	}

}
