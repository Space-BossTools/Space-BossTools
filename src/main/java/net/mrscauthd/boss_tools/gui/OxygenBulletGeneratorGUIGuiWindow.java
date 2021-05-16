
package net.mrscauthd.boss_tools.gui;

import org.lwjgl.opengl.GL11;

import net.mrscauthd.boss_tools.procedures.Oxygenlargeshow2Procedure;
import net.mrscauthd.boss_tools.procedures.Oxygenlargeshow1Procedure;
import net.mrscauthd.boss_tools.BossToolsMod;

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
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@OnlyIn(Dist.CLIENT)
public class OxygenBulletGeneratorGUIGuiWindow extends ContainerScreen<OxygenBulletGeneratorGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public OxygenBulletGeneratorGUIGuiWindow(OxygenBulletGeneratorGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 167;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/oxygen_bullet_generator_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		// tooltip Energy
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
		// Oxygen NBT
		double oxygenanimation = (double) (new Object() {
			public double getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getDouble(tag);
				return -1;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "fire"));
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
		// oxygen 0
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload0.png"));
		this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		if (oxygenanimation <= 400) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenloadfull.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 367) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload1.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 334) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload2.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 301) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload3.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 268) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload4.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 235) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload5.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 202) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload6.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 169) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload7.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 136) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload8.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 103) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload9.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 70) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload10.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 37) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload11.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		if (oxygenanimation <= 1) {
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenload12.png"));
			this.blit(ms, this.guiLeft + 76, this.guiTop + 28, 0, 0, 15, 14, 15, 14);
		}
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygenlarge6.png"));
		this.blit(ms, this.guiLeft + 10, this.guiTop + -10, 0, 0, 21, 11, 21, 11);
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
		this.font.drawString(ms, "Oxygen Generator", 37, 5, -13421773);
		this.font.drawString(ms, "Inventory", 6, 74, -13421773);
		if (Oxygenlargeshow1Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world)))
			this.font.drawString(ms, "3x3", 12, -8, -13395712);
		if (Oxygenlargeshow2Procedure.executeProcedure(ImmutableMap.of("x", x, "y", y, "z", z, "world", world)))
			this.font.drawString(ms, "6x6", 12, -8, -13395712);
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
		this.addButton(new Button(this.guiLeft + -20, this.guiTop + 25, 20, 20, new StringTextComponent("-"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBulletGeneratorGUIGui.ButtonPressedMessage(0, x, y, z));
			OxygenBulletGeneratorGUIGui.handleButtonAction(entity, 0, x, y, z);
		}));
		this.addButton(new Button(this.guiLeft + -20, this.guiTop + 5, 20, 20, new StringTextComponent("+"), e -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBulletGeneratorGUIGui.ButtonPressedMessage(1, x, y, z));
			OxygenBulletGeneratorGUIGui.handleButtonAction(entity, 1, x, y, z);
		}));
	}
}
