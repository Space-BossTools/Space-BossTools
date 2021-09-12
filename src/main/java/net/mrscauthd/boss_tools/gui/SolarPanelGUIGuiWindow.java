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
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
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
		this.playerInventoryTitleY = this.ySize - 92;
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
		super.drawGuiContainerForegroundLayer(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = this.getTileEntity();
		IEnergyStorage energyStorage = tileEntity.getPrimaryEnergyStorage();

		this.font.func_243248_b(ms, GaugeTextHelper.getStoredText(GaugeDataHelper.getEnergy(energyStorage.getEnergyStored())), this.titleX, 28, 0x3C3C3C);
		this.font.func_243248_b(ms, GaugeTextHelper.getCapacityText(GaugeDataHelper.getEnergy(energyStorage.getMaxEnergyStored())), this.titleX, 40, 0x3C3C3C);
		this.font.func_243248_b(ms, GaugeTextHelper.getMaxGenerationText(GaugeDataHelper.getEnergy(tileEntity.getMaxGeneration())), this.titleX, 52, 0x3C3C3C);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

}
