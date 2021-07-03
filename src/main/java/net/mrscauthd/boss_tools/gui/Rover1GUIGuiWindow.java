
package net.mrscauthd.boss_tools.gui;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class Rover1GUIGuiWindow extends ContainerScreen<Rover1GUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = Rover1GUIGui.guistate;
	public Rover1GUIGuiWindow(Rover1GUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 176;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/rover_1_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// Tooltip
		int fuel = (int) entity.getPersistentData().getDouble("fuel") / 160;
		List<ITextComponent> fuel2 = new ArrayList<ITextComponent>();
		if (mouseX > guiLeft + 8 && mouseX < guiLeft + 23 && mouseY > guiTop + 10 && mouseY < guiTop + 59) {
			if (fuel >= 1) {
				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			} else {
				fuel2.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Empty"));
			}
			fuel2.add(ITextComponent.getTextComponentOrEmpty(String.valueOf(fuel) + "%"));
			this.func_243308_b(ms, fuel2, mouseX, mouseY);
			//this.renderTooltip(ms, new StringTextComponent(fuel + "%"), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		// nbt
		double lavaanimation = entity.getPersistentData().getDouble("fuel");
		// lava 0
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new.png"));
		this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		// lava animation
		if (lavaanimation >= 363) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_43.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 726) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_42.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1089) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_41.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1452) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_40.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1815) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_39.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2178) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_38.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2541) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_37.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2904) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_36.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 3267) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_35.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 3630) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_34.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 3993) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_33.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 4356) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_32.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 4719) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_31.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 5082) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_30.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 5445) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_29.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 5808) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_28.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 6171) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_27.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 6534) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_26.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 6897) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_25.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 7260) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_24.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 7623) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_23.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 7986) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_22.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 8349) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_21.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 8712) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_20.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 9075) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_19.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 9438) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_18.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 9801) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_17.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 10164) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_16.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 10527) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_15.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 10890) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_14.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 11253) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_13.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 11616) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_12.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 11979) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_11.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 12342) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_10.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 12705) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_9.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 13068) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_8.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 13431) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_7.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 13794) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_6.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 14157) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_5.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 14520) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_4.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 14883) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_3.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 15246) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 15609) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_1.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 16000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_full_new2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
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
		this.font.drawString(ms, "Inventory", 7, 82, -12829636);
		this.font.drawString(ms, "Rover", 71, 5, -12829636);
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
