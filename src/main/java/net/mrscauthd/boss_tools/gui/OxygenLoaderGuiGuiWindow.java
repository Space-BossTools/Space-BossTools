package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class OxygenLoaderGuiGuiWindow extends ContainerScreen<OxygenLoaderGuiGui.GuiContainerMod> {
	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/oxygen_loader_gui.png");
	public static final int OXYGEN_LEFT = 76;
	public static final int OXYGEN_TOP = 40;
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private CustomTileEntity tileEntity;

	public OxygenLoaderGuiGuiWindow(OxygenLoaderGuiGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.xSize = 176;
		this.ySize = 167;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		if (this.getEnergyBounds().contains(mouseX, mouseY)) {
			GuiHelper.renderEnergyTooltip(ms, mouseX, mouseY, this, this.getTileEntity().getEnergyStorage());
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		CustomTileEntity tileEntity = this.getTileEntity();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		GuiHelper.drawOxygen(ms, this.guiLeft + OXYGEN_LEFT, this.guiTop + OXYGEN_TOP, tileEntity.getOxygenPowerSystem().getStoredRatio());
		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getEnergyStorage().getStoredRatio());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		this.font.drawString(ms, "Oxygen Loader", 45, 5, 0x333333);
		this.font.drawString(ms, "Inventory", 6, 74, 0x333333);
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

}
