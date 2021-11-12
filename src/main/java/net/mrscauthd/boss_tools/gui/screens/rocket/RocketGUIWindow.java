package net.mrscauthd.boss_tools.gui.screens.rocket;

import net.minecraft.client.renderer.Rectangle2d;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RocketGUIWindow extends ContainerScreen<RocketGUI.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rocket_gui_fuel.png");
	private static final ResourceLocation fuel_bar = new ResourceLocation("boss_tools:textures/rocket_fuel_bar.png");

	public RocketGUIWindow(RocketGUI.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 176;
		this.ySize = 167;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		int fuel = 0;

		if (container.rocket instanceof RocketTier1Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier1Entity.FUEL) / 3;
		}
		if (container.rocket instanceof RocketTier2Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier2Entity.FUEL) / 3;
		}
		if (container.rocket instanceof RocketTier3Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier3Entity.FUEL) / 3;
		}

		List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			if (fuel >= 1) {

				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			} else {

				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Empty"));
			}

			fuel2.add(ITextComponent.getTextComponentOrEmpty(fuel + "%"));
			this.func_243308_b(ms, fuel2, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		int fuel = 0;

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		this.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);


		if (container.rocket instanceof RocketTier1Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier1Entity.FUEL);
		}
		if (container.rocket instanceof RocketTier2Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier2Entity.FUEL);
		}
		if (container.rocket instanceof RocketTier3Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier3Entity.FUEL);
		}

		Minecraft.getInstance().getTextureManager().bindTexture(fuel_bar);
		this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), 1);
		GuiHelper.drawRocketFluidTank(ms, this.guiLeft + 67, this.guiTop + 22, fluidStack, 300, fuel);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		if (container.rocket instanceof RocketTier1Entity) {
			this.font.drawString(ms, "Tier 1 Rocket", 56, 5, -13421773);
		}
		if (container.rocket instanceof RocketTier2Entity) {
			this.font.drawString(ms, "Tier 2 Rocket", 56, 5, -13421773);
		}
		if (container.rocket instanceof RocketTier3Entity) {
			this.font.drawString(ms, "Tier 3 Rocket", 56, 5, -13421773);
		}

		this.font.drawString(ms, "Inventory", 6, 72, -13421773);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getRocketFluidTankBounds(this.guiLeft + 66, this.guiTop + 21);
	}
}
