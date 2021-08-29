
package net.mrscauthd.boss_tools.gui;

import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock.CustomTileEntity;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class OxygenLoaderGuiGuiWindow extends ContainerScreen<OxygenLoaderGuiGui.GuiContainerMod> {
	public static final int OXYGEN_LEFT  = 76;
	public static final int OXYGEN_TOP = 40;
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public OxygenLoaderGuiGuiWindow(OxygenLoaderGuiGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 167;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/oxygen_loader_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// tooltip Energy
		if (this.getEnergyBounds().contains(mouseX, mouseY)) {
			GuiHelper.renderEnergyTooltip(ms, mouseX, mouseY, this, this.getTileEntity().getPowerSystem());
		}
	}
	
	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

	public CustomTileEntity getTileEntity() {
		return (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		GuiHelper.drawOxygen(ms, this.guiLeft + OXYGEN_LEFT, this.guiTop + OXYGEN_TOP, (double)tileEntity.getActivatingTime() / (double) tileEntity.getMaxActivatingTime());
		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getPowerSystem().getStoredRatio());
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
		this.font.drawString(ms, "Oxygen Loader", 45, 5, -13421773);
		this.font.drawString(ms, "Inventory", 6, 74, -13421773);
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
