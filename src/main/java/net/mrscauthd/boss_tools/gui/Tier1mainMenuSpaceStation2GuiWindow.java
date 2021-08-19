
package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.item.OrbitPlacerItem;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Tier1mainMenuSpaceStation2GuiWindow extends ContainerScreen<Tier1mainMenuSpaceStation2Gui.GuiContainerMod> {
	public static boolean Button1 = false;
	public static boolean Button2 = false;
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public Tier1mainMenuSpaceStation2GuiWindow(Tier1mainMenuSpaceStation2Gui.GuiContainerMod container, PlayerInventory inventory,
			ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 512;
		this.ySize = 512;
	}
	private static ResourceLocation Button1T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
	//back button
	private static ResourceLocation Button2T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button.png");

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		//lists
		List<ITextComponent> Category1 = new ArrayList<ITextComponent>();

		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 246 && mouseY < guiTop + 267) {
			Button1 = true;

			//ToolTip
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Item Requirement:"));
			if (((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(OrbitPlacerItem.block, (int) (1))) : false) == true) {
				Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A7aSpace Station"));
			} else {
				Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A7cSpace Station"));
			}
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A7c-------------"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Type: \u00A73Orbit"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Gravity: \u00A73No Gravity"));
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Oxygen: \u00A7cfalse")); //false c / true a
			Category1.add(ITextComponent.getTextComponentOrEmpty("\u00A79Temperature: \u00A73-270")); //hot c / cold 3
			this.func_243308_b(ms, Category1, mouseX, mouseY);
		} else {
			Button1 = false;
		}

		//back Button
		if (mouseX > guiLeft + 52 && mouseX < guiLeft + 123 && mouseY > guiTop + 222 && mouseY < guiTop + 243) {
			Button2 = true;
		} else {
			Button2 = false;
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
		this.blit(ms, this.guiLeft + 168, this.guiTop + 223, 0, 0, 175, 101, 175, 101);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/sun_main_menu.png"));
		this.blit(ms, this.guiLeft + 252, this.guiTop + 269, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/earth_main_menu.png"));
		this.blit(ms, this.guiLeft + 210, this.guiTop + 265, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mars_main_menu.png"));
		this.blit(ms, this.guiLeft + 287, this.guiTop + 238, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/mercury.png"));
		this.blit(ms, this.guiLeft + 267, this.guiTop + 278, 0, 0, 8, 8, 8, 8);
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/rocket_menu_list.png"));
		this.blit(ms, this.guiLeft + 43, this.guiTop + 174, 0, 0, 160, 160, 160, 160);
		if (Button1 == true) {
			if (((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(OrbitPlacerItem.block, (int) (1))) : false) == true) {
				Button1T = new ResourceLocation("boss_tools:textures/buttons/green_button_2.png");
			} else {
				Button1T = new ResourceLocation("boss_tools:textures/buttons/red_button_2.png");
			}
		} else {
			if (((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(OrbitPlacerItem.block, (int) (1))) : false) == true) {
				Button1T = new ResourceLocation("boss_tools:textures/buttons/green_button.png");
			} else {
				Button1T = new ResourceLocation("boss_tools:textures/buttons/red_button.png");
			}
		}

		//back button
		if (Button2 == true) {
			Button2T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button_2.png");
		} else {
			Button2T = new ResourceLocation("boss_tools:textures/buttons/dark_blue_button.png");
		}
		//Create Button
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 247, 70, 20, 0, 0, 0, Button1T, 70, 20, (p_2130901) -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuSpaceStation2Gui.ButtonPressedMessage(1, x, y, z));
			Tier1mainMenuSpaceStation2Gui.handleButtonAction(entity, 1, x, y, z);
		}));

		//Back Button
		this.addButton(new ImageButton(this.guiLeft + 53, this.guiTop + 223, 70, 20, 0, 0, 0, Button2T, 70, 20, (p_2130901) -> {
				BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuSpaceStation2Gui.ButtonPressedMessage(0, x, y, z));
				Tier1mainMenuSpaceStation2Gui.handleButtonAction(entity, 0, x, y, z);
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
		this.font.drawString(ms, "    Back", 60, 229, -1);
		this.font.drawString(ms, "  CREATE", 62, 253, -1);
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
