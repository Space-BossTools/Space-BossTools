
package net.mrscauthd.boss_tools.gui;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

@OnlyIn(Dist.CLIENT)
public class RoverGuiWindow extends ContainerScreen<RoverGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = RoverGui.guistate;
	public RoverGuiWindow(RoverGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 176;
	}

	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rover_1_gui.png");

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// Tooltip
		int fuel = fuel = container.rover.getDataManager().get(RoverEntity.FUEL) / 30;

		List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();
		if (mouseX > guiLeft + 8 && mouseX < guiLeft + 23 && mouseY > guiTop + 10 && mouseY < guiTop + 59) {
			if (fuel >= 1) {
				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			} else {
				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Empty"));
			}
			fuel2.add(ITextComponent.getTextComponentOrEmpty(String.valueOf(fuel) + "%"));
			this.func_243308_b(ms, fuel2, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		int fuel = container.rover.getDataManager().get(RoverEntity.FUEL);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), fuel);
		GuiHelper.drawFluidTank(ms, this.guiLeft + 9, this.guiTop + 11, fluidStack, 3000);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeScreen();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		this.font.drawString(ms, "Inventory", 7, 82, -12829636);
		this.font.drawString(ms, "Rover", 71, 5, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		minecraft.keyboardListener.enableRepeatEvents(true);
	}
}
