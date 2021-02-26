
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.RocketTank9Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank8Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank7Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank6Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank5Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank4Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank3Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank2Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank23Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank22Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank21Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank20Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank1Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank19Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank18Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank17Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank16Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank15Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank14Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank13Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank12Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank11Procedure;
import net.mrscauthd.boss_tools.procedures.RocketTank10Procedure;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@OnlyIn(Dist.CLIENT)
public class RocketTier3GuiFuelGuiWindow extends ContainerScreen<RocketTier3GuiFuelGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public RocketTier3GuiFuelGuiWindow(RocketTier3GuiFuelGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 167;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rocket_tier_3_gui_fuel.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettank.png"));
		this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		if (RocketTank1Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull1.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank2Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull2.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank3Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull3.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank4Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull4.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank5Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull5.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank6Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull6.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank7Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull7.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank8Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull8.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank9Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull9.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank10Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull10.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank11Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull11.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank12Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull12.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank13Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull13.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank14Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull14.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank15Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull15.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank16Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull16.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank17Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull17.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank18Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull18.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank19Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull19.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank20Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull20.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank21Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull21.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank22Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rockettankfull22.png"));
			this.blit(ms, this.guiLeft + 66, this.guiTop + 21, 0, 0, 48, 48, 48, 48);
		}
		if (RocketTank23Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
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
		this.font.drawString(ms, "Tier 3 Rocket", 56, 5, -13421773);
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
