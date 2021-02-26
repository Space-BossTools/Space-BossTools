
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrowProcedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow9Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow8Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow7Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow6Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow5Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow4Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow3Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow2Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow19Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow18Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow17Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow16Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow15Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow14Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow13Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow12Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow11Procedure;
import net.mrscauthd.boss_tools.procedures.BlastFurnaceGuiArrow10Procedure;
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation0.png"));
		this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		if (BlastFurnaceGuiArrowProcedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation1.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation2.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation3.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation4.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation5.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation6.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation7.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation8.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation9.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation10.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation11.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation12.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation13.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation14.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation15.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation16.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation17.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation18.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation19.png"));
			this.blit(ms, this.guiLeft + 72, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
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
		this.blit(ms, this.guiLeft + 99, this.guiTop + 33, 0, 0, 26, 26, 26, 26);
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
