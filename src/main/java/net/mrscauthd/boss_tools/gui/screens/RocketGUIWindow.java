
package net.mrscauthd.boss_tools.gui.screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
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
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

@OnlyIn(Dist.CLIENT)
public class RocketGUIWindow extends ContainerScreen<RocketGUI.GuiContainerMod> {

	public static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rocket_tier_1_gui_fuel.png");
	public static final ResourceLocation fuel_bar = new ResourceLocation("boss_tools:textures/rocket_fuel_bar.png");

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

		RocketAbstractEntity entity = this.getContainer().getEntity();
		int fuel = entity.getFuel();
		int capacity = entity.getFuelCapacity();

		List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();

		if (mouseX > guiLeft + 65 && mouseX < guiLeft + 114 && mouseY > guiTop + 20 && mouseY < guiTop + 69) {
			GaugeData data = GaugeDataHelper.getFluid(new FluidStack(ModInnet.FUEL_STILL.get(), fuel), capacity);
			fuel2.add(new StringTextComponent("\u00A79" + data.getValue().getDisplayName().getString()));
			fuel2.add(new TranslationTextComponent("%s, %s", data.getAmountText(), (int) (data.getDisplayRatio() * 100) + "%"));
			this.func_243308_b(ms, fuel2, mouseX, mouseY);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		// Tank
		RocketAbstractEntity entity = this.getContainer().getEntity();
		int fuel = entity.getFuel();
		int capacity = entity.getFuelCapacity();

		// Textures
		Minecraft.getInstance().getTextureManager().bindTexture(fuel_bar);
		AbstractGui.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), fuel);
		GuiHelper.drawRocketFluidTank(ms, this.guiLeft + 67, this.guiTop + 22, fluidStack, capacity);
	}

}
