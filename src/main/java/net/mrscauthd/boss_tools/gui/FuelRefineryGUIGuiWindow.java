
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.GeneratorEngery9Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery8Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery7Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery6Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery5Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery4Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery3Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery2Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery23Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery22Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery21Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery20Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery19Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery18Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery17Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery16Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery15Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery14Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery13Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery12Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery11Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEngery10Procedure;
import net.mrscauthd.boss_tools.procedures.GeneratorEnergyGui1Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank9Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank8Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank7Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank6Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank5Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank4Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank45Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank44Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank43Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank42Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank41Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank40Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank3Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank39Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank38Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank37Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank36Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank35Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank34Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank33Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank32Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank31Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank30Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank2Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank29Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank28Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank27Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank26Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank25Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank24Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank23Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank22Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank21Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank20Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank19Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank18Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank17Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank16Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank15Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank14Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank13Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank12Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank11Procedure;
import net.mrscauthd.boss_tools.procedures.Fueltank10Procedure;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

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
		if (mouseX > guiLeft + 8 && mouseX < guiLeft + 23 && mouseY > guiTop + 10 && mouseY < guiTop + 59)
			this.renderTooltip(ms, new StringTextComponent(((new Object() {
				public double getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getDouble(tag);
					return -1;
				}
			}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "lava_gui"))) + " mb / 3000.0 mb"), mouseX, mouseY);
		// }
		// ToolTip Ende
		// toolTipStart Energy
		if (mouseX > guiLeft + 143 && mouseX < guiLeft + 168 && mouseY > guiTop + 21 && mouseY < guiTop + 70)
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
		Minecraft.getInstance().getTextureManager()
				.bindTexture(new ResourceLocation("boss_tools:textures/energy_volume_fractional_vertical_bar_background.png"));
		this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		if (GeneratorEnergyGui1Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull0.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull1.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull2.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull3.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull4.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull5.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull6.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull7.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull8.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull9.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull10.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull11.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull12.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull13.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull14.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull15.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull16.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull17.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull18.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery20Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull19.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery21Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull20.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery22Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull21.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		if (GeneratorEngery23Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/energyfull22.png"));
			this.blit(ms, this.guiLeft + 144, this.guiTop + 22, 0, 0, 24, 48, 24, 48);
		}
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new.png"));
		this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		if (Fueltank2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_43.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank3Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_42.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank4Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_41.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank5Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_40.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank6Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_39.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank7Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_38.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank8Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_37.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank9Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_36.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank10Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_35.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank11Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_34.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank12Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_33.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank13Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_32.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank14Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_31.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank15Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_30.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank16Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_29.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank17Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_28.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank18Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_27.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank19Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_26.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank20Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_25.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank21Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_24.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank22Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_23.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank23Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_22.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank24Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_21.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank25Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_20.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank26Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_19.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank27Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_18.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank28Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_17.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank29Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_16.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank30Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_15.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank31Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_14.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank32Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_13.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank33Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_12.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank34Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_11.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank35Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_10.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank36Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_9.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank37Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_8.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank38Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_7.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank39Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_6.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank40Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_5.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank41Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_4.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank42Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_3.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank43Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_2.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank44Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/fuel_refinery_fuel_new2_1.png"));
			this.blit(ms, this.guiLeft + 9, this.guiTop + 11, 0, 0, 14, 48, 14, 48);
		}
		if (Fueltank45Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world))) {
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
		this.font.drawString(ms, "Fuel Refinery", 50, 7, -13421773);
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
