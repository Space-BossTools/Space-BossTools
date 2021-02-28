
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.GeneratorEngery9Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery8Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery7Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery6Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery5Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery4Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery3Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery2Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery23Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery22Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery21Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery20Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery19Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery18Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery17Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery16Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery15Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery14Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery13Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery12Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery11Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery10Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEnergyGui1Procedure;
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

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.concurrent.atomic.AtomicInteger;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@OnlyIn(Dist.CLIENT)
public class CompressorGuiGuiWindow extends ContainerScreen<CompressorGuiGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public CompressorGuiGuiWindow(CompressorGuiGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 177;
		this.ySize = 172;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/compressor_gui.png");
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/outputslot.png"));
		this.blit(ms, this.guiLeft + 87, this.guiTop + 33, 0, 0, 26, 26, 26, 26);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyoutputslot.png"));
		this.blit(ms, this.guiLeft + 138, this.guiTop + 58, 0, 0, 32, 21, 32, 21);
		Minecraft.getInstance().getTextureManager()
				.bindTexture(new ResourceLocation("boss_tools:textures/energy_volume_fractional_vertical_bar_background.png"));
		this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		if (GeneratorEnergyGui1Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull0.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull1.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull2.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull3.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull4.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull5.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull6.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull7.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull8.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull9.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull10.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull11.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull12.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull13.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull14.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull15.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull16.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull17.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull18.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery20Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull19.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery21Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull20.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery22Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull21.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery23Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull22.png"));
			this.blit(ms, this.guiLeft + 141, this.guiTop + 11, 0, 0, 24, 48, 24, 48);
		}
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
		this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		if (BlastFurnaceArrow1Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_2.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_3.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_4.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_5.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_6.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_7.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_8.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_9.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_10.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_11.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_12.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_13.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_14.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_15.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_16.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_17.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_18.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_19.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow20Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_20.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow21Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_21.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow22Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_22.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
		}
		if (BlastFurnaceArrow23Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_23.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
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
		this.font.drawString(ms, "Compressor", 7, 6, -13421773);
		this.font.drawString(ms, "Inventory", 7, 76, -13421773);
		this.font.drawString(ms, "" + (new Object() {
			public int getEnergyStored(BlockPos pos) {
				AtomicInteger _retval = new AtomicInteger(0);
				TileEntity _ent = world.getTileEntity(pos);
				if (_ent != null)
					_ent.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
				return _retval.get();
			}
		}.getEnergyStored(new BlockPos((int) x, (int) y, (int) z))) + "", 141, 65, -12829636);
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
