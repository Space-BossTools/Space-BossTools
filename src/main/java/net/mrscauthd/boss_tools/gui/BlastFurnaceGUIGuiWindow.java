
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.BlastFurnaceFireProcedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire9Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire8Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire7Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire6Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire5Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire4Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire3Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire2Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire14Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire13Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire12Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire11Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceFire10Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow9Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow8Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow7Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow6Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow5Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow4Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow3Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow2Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow23Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow22Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow21Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow20Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow1Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow19Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow18Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow17Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow16Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow15Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow14Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow13Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow12Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow11Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceArrow10Procedure;

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
public class BlastFurnaceGUIGuiWindow extends ContainerScreen<BlastFurnaceGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public BlastFurnaceGUIGuiWindow(BlastFurnaceGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 177;
		this.ySize = 172;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/blast_furnace_gui.png");
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off.png"));
		this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		if (BlastFurnaceFireProcedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_on.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 14, 14, 14, 14);
		}
		if (BlastFurnaceFire2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off2.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off3.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off4.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off5.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off6.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off7.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off8.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off9.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off10.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off11.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off12.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off13.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (BlastFurnaceFire14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off14.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/outputslot.png"));
		this.blit(ms, this.guiLeft + 99, this.guiTop + 33, 0, 0, -1, -1, -1, -1);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
		this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		if (BlastFurnaceArrow1Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_2.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_3.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_4.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_5.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_6.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_7.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_8.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_9.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_10.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_11.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_12.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_13.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_14.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_15.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_16.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_17.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_18.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_19.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow20Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_20.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow21Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_21.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow22Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_22.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow23Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_23.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
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
		this.font.drawString(ms, "Blast Furnace", 54, 5, -13421773);
		this.font.drawString(ms, "Inventory", 7, 76, -13421773);
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
