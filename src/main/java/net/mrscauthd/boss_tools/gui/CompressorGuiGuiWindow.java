
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation0.png"));
		this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		if (BlastFurnaceGuiArrowProcedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation1.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation2.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation3.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation4.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation5.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation6.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation7.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation8.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation9.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation10.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation11.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation12.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation13.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation14.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation15.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation16.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation17.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation18.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		if (BlastFurnaceGuiArrow19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/arrowanimation19.png"));
			this.blit(ms, this.guiLeft + 61, this.guiTop + 39, 0, 0, 20, 14, 20, 14);
		}
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/outputslot.png"));
		this.blit(ms, this.guiLeft + 86, this.guiTop + 33, 0, 0, 26, 26, 26, 26);
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
