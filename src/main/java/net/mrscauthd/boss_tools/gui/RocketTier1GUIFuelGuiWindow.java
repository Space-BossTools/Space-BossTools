
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RocketTier1GUIFuelGuiWindow extends ContainerScreen<RocketTier1GUIFuelGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public RocketTier1GUIFuelGuiWindow(RocketTier1GUIFuelGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 167;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rocket_tier_1_gui_fuel.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		int fuel = (int) (entity.getPersistentData().getDouble("fuelgui"));
		List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();
		if (mouseX > guiLeft + 65 && mouseX < guiLeft + 114 && mouseY > guiTop + 20 && mouseY < guiTop + 69) {
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
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		// Tank
		double tank = ((entity.getPersistentData().getDouble("fuel")));
		// Textures
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettank.png"));
		this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		if (tank >= 17) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull1.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 37) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull2.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 51) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull3.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 68) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull4.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 85) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull5.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 102) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull6.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 119) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull7.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 136) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull8.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 153) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull9.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 170) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull10.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 187) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull11.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 204) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull12.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 221) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull13.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 238) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull14.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 255) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull15.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 272) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull16.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 289) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull17.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 306) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull18.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 323) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull19.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 340) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull20.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 357) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull21.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 374) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull22.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (tank >= 400) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull23.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
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
		this.font.drawString(ms, "Tier 1 Rocket", 56, 5, -13421773);
		this.font.drawString(ms, "Inventory", 6, 72, -13421773);
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
