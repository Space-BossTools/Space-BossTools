package net.mrscauthd.boss_tools.gui.guihelper;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

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

	public static void renderEnergyTooltip(MatrixStack matrixStack, int left, int top, Screen screen, int stored, int capacity) {
		screen.renderTooltip(matrixStack, new StringTextComponent(stored + " FE / " + capacity + " FE"), left, top);
	}

	public static void renderEnergyTooltip(MatrixStack matrixStack, int left, int top, Screen screen, IEnergyStorage energyStorage) {
		renderEnergyTooltip(matrixStack, left, top, screen, energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
	}

	public static void renderFluidTooltip(MatrixStack matrixStack, int left, int top, Screen screen, IFluidTank tank) {
		renderFluidTooltip(matrixStack, left, top, screen, tank.getFluid(), tank.getCapacity());
	}

	public static ITextComponent getFluidTooltip(FluidStack stack, int capacity) {
		return new TranslationTextComponent("%s: " + stack.getAmount() + " mb / " + capacity + " mb", stack.getDisplayName());
	}

	public static ITextComponent getFluidTooltip(Fluid fluid, int amount, int capacity) {
		return getFluidTooltip(new FluidStack(fluid, amount), capacity);
	}

	public static void renderFluidTooltip(MatrixStack matrixStack, int left, int top, Screen screen, FluidStack stack, int capacity) {
		screen.renderTooltip(matrixStack, getFluidTooltip(stack, capacity), left, top);
	}

	public static void renderFluidTooltip(MatrixStack matrixStack, int left, int top, Screen screen, Fluid fluid, int amount, int capacity) {
		screen.renderTooltip(matrixStack, getFluidTooltip(fluid, amount, capacity), left, top);
	}

	public static void drawArrow(MatrixStack matrixStack, int left, int top, double ratio) {
		GuiHelper.drawHorizontal(matrixStack, left, top, ARROW_WIDTH, ARROW_HEIGHT, ARROW_PATH, ratio);
	}

	public static Rectangle2d getArrorBounds(int left, int top) {
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
		if (stack == null) {
			return;
		}

		Fluid fluid = stack.getFluid();

		if (fluid == null) {
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);
		FluidAttributes attributes = fluid.getAttributes();
		int fluidColor = attributes.getColor(stack);
		drawTiledSprite(matrixStack, left, top, width, height, fluidColor, 0, fluidStillSprite);
	}

	private static void drawTiledSprite(MatrixStack matrixStack, final int xPosition, final int yPosition, final int tiledWidth, final int tiledHeight, int color, int scaledAmount, TextureAtlasSprite sprite) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
		Matrix4f matrix = matrixStack.getLast().getMatrix();
		setGLColorFromInt(color);

		final int xTileCount = tiledWidth / 16;
		final int xRemainder = tiledWidth - (xTileCount * 16);
		final int yTileCount = tiledHeight / 16;
		final int yRemainder = tiledHeight - (yTileCount * 16);

		final int yStart = yPosition + tiledHeight;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int width = (xTile == xTileCount) ? xRemainder : 16;
				int height = (yTile == yTileCount) ? yRemainder : 16;
				int x = xPosition + (xTile * 16);
				int y = yStart - ((yTile + 1) * 16);

				if (width > 0 && height > 0) {
					int maskTop = 16 - height;
					int maskRight = 16 - width;

					drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight, 100);
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private static void drawTextureWithMasking(Matrix4f matrix, float left, float top, TextureAtlasSprite textureSprite, int maskTop, int maskRight, float zLevel) {
		float uMin = textureSprite.getMinU();
		float uMax = textureSprite.getMaxU();
		float vMin = textureSprite.getMinV();
		float vMax = textureSprite.getMaxV();

		uMax = uMax - (maskRight / 16.0F * (uMax - uMin));
		vMax = vMax - (maskTop / 16.0F * (vMax - vMin));

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(matrix, left, top + 16.0F, zLevel).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(matrix, left + 16.0F - maskRight, top + 16.0F, zLevel).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(matrix, left + 16.0F - maskRight, top + maskTop, zLevel).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(matrix, left, top + maskTop, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

	private static void setGLColorFromInt(int color) {
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
