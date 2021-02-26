
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.SpaceStationIronLogicProcedure;
import net.mrscauthd.boss_tools.procedures.SpaceStationGuiLogicProcedure;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@OnlyIn(Dist.CLIENT)
public class Tier1SpaceStationMoonGuiGuiWindow extends ContainerScreen<Tier1SpaceStationMoonGuiGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public Tier1SpaceStationMoonGuiGuiWindow(Tier1SpaceStationMoonGuiGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 255;
		this.ySize = 170;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/guirockettier1newguinew.png"));
		this.blit(ms, this.guiLeft + 0, this.guiTop + 0, 0, 0, 256, 256, 256, 256);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/moonbild.png"));
		this.blit(ms, this.guiLeft + 56, this.guiTop + 64, 0, 0, 256, 256, 256, 256);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/spacestation_spaceitem.png"));
		this.blit(ms, this.guiLeft + 120, this.guiTop + 36, 0, 0, 16, 16, 16, 16);
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
		if (SpaceStationGuiLogicProcedure.executeProcedure(ImmutableMap.of("entity", entity)))
			this.font.drawString(ms, "False", 116, 56, -6750208);
		if (SpaceStationIronLogicProcedure.executeProcedure(ImmutableMap.of("entity", entity)))
			this.font.drawString(ms, "True", 117, 56, -16738048);
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
		this.addButton(new Button(this.guiLeft + 102, this.guiTop + 69, 55, 20, new StringTextComponent("CREATE"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1SpaceStationMoonGuiGui.ButtonPressedMessage(0, x, y, z));
			Tier1SpaceStationMoonGuiGui.handleButtonAction(entity, 0, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 9, this.guiTop + 14, 68, 20, new StringTextComponent("Planets List"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1SpaceStationMoonGuiGui.ButtonPressedMessage(1, x, y, z));
			Tier1SpaceStationMoonGuiGui.handleButtonAction(entity, 1, x, y, z);
		}));
	}
}
