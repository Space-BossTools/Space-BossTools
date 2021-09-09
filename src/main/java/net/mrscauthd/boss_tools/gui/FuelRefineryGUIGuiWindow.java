package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.FuelRefineryBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class FuelRefineryGUIGuiWindow extends ContainerScreen<FuelRefineryGUIGui.GuiContainerMod> {
	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/fuel_refinery_gui.png");
	public static final int INPUT_TANK_LEFT = 9;
	public static final int INPUT_TANK_TOP = 21;
	public static final int INPUT_SOURCE_LEFT = 25;
	public static final int INPUT_SOURCE_TOP = 21;
	public static final int INPUT_SINK_LEFT = 25;
	public static final int INPUT_SINK_TOP = 21;
	public static final int OUTPUT_TANK_LEFT = 75;
	public static final int OUTPUT_TANK_TOP = 21;
	public static final int OUTPUT_SOURCE_LEFT = 91;
	public static final int OUTPUT_SOURCE_TOP = 21;
	public static final int OUTPUT_SINK_LEFT = 91;
	public static final int OUTPUT_SINK_TOP = 21;
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 48;
	public static final int ARROW_TOP = 36;

	private final CustomTileEntity tileEntity;

	public FuelRefineryGUIGuiWindow(FuelRefineryGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.xSize = 177;
		this.ySize = 172;
	}

	public Rectangle2d getInputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.guiLeft + INPUT_TANK_LEFT, this.guiTop + INPUT_TANK_TOP);
	}

	public Rectangle2d getOutputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.guiLeft + OUTPUT_TANK_LEFT, this.guiTop + OUTPUT_TANK_TOP);
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = (CustomTileEntity) this.getTileEntity();

		if (this.getInputTankBounds().contains(mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeDataHelper.getFluid(tileEntity.getInputTank()).getText(), mouseX, mouseY);
		} else if (this.getOutputTankBounds().contains(mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeDataHelper.getFluid(tileEntity.getOutputTank()).getText(), mouseX, mouseY);
		} else if (this.getEnergyBounds().contains(mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeDataHelper.getEnergy(tileEntity).getText(), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		CustomTileEntity tileEntity = this.getTileEntity();

		this.minecraft.getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getPrimaryEnergyStorage());
		GuiHelper.drawFluidTank(ms, this.guiLeft + INPUT_TANK_LEFT, this.guiTop + INPUT_TANK_TOP, tileEntity.getInputTank());
		GuiHelper.drawFluidTank(ms, this.guiLeft + OUTPUT_TANK_LEFT, this.guiTop + OUTPUT_TANK_TOP, tileEntity.getOutputTank());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		this.font.drawString(ms, "Fuel Refinery", 54, 7, -13421773);
		this.font.drawString(ms, "Inventory", 7, 79, -13421773);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

}