
package net.mrscauthd.boss_tools.gui.screens;

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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

@OnlyIn(Dist.CLIENT)
public class RocketGUIWindow extends ContainerScreen<RocketGUI.GuiContainerMod> {

	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rocket_tier_1_gui_fuel.png");
	public static final int TANK_LEFT = 93;
	public static final int TANK_TOP = 21;
	public static final int TANK_WIDTH = 48;
	public static final int TANK_HEIGHT = 48;
	public static final int ARROW_LEFT = 66;
	public static final int ARROW_TOP = 29;

	public RocketGUIWindow(RocketGUI.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 176;
		this.ySize = 167;
		this.playerInventoryTitleY = this.ySize - 92;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		if (this.getTankBounds().contains(mouseX, mouseY)) {
			RocketAbstractEntity entity = this.getContainer().getEntity();
			GaugeData data = GaugeDataHelper.getFluid(new FluidStack(ModInnet.FUEL_STILL.get(), entity.getFuel()), entity.getFuelCapacity());

			List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();
			fuel2.add(new StringTextComponent("\u00A79" + data.getValue().getDisplayName().getString()));
			fuel2.add(new TranslationTextComponent("%s, %s", data.getAmountText(), (int) (data.getDisplayRatio() * 100) + "%"));
			this.func_243308_b(ms, fuel2, mouseX, mouseY);
		}

	}

	public Rectangle2d getTankBounds() {
		return new Rectangle2d(this.guiLeft + TANK_LEFT, this.guiTop + TANK_TOP, TANK_WIDTH, TANK_HEIGHT);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		// Textures
		RocketAbstractEntity entity = this.getContainer().getEntity();
		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_STILL.get(), entity.getFuel());
		GuiHelper.drawRocketFluidTank(ms, this.guiLeft + TANK_LEFT + 1, this.guiTop + TANK_TOP + 1, fluidStack, entity.getFuelCapacity());
	}

}
