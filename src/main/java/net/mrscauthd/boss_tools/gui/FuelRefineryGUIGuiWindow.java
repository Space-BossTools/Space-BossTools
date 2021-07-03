
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.api.distmarker.OnlyIn;
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
import net.minecraft.client.Minecraft;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class FuelRefineryGUIGuiWindow extends ContainerScreen<FuelRefineryGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public FuelRefineryGUIGuiWindow(FuelRefineryGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
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
		// Tooltip
		double fuel = (((new Object() {
			public double getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getDouble(tag);
				return -1;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "lava_gui"))));
		if (mouseX > guiLeft + 8 && mouseX < guiLeft + 23 && mouseY > guiTop + 10 && mouseY < guiTop + 59) {
			List<ITextComponent> lava = new ArrayList<ITextComponent>();
			if (fuel >= 1) {
				lava.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			} else {
				lava.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Empty"));
			}
			lava.add(ITextComponent.getTextComponentOrEmpty(String.valueOf(fuel) + " mb / 3000.0 mb"));
			this.func_243308_b(ms, lava, mouseX, mouseY);
		}
		// }
		// ToolTip Ende
		// toolTipStart Energy
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
		// Energy NBT
		double energyanimation = (double) (new Object() {
			public double getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getDouble(tag);
				return -1;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "EnergyGui"));
		// Fuel Tank
		double lavaanimation = ((new Object() {
			public int getFluidTankLevel(BlockPos pos, int tank) {
				AtomicInteger _retval = new AtomicInteger(0);
				TileEntity _ent = world.getTileEntity(pos);
				if (_ent != null)
					_ent.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)
							.ifPresent(capability -> _retval.set(capability.getFluidInTank(tank).getAmount()));
				return _retval.get();
			}
		}.getFluidTankLevel(new BlockPos((int) x, (int) y, (int) z), (int) 1)));
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
		// lava 0
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new.png"));
		this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		// lava animation
		if (lavaanimation >= 120) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_43.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 180) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_42.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 240) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_41.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 300) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_40.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 360) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_39.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 420) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_38.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 480) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_37.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 540) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_36.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 600) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_35.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 660) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_34.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 720) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_33.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 780) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_32.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 840) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_31.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 900) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_30.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_29.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1070) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_28.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1140) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_27.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1210) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_26.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1280) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_25.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1350) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_24.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1420) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_23.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1490) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_22.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1560) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_21.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1630) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_20.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1700) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_19.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1770) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_18.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1840) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_17.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 1910) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_16.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_15.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2060) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_14.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2120) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_13.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2180) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_12.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2240) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_11.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2300) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_10.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2360) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_9.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2420) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_8.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2480) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_7.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2540) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_6.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2600) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_5.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2660) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_4.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2750) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_3.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2810) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 2900) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_1.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (lavaanimation >= 3000) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_full_new2.png"));
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
