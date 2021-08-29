package net.mrscauthd.boss_tools.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class BlastFurnaceGUIGuiWindow extends ContainerScreen<BlastFurnaceGUIGui.GuiContainerMod> {
	
	public static final int FIRE_LEFT = 53;
	public static final int FIRE_TOP = 39;
	public static final int ARROW_LEFT = 74;
	public static final int AROOW_TOP = 37;
	
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
		
		if (this.getFireBounds().contains(mouseX, mouseY)) {
			CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
			this.renderTooltip(ms, new StringTextComponent("Fuel : " + tileEntity.getPowerSystem().getStored()), mouseX, mouseY);
		}
		
	}

	public Rectangle2d getFireBounds() {
		return GuiHelper.getFireBounds(this.guiLeft + FIRE_LEFT, this.guiTop + FIRE_TOP);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		AbstractGui.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		GuiHelper.drawFire(ms, this.guiLeft + FIRE_LEFT, this.guiTop + FIRE_TOP, tileEntity.getPowerSystem().getStoredRatio());
		GuiHelper.drawArrow(ms, this.guiLeft + ARROW_LEFT, this.guiTop + AROOW_TOP, tileEntity.getTimerRatio());
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
