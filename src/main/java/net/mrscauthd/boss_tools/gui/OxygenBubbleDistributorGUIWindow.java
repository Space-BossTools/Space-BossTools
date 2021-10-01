package net.mrscauthd.boss_tools.gui;

import java.text.NumberFormat;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class OxygenBubbleDistributorGUIWindow extends ContainerScreen<OxygenBubbleDistributorGUI.GuiContainerMod> {
	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/oxygen_bubble_distributor_gui.png");
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

	private CustomTileEntity tileEntity;
	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public OxygenBubbleDistributorGUIWindow(OxygenBubbleDistributorGUI.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.xSize = 177;
		this.ySize = 172;
		this.playerInventoryTitleY = this.ySize - 92;
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
		this.updateWorkingAreaVisibleButton();
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = (CustomTileEntity) this.getTileEntity();

		if (this.getInputTankBounds().contains(mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeDataHelper.getFluid(tileEntity.getInputTank()).getText(), mouseX, mouseY);
		} else if (this.getOutputTankBounds().contains(mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeDataHelper.getOxygen(tileEntity.getOutputTank()).getText(), mouseX, mouseY);
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
		GuiHelper.drawOxygenTank(ms, this.guiLeft + OUTPUT_TANK_LEFT, this.guiTop + OUTPUT_TANK_TOP, tileEntity.getOutputTank());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(ms, mouseX, mouseY);

		double range = this.getTileEntity().getRange();
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String rangeToString = numberInstance.format((range * 2.0D) + 1.0D);
		TranslationTextComponent workingAreaText = new TranslationTextComponent("gui.boss_tools.oxygen_bubble_distributor.workingarea.text", rangeToString, rangeToString, rangeToString);

		int sideWidth = 2;
		int sidePadding = 2;
		int workingAreaWidth = this.font.getStringPropertyWidth(workingAreaText) + (sidePadding * 2);
		int workingAreaHeight = 11;
		int workingAreaLeft = this.workingAreaVisibleButton.x + this.workingAreaVisibleButton.getWidth() - this.guiLeft;
		int workignAreaTop = -workingAreaHeight;

		int workingAreaOffsetX = workingAreaLeft;
		this.minecraft.getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/workingarea_side.png"));
		AbstractGui.blit(ms, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.minecraft.getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/workingarea_middle.png"));
		AbstractGui.blit(ms, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;

		this.minecraft.getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/workingarea_side.png"));
		AbstractGui.blit(ms, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.font.func_243248_b(ms, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x339900);

		ITextComponent oxygenText = GaugeTextHelper.getUsingText(GaugeDataHelper.getOxygen(this.getTileEntity().getOxygenUsing(range)));
		int oxygenWidth = this.font.getStringPropertyWidth(oxygenText);
		this.font.func_243248_b(ms, oxygenText, this.xSize - oxygenWidth - 5, this.playerInventoryTitleY, 0x333333);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);

		this.addButton(new Button(this.guiLeft - 20, this.guiTop + 25, 20, 20, new StringTextComponent("-"), e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeRangeMessage(pos, false));
		}));
		this.addButton(new Button(this.guiLeft - 20, this.guiTop + 5, 20, 20, new StringTextComponent("+"), e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeRangeMessage(pos, true));
		}));
		this.workingAreaVisibleButton = this.addButton(new Button(this.guiLeft, this.guiTop - 20, 20, 20, new StringTextComponent(""), e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage(pos, !this.cachedWorkingAreaVisible));
		}));

		this.resizeWorkingAreaVisibleButton();
		this.refreshWorkingAreaVisibleButtonMessage();
	}

	private void updateWorkingAreaVisibleButton() {
		boolean next = this.getTileEntity().isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next) {
			this.cachedWorkingAreaVisible = next;
			this.refreshWorkingAreaVisibleButtonMessage();
		}
	}

	private ITextComponent getWorkingAreaVisibleMessage(boolean visible) {
		String prefix = "gui.boss_tools.oxygen_bubble_distributor.workingarea.";
		String method = visible ? "hide" : "show";
		return new TranslationTextComponent(prefix + method);
	}

	private void refreshWorkingAreaVisibleButtonMessage() {
		ITextComponent message = this.getWorkingAreaVisibleMessage(this.cachedWorkingAreaVisible);
		this.workingAreaVisibleButton.setMessage(message);
	}

	private void resizeWorkingAreaVisibleButton() {
		int messageWidth = this.workingAreaVisibleButton.getHeightRealms();
		messageWidth = Math.max(messageWidth, this.font.getStringPropertyWidth(this.getWorkingAreaVisibleMessage(true)));
		messageWidth = Math.max(messageWidth, this.font.getStringPropertyWidth(this.getWorkingAreaVisibleMessage(false)));
		this.workingAreaVisibleButton.setWidth(messageWidth + 8);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

}
