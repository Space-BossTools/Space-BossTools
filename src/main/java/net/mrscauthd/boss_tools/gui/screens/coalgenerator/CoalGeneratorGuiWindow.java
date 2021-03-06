package net.mrscauthd.boss_tools.gui.screens.coalgenerator;

import net.mrscauthd.boss_tools.BossToolsMod;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.mrscauthd.boss_tools.util.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class CoalGeneratorGuiWindow extends ContainerScreen<CoalGeneratorGui.GuiContainer> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(BossToolsMod.ModId,"textures/screens/coal_generator_gui.png");

	public static final int FIRE_LEFT = 77;
	public static final int FIRE_TOP = 49;

	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private CustomTileEntity tileEntity;

	public CoalGeneratorGuiWindow(CoalGeneratorGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
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

		if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(this.getTileEntity().getGeneratingEnergyStorage())).build(), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		CustomTileEntity tileEntity = this.getTileEntity();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		GuiHelper.drawFire(ms, this.guiLeft + FIRE_LEFT, this.guiTop + FIRE_TOP, tileEntity.getPowerSystemGenerating().getStoredRatio());
		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getGeneratingEnergyStorage());
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}
}
