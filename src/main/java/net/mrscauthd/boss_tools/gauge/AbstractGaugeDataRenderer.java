package net.mrscauthd.boss_tools.gauge;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public abstract class AbstractGaugeDataRenderer {

	private final IGaugeData data;

	public AbstractGaugeDataRenderer(IGaugeData data) {
		this.data = data;
	}

	public void toBytes(PacketBuffer buffer) {
		this.getData().write(buffer);
	}

	public void render(MatrixStack matrixStack, int left, int top) {
		this.drawBorder(matrixStack, left, top);

		int width = this.getWidth();
		int height = this.getHeight();
		int padding = this.getBorderWidth();
		Rectangle2d innerBounds = new Rectangle2d(left + padding, top + padding, width - padding * 2, height - padding * 2);
		this.drawBackground(matrixStack, innerBounds);
		this.drawContents(matrixStack, innerBounds);
		this.drawGaugeText(matrixStack, innerBounds);
	}

	protected void drawContents(MatrixStack matrixStack, Rectangle2d innerBounds) {

	}

	@Nullable
	public String getGaugeText() {
		return this.getData().getText().getString();
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

	protected void drawBackground(MatrixStack matrixStack, Rectangle2d innerBounds) {
		int tileColor = this.getBackgroundColor();
		double displayRatio = this.getData().getDisplayRatio();

		try {
			RenderSystem.enableBlend();
			RenderSystem.enableAlphaTest();
			GuiHelper.setGLColorFromInt(tileColor);

			TextureAtlasSprite tileTexture = this.getBackgroundTileTexture();

			if (tileTexture != null) {
				int tileWidth = this.getBackgroundTileWidth();
				int tileHeight = this.getBackgroundTileHeight();
				int ratioWidth = (int) Math.ceil(innerBounds.getWidth() * displayRatio);
				GuiHelper.drawTiledSprite(matrixStack, innerBounds.getX(), innerBounds.getY(), ratioWidth, innerBounds.getHeight(), tileTexture, tileWidth, tileHeight);
			}

			ResourceLocation texture = this.getBackgroundTexture();

			if (texture != null) {
				GuiHelper.drawHorizontal(matrixStack, innerBounds.getX(), innerBounds.getY(), innerBounds.getWidth(), innerBounds.getHeight(), texture, displayRatio);
			}

		} finally {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			RenderSystem.disableBlend();
		}

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
	public TextureAtlasSprite getBackgroundTileTexture() {
		return null;
	}

	@Nullable
	public ResourceLocation getBackgroundTexture() {
		return null;
	}

	public int getBackgroundColor() {
		return 0xFFFFFFFF;
	}

	public int getBackgroundTileWidth() {
		return 16;
	}

	public int getBackgroundTileHeight() {
		return 16;
	}

	public int getBorderWidth() {
		return 1;
	}

	public int getBorderColor() {
		return 0xFF000000;
	}

	public int getWidth() {
		return 100;
	}

	public int getHeight() {
		return 13;
	}

	public IGaugeData getData() {
		return this.data;
	}

}
