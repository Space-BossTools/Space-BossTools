
package net.mrscauthd.boss_tools.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock.CustomTileEntity;

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
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off.png"));
		this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		double fireanimation = tileEntity.getEnergyStorage().getEnergyStoredPercentage();
		double arrowanimation = tileEntity.getTimerPercentage() * 2;
		
		if (fireanimation <= 100) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_on.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 14, 14, 14, 14);
		}
		if (fireanimation <= 92.31) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off2.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 84.62) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off3.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 76.93) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off4.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 69.24) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off5.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 61.55) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off6.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 53.86) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off7.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 46.17) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off8.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 38.48) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off9.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 30.79) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off10.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 23.1) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off11.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 15.41) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off12.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 7.72) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off13.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		if (fireanimation <= 0) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fire_off14.png"));
			this.blit(ms, this.guiLeft + 53, this.guiTop + 39, 0, 0, 15, 14, 15, 14);
		}
		//arrow 0
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/outputslot.png"));
		this.blit(ms, this.guiLeft + 99, this.guiTop + 33, 0, 0, 26, 26, 26, 26);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
		this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		//arrow animation
		if (arrowanimation >= 8.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 17) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_2.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 25.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_3.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 34) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_4.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 42.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_5.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 51) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_6.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 59.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_7.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 68) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_8.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 76.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_9.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 85) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_10.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 93.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_11.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 102) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_12.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 110.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_13.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 119) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_14.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 127.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_15.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 136) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_16.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 144.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_17.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 153) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_18.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 161.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_19.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 170) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_20.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 178.5) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_21.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 187) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_22.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
		if (arrowanimation >= 200) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_23.png"));
			this.blit(ms, this.guiLeft + 73, this.guiTop + 37, 0, 0, 22, 16, 22, 16);
		}
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
