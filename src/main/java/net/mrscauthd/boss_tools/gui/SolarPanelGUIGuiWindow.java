
package net.mrscauthd.boss_tools.gui;

import net.minecraftforge.energy.CapabilityEnergy;
import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;
import net.mrscauthd.boss_tools.machines.SolarPanelBlock.CustomTileEntity;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class SolarPanelGUIGuiWindow extends ContainerScreen<SolarPanelGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = SolarPanelGUIGui.guistate;
	public SolarPanelGUIGuiWindow(SolarPanelGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 166;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/solar_panel_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
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
		
		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		EnergyStorageBasic energyStorage = tileEntity.getEnergyStorage();
		
		this.font.drawString(ms, "Solar Panel", 58, 5, -13421773);
		this.font.drawString(ms, "Charge: " + energyStorage.getEnergyStored() + " FE", 7, 26, -12829636);
		this.font.drawString(ms, "Capacity: " + energyStorage.getMaxEnergyStored() + " FE", 7, 38, -12829636);
		this.font.drawString(ms, "Generation: " + tileEntity.getGeneratePerTick() + " FE/t" , 7, 50, -12829636);
		
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
