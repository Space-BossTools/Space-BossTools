
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

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

@OnlyIn(Dist.CLIENT)
public class RocketGuitier2GuiWindow extends ContainerScreen<RocketGuitier2Gui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public RocketGuitier2GuiWindow(RocketGuitier2Gui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 512;
		this.ySize = 512;
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
		this.blit(ms, this.guiLeft + 128, this.guiTop + 171, 0, 0, 256, 256, 256, 256);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/overworldbild.png"));
		this.blit(ms, this.guiLeft + 140, this.guiTop + 187, 0, 0, 256, 256, 256, 256);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/moonbild.png"));
		this.blit(ms, this.guiLeft + 140, this.guiTop + 225, 0, 0, 256, 256, 256, 256);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/marsbild.png"));
		this.blit(ms, this.guiLeft + 140, this.guiTop + 263, 0, 0, 256, 256, 256, 256);
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
		this.addButton(new Button(this.guiLeft + 255, this.guiTop + 193, 36, 20, new StringTextComponent("Orbit"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(0, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 0, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 255, this.guiTop + 231, 36, 20, new StringTextComponent("Orbit"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(1, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 1, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 255, this.guiTop + 269, 36, 20, new StringTextComponent("Orbit"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(2, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 2, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 176, this.guiTop + 193, 75, 20, new StringTextComponent("Overworld"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(3, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 3, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 295, this.guiTop + 193, 76, 20, new StringTextComponent("Space Station"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(4, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 4, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 176, this.guiTop + 231, 75, 20, new StringTextComponent("Moon"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(5, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 5, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 295, this.guiTop + 231, 76, 20, new StringTextComponent("Space Station"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(6, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 6, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 176, this.guiTop + 269, 75, 20, new StringTextComponent("Mars"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(7, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 7, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + 295, this.guiTop + 269, 76, 20, new StringTextComponent("Space Station"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new RocketGuitier2Gui.ButtonPressedMessage(8, x, y, z));
			RocketGuitier2Gui.handleButtonAction(entity, 8, x, y, z);
		}));
	}
}
