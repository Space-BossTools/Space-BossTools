
package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class CableGUIGuiWindow extends ContainerScreen<CableGUIGui.GuiContainerMod> {
	public static boolean Button1 = false;
	public static boolean Button1ON = false;
	//Buton 2
	public static boolean Button2 = false;
	public static boolean Button2ON = false;
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = CableGUIGui.guistate;
	public CableGUIGuiWindow(CableGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 176;
		this.ySize = 146;
	}
	private static final ResourceLocation texture = new ResourceLocation("boss_tools:textures/cable_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		if (mouseX > guiLeft + 122 && mouseX < guiLeft + 143 && mouseY > guiTop + 26 && mouseY < guiTop + 47) {
			Button1 = true;
			if (Button1ON == true) {
				this.renderTooltip(ms, new StringTextComponent("ON"), mouseX, mouseY);
			} else {
				this.renderTooltip(ms, new StringTextComponent("OFF"), mouseX, mouseY);
			}
		} else {
			Button1 = false;
		}
		if (mouseX > guiLeft + 147 && mouseX < guiLeft + 168 && mouseY > guiTop + 26 && mouseY < guiTop + 47) {
			Button2 = true;
			if (Button2ON == true) {
				this.renderTooltip(ms, new StringTextComponent("Export Energy"), mouseX, mouseY);
			} else {
				this.renderTooltip(ms, new StringTextComponent("Import Energy"), mouseX, mouseY);
			}
		} else {
			Button2 = false;
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

		Button1ON = (((new Object() {
			public boolean getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getBoolean(tag);
				return false;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "Button1ON"))));

		Button2ON = (((new Object() {
			public boolean getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getBoolean(tag);
				return false;
			}
		}.getValue(world, new BlockPos((int) x, (int) y, (int) z), "Button2ON"))));

		//Buton 1
		//OFF
		if (Button1 == true && Button1ON == false) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_off2.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 123, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_off2.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(0, x, y, z));
						CableGUIGui.handleButtonAction(entity, 0, x, y, z);
					}));
		}
		if (Button1 == false && Button1ON == false) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_off.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 123, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_off.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(0, x, y, z));
						CableGUIGui.handleButtonAction(entity, 0, x, y, z);
					}));
		}
		//ON
		if (Button1 == true && Button1ON == true) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_on2.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 123, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_on2.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(0, x, y, z));
						CableGUIGui.handleButtonAction(entity, 0, x, y, z);
					}));
		}
		if (Button1 == false && Button1ON == true) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_on.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 123, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_on.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(0, x, y, z));
						CableGUIGui.handleButtonAction(entity, 0, x, y, z);
					}));
		}
		//Button 1 end
		//Buton 2
		//OFF
		if (Button2 == true && Button2ON == false) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_off2.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 148, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_off2.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(1, x, y, z));
						CableGUIGui.handleButtonAction(entity, 1, x, y, z);
					}));
		}
		if (Button2 == false && Button2ON == false) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_off.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 148, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_off.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(1, x, y, z));
						CableGUIGui.handleButtonAction(entity, 1, x, y, z);
					}));
		}
		//ON
		if (Button2 == true && Button2ON == true) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_on2.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 148, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_on2.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(1, x, y, z));
						CableGUIGui.handleButtonAction(entity, 1, x, y, z);
					}));
		}
		if (Button2 == false && Button2ON == true) {
			//	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/buttons/button_on.png"));
			//	this.blit(ms, this.guiLeft + 123, this.guiTop + 29, 0, 0, 20, 20, 20, 20);
			this.addButton(new ImageButton(this.guiLeft + 148, this.guiTop + 27, 20, 20, 0, 0, 0, new ResourceLocation("boss_tools:textures/buttons/button_on.png"), 20, 20,
					(p_2130901) -> {
						BossToolsMod.PACKET_HANDLER.sendToServer(new CableGUIGui.ButtonPressedMessage(1, x, y, z));
						CableGUIGui.handleButtonAction(entity, 1, x, y, z);
					}));
		}
		//Button 2 end
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
		this.font.drawString(ms, "Wireless Energy Router", 28, 6, -13421773);
		this.font.drawString(ms, "Inventory", 8, 53, -13421773);
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
