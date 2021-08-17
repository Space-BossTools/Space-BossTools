
package net.mrscauthd.boss_tools.gui;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class Tier1mainMenuGuiWindow extends ContainerScreen<Tier1mainMenuGui.GuiContainerMod> {
	public static boolean Button1 = false;
	public static boolean Button2 = false;
	public static boolean Button3 = false;
	public static boolean Button4 = false;
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = Tier1mainMenuGui.guistate;
	public Tier1mainMenuGuiWindow(Tier1mainMenuGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 512;
		this.ySize = 512;
	}
	private static ResourceLocation GreenButton1T = new ResourceLocation("boss_tools:textures/buttons/green_button.png");
	private static ResourceLocation GreenButton2T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
	private static ResourceLocation GreenButton3T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
	private static ResourceLocation GreenButton4T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		//lists
		List<ITextComponent> Category1 = new ArrayList<ITextComponent>();
		List<ITextComponent> Category2 = new ArrayList<ITextComponent>();
		List<ITextComponent> Category3 = new ArrayList<ITextComponent>();
		List<ITextComponent> Category4 = new ArrayList<ITextComponent>();

		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 222 && mouseY < guiTop + 243) {
			Button1 = true;
			//ToolTip
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Category: \u00A7aOverworld"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Provided: \u00A7bTier 1 Rocket"));
			this.func_243308_b(ms, Category1, mouseX, mouseY);
		} else {
			Button1 = false;
		}
		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 246 && mouseY < guiTop + 267) {
			Button2 = true;

			//ToolTip
			Category2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Category: \u00A7cMars"));
			Category2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Provided: \u00A7bTier 2 Rocket"));
			this.func_243308_b(ms, Category2, mouseX, mouseY);
		} else {
			Button2 = false;
		}
		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 270 && mouseY < guiTop + 291) {
			Button3 = true;
			//ToolTip
			Category3.add(ITextComponent.getTextComponentOrEmpty("\u00A79Category: \u00A7cMercury"));
			Category3.add(ITextComponent.getTextComponentOrEmpty("\u00A79Provided: \u00A7bTier 3 Rocket"));
			this.func_243308_b(ms, Category3, mouseX, mouseY);
		} else {
			Button3 = false;
		}
		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 294 && mouseY < guiTop + 315) {
			Button4 = true;
			//ToolTip
			Category4.add(ITextComponent.getTextComponentOrEmpty("\u00A79Category: \u00A7cVenus"));
			Category4.add(ITextComponent.getTextComponentOrEmpty("\u00A79Provided: \u00A7bTier 3 Rocket"));
			this.func_243308_b(ms, Category4, mouseX, mouseY);
		} else {
			Button4 = false;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		int Width = Minecraft.getInstance().getMainWindow().getScaledWidth();
		int Heigh = Minecraft.getInstance().getMainWindow().getScaledHeight();
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rocket_menu_background.png"));
		this.blit(ms, 0, 0, 0, 0, Width, Heigh, Width, Heigh);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rocket_menu_list.png"));
		this.blit(ms, this.guiLeft + 43, this.guiTop + 174, 0, 0, 160, 160, 160, 160);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/milky_way.png"));
		this.blit(ms, this.guiLeft + 206, this.guiTop + 209, 0, 0, 175, 101, 175, 101);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/sun_main_menu.png"));
		this.blit(ms, this.guiLeft + 290, this.guiTop + 255, 0, 0, 8, 8, 8, 8);
		// RenderSystem.rotatef(10,10,10,10);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/earth_main_menu.png"));
		this.blit(ms, this.guiLeft + 251, this.guiTop + 244, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mars_main_menu.png"));
		this.blit(ms, this.guiLeft + 327, this.guiTop + 225, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mercury.png"));
		this.blit(ms, this.guiLeft + 305, this.guiTop + 264, 0, 0, 8, 8, 8, 8);

		if (Button1 == true) {
			GreenButton1T = new ResourceLocation("boss_tools:textures/buttons/green_button_2.png");
		} else {
			GreenButton1T = new ResourceLocation("boss_tools:textures/buttons/green_button.png");
		}

		if (Button2 == true) {
			GreenButton2T = new ResourceLocation("boss_tools:textures/buttons/red_button_2.png");
		} else {
			GreenButton2T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
		}

		if (Button3 == true) {
			GreenButton3T = new ResourceLocation("boss_tools:textures/buttons/red_button_2.png");
		} else {
			GreenButton3T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
		}

		if (Button4 == true) {
			GreenButton4T = new ResourceLocation("boss_tools:textures/buttons/red_button_2.png");
		} else {
			GreenButton4T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
		}

		//Button 1
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 223, 70, 20, 0, 0, 0, GreenButton1T, 70, 20, (p_2130901) -> {
					BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuGui.ButtonPressedMessage(0, x, y, z));
					Tier1mainMenuGui.handleButtonAction(entity, 0, x, y, z);
				}));
		//Button 2
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 247, 70, 20, 0, 0, 0, GreenButton2T, 70, 20, (p_2130901) -> {
					BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuGui.ButtonPressedMessage(1, x, y, z));
					Tier1mainMenuGui.handleButtonAction(entity, 1, x, y, z);
				}));
		//Button 3
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 271, 70, 20, 0, 0, 0, GreenButton3T, 70, 20, (p_2130901) -> {
					BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuGui.ButtonPressedMessage(2, x, y, z));
					Tier1mainMenuGui.handleButtonAction(entity, 2, x, y, z);
				}));
		//Button 4
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 295, 70, 20, 0, 0, 0, GreenButton4T, 70, 20, (p_2130901) -> {
					BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuGui.ButtonPressedMessage(3, x, y, z));
					Tier1mainMenuGui.handleButtonAction(entity, 3, x, y, z);
				}));
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
		this.font.drawString(ms, "CATALOG", 65, 191, -1);
		this.font.drawString(ms, "Overworld", 62, 229, -1);
		this.font.drawString(ms, "   Mars", 63, 253, -1);
		this.font.drawString(ms, " Mercury", 63, 277, -1);
		this.font.drawString(ms, "  Venus", 64, 301, -1);
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