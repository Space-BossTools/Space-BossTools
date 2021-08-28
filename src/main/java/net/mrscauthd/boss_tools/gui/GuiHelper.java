package net.mrscauthd.boss_tools.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiHelper {

	public static void drawOxygen(MatrixStack matrixStack, int left, int top, double ratio) {
		ResourceLocation empty = new ResourceLocation("boss_tools:textures/oxygen_empty.png");
		ResourceLocation full = new ResourceLocation("boss_tools:textures/oxygen_full.png");
		drawVertical(matrixStack, left, top, 15, 14, empty, full, ratio);
	}

	public static void drawEnergy(MatrixStack matrixStack, int left, int top, double ratio) {
		ResourceLocation empty = new ResourceLocation("boss_tools:textures/energy_empty.png");
		ResourceLocation full = new ResourceLocation("boss_tools:textures/energy_full.png");
		drawVertical(matrixStack, left, top, 24, 48, empty, full, ratio);
	}

	public static void drawVertical(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation emtpty, ResourceLocation full, double ratio) {
		int ratioHeight = (int)(Math.ceil(height * ratio));
		int offset = height - ratioHeight;
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(emtpty);
		AbstractGui.blit(matrixStack, left, top, 0, 0, width,height, width, height);
		textureManager.bindTexture(full);
		AbstractGui.blit(matrixStack, left, top + offset, 0, offset, width, ratioHeight, width, height);
	}
	
	private GuiHelper() {

	}

}
