package net.mrscauthd.boss_tools.compat.theoneprobe;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mcjty.theoneprobe.api.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.PacketBuffer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public abstract class GaugeElement implements IElement {

	private IGaugeValue gaugeValue;

	public GaugeElement(IGaugeValue gaugeValue) {
		this.gaugeValue = gaugeValue;
	}

	public GaugeElement(PacketBuffer buffer) {

	}

	@Override
	public void toBytes(PacketBuffer buffer) {
		this.gaugeValue.write(buffer);
	}

	@Override
	public void render(MatrixStack matrixStack, int left, int top) {
		this.drawBorder(matrixStack, left, top);

		int width = this.getWidth();
		int height = this.getHeight();
		int padding = this.getBorderWidth();
		Rectangle2d innerBounds = new Rectangle2d(left + padding, top + padding, width - padding * 2, height - padding * 2);
		this.drawGaugeTileTexture(matrixStack, innerBounds);
		this.drawContents(matrixStack, innerBounds);
		this.drawGaugeText(matrixStack, innerBounds);
	}

	protected void drawContents(MatrixStack matrixStack, Rectangle2d innerBounds) {

	}
	
	@Nullable
	public String getGaugeText() {
		return this.getGaugeValue().getText().getString();
	}

	protected void drawGaugeText(MatrixStack matrixStack, Rectangle2d innerBounds) {
		String text = this.getGaugeText();

		if (!StringUtils.isEmpty(text)) {
			int color = this.getTextColor();
			int textPadding = 2;
			Rectangle2d textBounds = new Rectangle2d(innerBounds.getX() + textPadding, innerBounds.getY(), innerBounds.getWidth() - textPadding, innerBounds.getHeight());
			this.drawText(matrixStack, textBounds, text, color);
		}
	}

	protected void drawText(MatrixStack matrixStack, Rectangle2d bounds, String text, int color) {
		this.drawText(Minecraft.getInstance(), matrixStack, bounds, text, color);
	}

	protected void drawText(Minecraft minecraft, MatrixStack matrixStack, Rectangle2d bounds, String text, int color) {
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int textWidth = fontRenderer.getStringWidth(text);

		float scale = Math.min(1.0F, (float) bounds.getWidth() / (float) textWidth);
		float offsetX = 0.0F;
		float offsetY = (bounds.getHeight() - ((fontRenderer.FONT_HEIGHT - 1) * scale)) / 2.0F;
		float scaledX = (bounds.getX() + offsetX) / scale;
		float scaledY = (bounds.getY() + offsetY) / scale;

		matrixStack.push();
		matrixStack.scale(scale, scale, scale);
		fontRenderer.drawStringWithShadow(matrixStack, text, scaledX, scaledY, color);
		matrixStack.pop();
	}

	protected void drawGaugeTileTexture(MatrixStack matrixStack, Rectangle2d innerBounds) {
		TextureAtlasSprite tileTexture = this.getTileTexture();

		if (tileTexture == null) {
			return;
		}

		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		int tileColor = this.getTileColor();
		int tileWidth = this.getTileWidth();
		int tileHeight = this.getTileHeight();
		int ratioWidth = (int) Math.ceil(innerBounds.getWidth() * this.getGaugeValue().getDisplayRatio());
		GuiHelper.drawTiledSprite(matrixStack, innerBounds.getX(), innerBounds.getY(), ratioWidth, innerBounds.getHeight(), tileColor, tileTexture, tileWidth, tileHeight);
		RenderSystem.disableAlphaTest();
		RenderSystem.disableBlend();
	}

	protected void drawBorder(MatrixStack matrixStack, int left, int top) {
		int width = this.getWidth();
		int height = this.getHeight();
		int borderColor = this.getBorderColor();
		int padding = this.getBorderWidth();

		AbstractGui.fill(matrixStack, left, top, left + width - padding, top + padding, borderColor);
		AbstractGui.fill(matrixStack, left, top, left + padding, top + height - padding, borderColor);
		AbstractGui.fill(matrixStack, left + width - padding, top, left + width, top + height - padding, borderColor);
		AbstractGui.fill(matrixStack, left, top + height - padding, left + width, top + height, borderColor);
	}

	public int getTextColor() {
		return 0xFFFFFFFF;
	}

	@Nullable
	public abstract TextureAtlasSprite getTileTexture();

	public int getTileColor() {
		return 0xFFFFFFFF;
	}

	public int getTileWidth() {
		return 16;
	}

	public int getTileHeight() {
		return 16;
	}

	public int getBorderWidth() {
		return 1;
	}

	public int getBorderColor() {
		return 0xFF000000;
	}

	@Override
	public int getWidth() {
		return 100;
	}

	@Override
	public int getHeight() {
		return 13;
	}

	public IGaugeValue getGaugeValue() {
		return this.gaugeValue;
	}

}
