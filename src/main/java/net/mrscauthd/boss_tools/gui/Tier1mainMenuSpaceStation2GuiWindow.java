
package net.mrscauthd.boss_tools.gui;

import net.mrscauthd.boss_tools.procedures.Spacestationcheck1Procedure;
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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@OnlyIn(Dist.CLIENT)
public class Tier1mainMenuSpaceStation2GuiWindow extends ContainerScreen<Tier1mainMenuSpaceStation2Gui.GuiContainerMod> {
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
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/space_station_check.png"));
		this.blit(ms, this.guiLeft + 52, this.guiTop + 247, 0, 0, 80, 22, 80, 22);
		if (Spacestationcheck1Procedure.executeProcedure(ImmutableMap.of("entity", entity))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/space_station_check_2.png"));
			this.blit(ms, this.guiLeft + 52, this.guiTop + 247, 0, 0, 80, 22, 80, 22);
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
		this.font.drawString(ms, "CATALOG", 65, 191, -1);
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
		this.addButton(new Button(this.guiLeft + 53, this.guiTop + 223, 70, 20, new StringTextComponent("Back"), e -> {
			if (true) {
				BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuSpaceStation2Gui.ButtonPressedMessage(0, x, y, z));
				Tier1mainMenuSpaceStation2Gui.handleButtonAction(entity, 0, x, y, z);
			}
		}));
		this.addButton(new Button(this.guiLeft + 53, this.guiTop + 248, 70, 20, new StringTextComponent("CREATE"), e -> {
			if (true) {
				BossToolsMod.PACKET_HANDLER.sendToServer(new Tier1mainMenuSpaceStation2Gui.ButtonPressedMessage(1, x, y, z));
				Tier1mainMenuSpaceStation2Gui.handleButtonAction(entity, 1, x, y, z);
			}
		}));
	}
}
