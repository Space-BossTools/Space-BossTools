package net.mrscauthd.boss_tools.gui.guihelper;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

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

	public static void renderEnergyTooltip(MatrixStack matrixStack, int left, int top, Screen screen, int stored, int capacity) {
		screen.renderTooltip(matrixStack, new StringTextComponent(stored + " FE / " + capacity + " FE"), left, top);
	}

	public static void renderEnergyTooltip(MatrixStack matrixStack, int left, int top, Screen screen, IEnergyStorage energyStorage) {
		renderEnergyTooltip(matrixStack, left, top, screen, energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
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
