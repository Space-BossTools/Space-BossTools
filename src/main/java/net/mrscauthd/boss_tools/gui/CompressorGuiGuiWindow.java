
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.CompressorBlock.CustomTileEntity;

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
        // tooltip Energy
        if (mouseX > guiLeft + 143 && mouseX < guiLeft + 168 && mouseY > guiTop + 20 && mouseY < guiTop + 69)
            this.renderTooltip(ms, new StringTextComponent(((new Object() {
                public double getValue(IWorld world, BlockPos pos, String tag) {
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity != null)
                        return tileEntity.getTileData().getDouble(tag);
                    return -1;
                }
            }.getValue(world, new BlockPos((int) x, (int) y, (int) z), "energy_fe_gui"))) + " FE / 9000.0 FE"), mouseX, mouseY);
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
        

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
		double energyanimation = tileEntity.getEnergyStorage().getEnergyStored();
		double arrowanimation = tileEntity.getTimerPercentage() * 2;
		
        // energy 0
        Minecraft.getInstance().getTextureManager()
                .bindTexture(new ResourceLocation("boss_tools:textures/energy_volume_fractional_vertical_bar_background.png"));
        this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
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

        //Arrow
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
        this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        //arrow animation
        if (arrowanimation >= 8.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_1.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 17) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_2.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 25.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_3.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 34) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_4.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 42.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_5.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 51) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_6.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 59.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_7.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 68) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_8.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 76.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_9.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 85) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_10.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 93.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_11.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 102) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_12.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 110.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_13.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 119) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_14.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 127.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_15.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 136) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_16.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 144.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_17.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 153) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_18.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 161.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_19.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 170) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_20.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 178.5) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_21.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 187) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/animated_arrow_22.png"));
            this.blit(ms, this.guiLeft + 61, this.guiTop + 38, 0, 0, 22, 16, 22, 16);
        }
        if (arrowanimation >= 200) {
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