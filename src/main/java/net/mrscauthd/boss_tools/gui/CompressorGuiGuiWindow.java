package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.machines.CompressorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class CompressorGuiGuiWindow extends ContainerScreen<CompressorGuiGui.GuiContainerMod> {
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 36;
	
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
        this.ySize = 168;
    }
    private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/compressor_gui.png");
    @Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// tooltip Energy
		if (this.getEnergyBounds().contains(mouseX, mouseY)) {
			CustomTileEntity tileEntity = (CustomTileEntity) world.getTileEntity(new BlockPos(this.x, this.y, this.z));
			GuiHelper.renderEnergyTooltip(ms, mouseX, mouseY, this, tileEntity.getPowerSystem());
		}
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getPowerSystem().getStoredRatio());
		GuiHelper.drawArrow(ms, this.guiLeft + ARROW_LEFT, this.guiTop + ARROW_TOP, tileEntity.getTimerRatio());
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