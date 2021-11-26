package net.mrscauthd.boss_tools.gui.screens.planetselection;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.helper.ImageButtonPlacer;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlanetSelectionGuiWindow extends ContainerScreen<PlanetSelectionGui.GuiContainer> {

	private static ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId,"textures/screens/planet_selection_gui.png");

	private static ResourceLocation defaultButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button.png");
	private static ResourceLocation gbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/green_button.png");
	private static ResourceLocation gb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/green_button_2.png");
	private static ResourceLocation rbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button.png");
	private static ResourceLocation rb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button_2.png");

	public float rotationMars = 0;
	public float rotationEarth = 90;
	public float rotationVenus = 180;
	public float rotationMercury = 270;

	public ImageButtonPlacer overworldButton;
	public ImageButtonPlacer marsButton;

	public PlanetSelectionGuiWindow(PlanetSelectionGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 512;
		this.ySize = 512;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		rotationMars = (rotationMars + partialTicks * 0.6f) % 360;
		rotationEarth = (rotationEarth + partialTicks * 1.2f) % 360;
		rotationVenus = (rotationVenus + partialTicks * 1.1f) % 360;
		rotationMercury = (rotationMercury + partialTicks * 0.9f) % 360;

		String rocketType = container.rocket;

		this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 60 / 2, 70, 20, ms, overworldButton, "Overworld", "Tier 1 Rocket", 1);

		this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 16 / 2, 70, 20, ms, marsButton, "Mars", "Tier 2 Rocket", 2);

		//RENDER FONTS
		this.font.drawString(ms, "CATALOG", 21, (this.height / 2) - 126 / 2, -1);
		this.font.drawString(ms, "Overworld", 19, (this.height / 2) - 51 / 2, -1);
		this.font.drawString(ms, "Mars", 33, (this.height / 2) - 6 / 2, -1);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		this.blit(ms, 0, 0, 0, 0, this.width, this.height, this.width, this.height);

		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId,"textures/rocket_menu_list.png"));
		this.blit(ms, 0, (this.height / 2) - 160 / 2, 0, 0, 160, 160, 160, 160);

		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId,"textures/milky_way.png"));
		GuiHelper.blit(ms, (this.width - 185) / 2, (this.height - 185) / 2, 0, 0, 185, 185, 185, 185);

		//SUN
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/sun.png"));
		GuiHelper.blit(ms, (this.width - 15) / 2, (this.height - 15) / 2, 0, 0, 15, 15, 15, 15);

		//PLANETS
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/mars.png"), -70, -70, 10, 10, rotationMars);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/earth.png"), -54, -54, 10, 10, rotationEarth);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/venus.png"), -37, -37, 10, 10, rotationVenus);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/mercury.png"), -20.5F, -20.5F, 10, 10, rotationMercury);

		RenderSystem.disableBlend();
	}

	public void addPlanet(MatrixStack ms, ResourceLocation planet, float x, float y, int width, int height, float rotation) {
		ms.push();

		ms.translate(this.width / 2, this.height / 2, 0);
		ms.rotate(new Quaternion(Vector3f.ZP, rotation, true));

		Minecraft.getInstance().getTextureManager().bindTexture(planet);
		GuiHelper.blit(ms, x, y, 0, 0, width, height, width, height);

		ms.translate(-this.width / 2, -this.height / 2, 0);
		ms.pop();
	}

	@Override
	protected void init() {
		super.init();

		overworldButton = this.addButton(new ImageButtonPlacer(10, (this.height / 2) - 60 / 2, 70, 20, 0, 0, 0, defaultButtonTex, 70, 20, (p_2130901) -> {
			if (checkRocket(container.rocket, 1)) {
				BossToolsMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(playerInventory.player.getPosition(), 0));
			}
		}));

		marsButton = this.addButton(new ImageButtonPlacer(10, (this.height / 2) - 16 / 2, 70, 20, 0, 0, 0, defaultButtonTex, 70, 20, (p_2130901) -> {
			if (checkRocket(container.rocket, 2)) {
				BossToolsMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(playerInventory.player.getPosition(), 1));
			}
		}));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
	}

	public Rectangle2d getBounds(int left, int top, int width, int height) {
		return GuiHelper.getBounds(left, top, width, height);
	}

	public static boolean checkRocket(String rocketType, int stage) {
		if (stage == 1 && rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t1") || rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t2") || rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t3")) {
			return true;
		}
		if (stage == 2 && rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t2") || rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t3")) {
			return true;
		}
		if (stage == 3 && rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t3")) {
			return true;
		}

		return false;
	}

	public void buttonManager(String rocketType, ResourceLocation gb2, ResourceLocation rb2, ResourceLocation gb, ResourceLocation rb, int mouseX, int mouseY, int left, int top, int width, int height, MatrixStack ms, ImageButtonPlacer button, String dim, String rocketTier, int stage) {
		String level = "c";

		if (checkRocket(rocketType, stage)) {
			level = "a";
			button.setTexture(gb2);
		} else {
			level = "c";
			button.setTexture(rb2);
		}

		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {

			//ToolTip
			List<ITextComponent> list = new ArrayList<ITextComponent>();
			list.add(ITextComponent.getTextComponentOrEmpty("\u00A79Category: " + "\u00A7" + level + dim));
			list.add(ITextComponent.getTextComponentOrEmpty("\u00A79Provided: \u00A7b" + rocketTier));
			this.func_243308_b(ms, list, mouseX, mouseY);

		} else {
			if (checkRocket(rocketType, stage)) {
				button.setTexture(gb);
			} else {
				button.setTexture(rb);
			}
		}
	}
}
