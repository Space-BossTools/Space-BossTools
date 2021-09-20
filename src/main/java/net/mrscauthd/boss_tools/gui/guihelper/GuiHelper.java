package net.mrscauthd.boss_tools.gui.guihelper;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class GuiHelper {

	public static final ResourceLocation FIRE_PATH = new ResourceLocation("boss_tools:textures/fire_on.png");
	public static final int FIRE_WIDTH = 14;
	public static final int FIRE_HEIGHT = 14;
	public static final ResourceLocation ARROW_PATH = new ResourceLocation("boss_tools:textures/animated_arrow_full.png");
	public static final int ARROW_WIDTH = 24;
	public static final int ARROW_HEIGHT = 17;
	public static final ResourceLocation OXYGEN_PATH = new ResourceLocation("boss_tools:textures/oxygen_full.png");
	public static final int OXYGEN_WIDTH = 14;
	public static final int OXYGEN_HEIGHT = 14;
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

	public static void drawOxygen(MatrixStack matrixStack, int left, int top, double ratio) {
		drawVertical(matrixStack, left, top, OXYGEN_WIDTH, OXYGEN_HEIGHT, OXYGEN_PATH, ratio);
	}

	public static Rectangle2d getOxygenBounds(int left, int top) {
		return new Rectangle2d(left, top, OXYGEN_WIDTH, OXYGEN_HEIGHT);
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
		setGLColorFromInt(attributes.getColor(stack));
		drawTiledSprite(matrixStack, left, top, width, height, fluidStillSprite, 16, 16);
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
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
		Matrix4f matrix = matrixStack.getLast().getMatrix();

		final int xTileCount = width / tileWidth;
		final int xRemainder = width - (xTileCount * tileWidth);
		final int yTileCount = height / tileWidth;
		final int yRemainder = height - (yTileCount * tileWidth);

		final int yStart = top + height;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int tiledWidth = (xTile == xTileCount) ? xRemainder : tileWidth;
				int tiledHeight = (yTile == yTileCount) ? yRemainder : tileWidth;
				int x = left + (xTile * tileWidth);
				int y = yStart - ((yTile + 1) * tileHeight);

				if (tiledWidth > 0 && tiledHeight > 0) {
					int maskRight = tileWidth - tiledWidth;
					int maskTop = tileHeight - tiledHeight;

					drawTextureWithMasking(matrix, x, y, sprite, tileWidth, tileHeight, maskTop, maskRight, 0);
				}
			}
		}

	}

	private static void drawTextureWithMasking(Matrix4f matrix, float left, float top, TextureAtlasSprite textureSprite, float tileWidth, float tileHeight, int maskTop, int maskRight, float zLevel) {
		float uMin = textureSprite.getMinU();
		float uMax = textureSprite.getMaxU();
		float vMin = textureSprite.getMinV();
		float vMax = textureSprite.getMaxV();

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
		Minecraft minecraft = Minecraft.getInstance();
		Fluid fluid = stack.getFluid();
		FluidAttributes attributes = fluid.getAttributes();
		ResourceLocation fluidStill = attributes.getStillTexture(stack);
		return minecraft.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStill);
	}

	public static Rectangle2d getFluidTankBounds(int left, int top) {
		return new Rectangle2d(left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
	}

	public static void drawVertical(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
	}

	public static void drawVerticalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, width, remainHeight, width, height);
	}

	public static void drawHorizontal(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, ratioWidth, height, width, height);
	}

	public static void drawHorizontalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);
		int remainWidth = width - ratioWidth;
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(resource);
		AbstractGui.blit(matrixStack, left + ratioWidth, top, ratioWidth, 0, remainWidth, height, width, height);
	}

	private GuiHelper() {

	}

}
