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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.OxygenGeneratorBlock;
import net.mrscauthd.boss_tools.machines.OxygenGeneratorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class OxygenBulletGeneratorGUIGuiWindow extends ContainerScreen<OxygenBulletGeneratorGUIGui.GuiContainerMod> {
	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/oxygen_bullet_generator_gui.png");
	public static final int OXYGEN_LEFT = 76;
	public static final int OXYGEN_TOP = 28;
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private CustomTileEntity tileEntity;

	public OxygenBulletGeneratorGUIGuiWindow(OxygenBulletGeneratorGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
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

		this.minecraft.getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getEnergyStorage().getStoredRatio());
		GuiHelper.drawOxygen(ms, this.guiLeft + OXYGEN_LEFT, this.guiTop + OXYGEN_TOP, tileEntity.getOxygenPowerSystem().getStoredRatio());

		this.minecraft.getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenlarge6.png"));
		AbstractGui.blit(ms, this.guiLeft + 10, this.guiTop - 10, 0, 0, 21, 11, 21, 11);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		double range = this.getTileEntity().getRange();
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String rangeToString = numberInstance.format(range);

		
		String oxygenText = "Using: " + tileEntity.getOxygenPowerSystem().getPowerForOperation() + " Oxygen/t";
		int oxygenWidth = this.font.getStringWidth(oxygenText);
		
		this.font.drawString(ms, "Oxygen Generator", 37, 5, 0x333333);
		this.font.drawString(ms, "Inventory", 6, 74, 0x333333);
		this.font.drawString(ms, String.format("%sx%s", rangeToString, rangeToString), 12, -8, 0x339900);
		this.font.drawString(ms, oxygenText, this.xSize - oxygenWidth - 5, 74, 0x333333);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);

		this.addButton(new Button(this.guiLeft - 20, this.guiTop + 25, 20, 20, new StringTextComponent("-"), e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenGeneratorBlock.SetLargeMessage(pos, false));
		}));
		this.addButton(new Button(this.guiLeft - 20, this.guiTop + 5, 20, 20, new StringTextComponent("+"), e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenGeneratorBlock.SetLargeMessage(pos, true));
		}));
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

}
