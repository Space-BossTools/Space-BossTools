
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.boss_tools.block.FuelMakerBlock.CustomTileEntity;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.Minecraft;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class FuelRefineryGUIGuiWindow extends ContainerScreen<FuelRefineryGUIGui.GuiContainerMod> {
	private World world;
	private BlockPos blockPos;
	private PlayerEntity entity;

	public FuelRefineryGUIGuiWindow(FuelRefineryGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.blockPos = new BlockPos(container.x, container.y, container.z);
		this.entity = container.entity;
		this.xSize = 177;
		this.ySize = 172;
	}

	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/fuel_refinery_gui.png");

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(this.blockPos);
		EnergyStorage energyStorage = tileEntity.getEnergyStorage();
		FluidTank fluidTank = tileEntity.getFluidTank();
		int fuel = fluidTank.getFluidAmount();

		if (mouseX > guiLeft + 8 && mouseX < guiLeft + 23 && mouseY > guiTop + 10 && mouseY < guiTop + 59) {
			List<ITextComponent> lava = new ArrayList<ITextComponent>();
			if (fuel > 0) {
				lava.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			} else {
				lava.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Empty"));
			}
			lava.add(ITextComponent.getTextComponentOrEmpty(fuel + " mb / " + fluidTank.getCapacity() + " mb"));
			this.func_243308_b(ms, lava, mouseX, mouseY);
		}
		// }
		// ToolTip Ende
		// toolTipStart Energy
		if (mouseX > guiLeft + 143 && mouseX < guiLeft + 168 && mouseY > guiTop + 20 && mouseY < guiTop + 69) {
			this.renderTooltip(ms, new StringTextComponent(energyStorage.getEnergyStored() + " FE / " + energyStorage.getMaxEnergyStored() + " FE"), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		textureManager.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		// Energy NBT

		CustomTileEntity tileEntity = (CustomTileEntity) this.world.getTileEntity(this.blockPos);
		int energyanimation = tileEntity.getEnergyStorage().getEnergyStored();

		// Fuel Tank
		int lavaanimation = tileEntity.getFluidTank().getFluidAmount();

		// energy 0
		textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energy_volume_fractional_vertical_bar_background.png"));
		this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		// Energy bar
		if (energyanimation >= 360) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull0.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 720) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull1.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1080) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull2.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1440) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull3.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 1800) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull4.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 2160) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull5.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 2520) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull6.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3240) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull7.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3600) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull8.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 3960) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull9.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 4320) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull10.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 4680) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull11.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5040) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull12.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5400) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull13.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 5760) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull14.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6120) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull15.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6480) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull16.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 6840) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull17.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 7200) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull18.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 7560) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull19.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 8000) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull20.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 8560) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull21.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		if (energyanimation >= 9000) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/energyfull22.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 21, 0, 0, 24, 48, 24, 48);
		}
		// lava 0
		textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new.png"));
		this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		// lava animation
		if (lavaanimation >= 120) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_43.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 180) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_42.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 240) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_41.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 300) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_40.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 360) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_39.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 420) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_38.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 480) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_37.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 540) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_36.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 600) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_35.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 660) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_34.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 720) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_33.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 780) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_32.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 840) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_31.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 900) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_30.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1000) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_29.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1070) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_28.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1140) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_27.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1210) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_26.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1280) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_25.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1350) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_24.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1420) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_23.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1490) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_22.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1560) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_21.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1630) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_20.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1700) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_19.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1770) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_18.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1840) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_17.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1910) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_16.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2000) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_15.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2060) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_14.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2120) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_13.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2180) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_12.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2240) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_11.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2300) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_10.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2360) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_9.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2420) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_8.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2480) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_7.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2540) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_6.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2600) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_5.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2660) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_4.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2750) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_3.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2810) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2900) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_1.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 3000) {
			textureManager.bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_full_new2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
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
		this.font.drawString(ms, "Fuel Refinery", 54, 7, -13421773);
		this.font.drawString(ms, "Inventory", 7, 79, -13421773);
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
