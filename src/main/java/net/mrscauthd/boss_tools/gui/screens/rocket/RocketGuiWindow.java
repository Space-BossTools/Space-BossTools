package net.mrscauthd.boss_tools.gui.screens.rocket;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

@OnlyIn(Dist.CLIENT)
public class RocketGuiWindow extends ContainerScreen<RocketGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId, "textures/screens/rocket_gui.png");

	public RocketGuiWindow(RocketGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 176;
		this.ySize = 167;
		this.playerInventoryTitleY = this.ySize - 93;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		List<ITextComponent> fuelToolTip = new ArrayList<ITextComponent>();

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			int fuel = container.rocket.getFuel();
			int full = container.rocket.getFuelOfFull();
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, full)), TextFormatting.WHITE));
			this.func_243308_b(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		int fuel = container.rocket.getFuel();
		int full = container.rocket.getFuelOfFull();

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), fuel);
		GuiHelper.drawRocketFluidTank(ms, this.guiLeft + 67, this.guiTop + 22, fluidStack, full);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		TranslationTextComponent title = new TranslationTextComponent("container." + container.rocket.getType());

		this.font.drawString(ms, title.getString(), (float) (this.xSize / 2) - 33, (float) this.titleY, 4210752);

		this.font.func_243248_b(ms, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, 4210752);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getRocketFluidTankBounds(this.guiLeft + 66, this.guiTop + 21);
	}
}
