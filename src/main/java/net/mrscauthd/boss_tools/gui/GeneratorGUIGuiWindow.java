
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock.CustomTileEntity;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class GeneratorGUIGuiWindow extends ContainerScreen<GeneratorGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public GeneratorGUIGuiWindow(GeneratorGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 166;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/generator_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// tooltip Energy
		if (mouseX > guiLeft + 143 && mouseX < guiLeft + 168 && mouseY > guiTop + 20 && mouseY < guiTop + 69)
		{
			CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
			IEnergyStorage energyStorage = tileEntity.getEnergyStorage();
			this.renderTooltip(ms, new StringTextComponent(energyStorage.getEnergyStored() + " FE / " + energyStorage.getMaxEnergyStored()+" FE"), mouseX, mouseY);
		}
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		Minecraft.getInstance().getTextureManager()
				.bindTexture(new ResourceLocation("boss_tools:textures/energy_volume_fractional_vertical_bar_background.png"));
		this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		double energyanimation = tileEntity.getEnergyStorage().getEnergyStored();
		double animation = tileEntity.getPowerSystem().getStoredPercentage();
		
		// Background
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off.png"));
		this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		// Fire Animation
		if (animation <= 108) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_on.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 14, 14, 14, 14);
		}
		if (animation <= 100) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off2.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 92) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off3.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 84) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off4.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 76) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off5.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 68) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off6.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 60) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off7.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 52) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off8.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 44) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off9.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 36) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off10.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 28) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off11.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 20) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off12.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 12) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off13.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		if (animation <= 1) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off14.png"));
			this.blit(ms, this.guiLeft + 77, this.guiTop + 51, 0, 0, 15, 14, 15, 14);
		}
		// Energy bar
		if (energyanimation >= 360) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull0.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 720) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull1.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1080) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull2.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1440) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull3.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1800) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull4.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 2160) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull5.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 2520) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull6.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3240) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull7.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3600) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull8.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3960) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull9.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 4320) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull10.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 4680) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull11.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5040) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull12.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5400) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull13.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5760) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull14.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6120) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull15.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6480) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull16.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6840) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull17.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 7200) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull18.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 7560) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull19.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 8000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull20.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 8560) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull21.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 9000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull22.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
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
		this.font.drawString(ms, "Coal Generator", 47, 5, -13421773);
		this.font.drawString(ms, "Inventory", 6, 73, -13421773);
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
