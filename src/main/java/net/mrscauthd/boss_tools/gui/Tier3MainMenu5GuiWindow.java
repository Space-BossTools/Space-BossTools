
package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.widget.button.ImageButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class Tier3MainMenu5GuiWindow extends ContainerScreen<Tier3MainMenu5Gui.GuiContainerMod> {
	public static boolean Button1 = false;
	public static boolean Button2 = false;
	public static boolean Button3 = false;
	//back Button
	public static boolean Button7 = false;
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = Tier3MainMenu5Gui.guistate;
	public Tier3MainMenu5GuiWindow(Tier3MainMenu5Gui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 512;
		this.ySize = 512;
	}
	private static ResourceLocation Button1T = new ResourceLocation("boss_tools:textures/buttons/blue_button.png");
	private static ResourceLocation Button2T = new ResourceLocation("boss_tools:textures/buttons/small_blue_button.png");
	private static ResourceLocation Button3T = new ResourceLocation("boss_tools:textures/buttons/big_blue_button.png");
	//back button
	private static ResourceLocation Button7T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button.png");


	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		//lists
		List<ITextComponent> Category1 = new ArrayList<ITextComponent>();
		List<ITextComponent> Category2 = new ArrayList<ITextComponent>();
		List<ITextComponent> Category3 = new ArrayList<ITextComponent>();
		//	List<ITextComponent> Category7 = new ArrayList<ITextComponent>();

		if (mouseX > guiLeft + 50 && mouseX < guiLeft + 121 && mouseY > guiTop + 245 && mouseY < guiTop + 266) {
			Button1 = true;

			//ToolTip
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Type: \u00A73Planet"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Gravity: \u00A738.87 m/s"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Oxygen: \u00A7cfalse")); //false c / true a
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Temperature: \u00A7c462")); //hot c / cold 3
			this.func_243308_b(ms, Category1, mouseX, mouseY);
		} else {
			Button1 = false;
		}

		//back Button
		if (mouseX > guiLeft + 50 && mouseX < guiLeft + 121 && mouseY > guiTop + 222 && mouseY < guiTop + 243) {
			Button7 = true;

			//ToolTip
			//Category7.add(ITextComponent.getTextComponentOrEmpty("\u00A79Main Menu"));
			//this.func_243308_b(ms, Category7, mouseX, mouseY);
		} else {
			Button7 = false;
		}
		//Orbit Venus
		if (mouseX > guiLeft + 125 && mouseX < guiLeft + 163 && mouseY > guiTop + 245 && mouseY < guiTop + 266) {
			Button2 = true;

			//ToolTip
			Category2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fly to your \u00A78Space Station"));
			this.func_243308_b(ms, Category2, mouseX, mouseY);
		} else {
			Button2 = false;
		}

		//Space Station Venus
		if (mouseX > guiLeft + 167 && mouseX < guiLeft + 243 && mouseY > guiTop + 245 && mouseY < guiTop + 266) {
			Button3 = true;

			//ToolTip
			Category3.add(ITextComponent.getTextComponentOrEmpty("\u00A79Create your \u00A78Space Station"));
			this.func_243308_b(ms, Category3, mouseX, mouseY);
		} else {
			Button3 = false;
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/milky_way.png"));
		this.blit(ms, this.guiLeft + 278, this.guiTop + 223, 0, 0, 175, 101, 175, 101);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/sun_main_menu.png"));
		this.blit(ms, this.guiLeft + 362, this.guiTop + 270, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/earth_main_menu.png"));
		this.blit(ms, this.guiLeft + 328, this.guiTop + 254, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mars_main_menu.png"));
		this.blit(ms, this.guiLeft + 398, this.guiTop + 239, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mercury.png"));
		this.blit(ms, this.guiLeft + 377, this.guiTop + 278, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rocket_menu_list2.png"));
		this.blit(ms, this.guiLeft + 43, this.guiTop + 174, 0, 0, 260, 160, 260, 160);


		if (Button7 == true) {
			Button7T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button_2.png");
		} else {
			Button7T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button.png");
		}

		if (Button1 == true) {
			Button1T = new ResourceLocation("boss_tools:textures/buttons/blue_button_2.png");
		} else {
			Button1T = new ResourceLocation("boss_tools:textures/buttons/blue_button.png");
		}

		if (Button2 == true) {
			Button2T = new ResourceLocation("boss_tools:textures/buttons/small_blue_button_2.png");
		} else {
			Button2T = new ResourceLocation("boss_tools:textures/buttons/small_blue_button.png");
		}

		if (Button3 == true) {
			Button3T = new ResourceLocation("boss_tools:textures/buttons/big_blue_button_2.png");
		} else {
			Button3T = new ResourceLocation("boss_tools:textures/buttons/big_blue_button.png");
		}


		//Back Button
		this.addButton(new ImageButton(this.guiLeft + 51, this.guiTop + 223, 70, 20, 0,0,0, Button7T, 70 , 20, e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier3MainMenu5Gui.ButtonPressedMessage(0, x, y, z));
			Tier3MainMenu5Gui.handleButtonAction(entity, 0, x, y, z);
		}));

		//Venus Button
		this.addButton(new ImageButton(this.guiLeft + 51, this.guiTop + 246, 70, 20, 0,0,0,Button1T,70,20, e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier3MainMenu5Gui.ButtonPressedMessage(1, x, y, z));
			Tier3MainMenu5Gui.handleButtonAction(entity, 1, x, y, z);
		}));

		//Venus Orbit Button
		this.addButton(new ImageButton(this.guiLeft + 126, this.guiTop + 246, 37, 20, 0,0,0, Button2T,37,20, e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier3MainMenu5Gui.ButtonPressedMessage(2, x, y, z));
			Tier3MainMenu5Gui.handleButtonAction(entity, 2, x, y, z);
		}));

		//Venus Space Station Button
		this.addButton(new ImageButton(this.guiLeft + 168, this.guiTop + 246, 75, 20, 0,0,0, Button3T,75,20, e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier3MainMenu5Gui.ButtonPressedMessage(3, x, y, z));
			Tier3MainMenu5Gui.handleButtonAction(entity, 3, x, y, z);
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
		this.font.drawString(ms, "   Back", 62, 229, -1);
		//Venus
		this.font.drawString(ms, "  Venus", 62, 252, -1);
		this.font.drawString(ms, "  Orbit", 124, 252, -1);
		this.font.drawString(ms, "Space Station", 171.5f, 252, -1);
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
