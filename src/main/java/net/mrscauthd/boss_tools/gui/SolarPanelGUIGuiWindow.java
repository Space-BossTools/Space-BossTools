package net.mrscauthd.boss_tools.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;
import net.mrscauthd.boss_tools.machines.SolarPanelBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class SolarPanelGUIGuiWindow extends ContainerScreen<SolarPanelGUIGui.GuiContainerMod> {
	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/solar_panel_gui.png");

	private CustomTileEntity tileEntity;

	public SolarPanelGUIGuiWindow(SolarPanelGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {

		CustomTileEntity tileEntity = this.getTileEntity();
		EnergyStorageBasic energyStorage = tileEntity.getEnergyStorage();

		this.font.drawString(ms, "Solar Panel", 58, 5, 0x333333);
		this.font.drawString(ms, "Charge: " + energyStorage.getEnergyStored() + " FE", 7, 26, 0x3C3C3C);
		this.font.drawString(ms, "Capacity: " + energyStorage.getMaxEnergyStored() + " FE", 7, 38, 0x3C3C3C);
		this.font.drawString(ms, "Generation: " + tileEntity.getGeneratePerTick() + " FE/t", 7, 50, 0x3C3C3C);

		this.font.drawString(ms, "Inventory", 6, 73, 0x333333);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

}
