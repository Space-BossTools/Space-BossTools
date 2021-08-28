
package net.mrscauthd.boss_tools.gui.overlay;

import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.armor.CapabilityOxygen;
import net.mrscauthd.boss_tools.armor.IOxygenStorage;
import net.mrscauthd.boss_tools.armor.SpaceSuit;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@BossToolsModElements.ModElement.Tag
public class OxygenTankCheckOverlay extends BossToolsModElements.ModElement {
	public OxygenTankCheckOverlay(BossToolsModElements instance) {
		super(instance, 677);
	}

	@Override
	public void initElements() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void eventHandler(RenderGameOverlayEvent event) {
		if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
			PlayerEntity entity = Minecraft.getInstance().player;
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			
			ItemStack chest = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);
			Item chestItem = chest.getItem();
			
			if (chestItem == ModInnet.SPACE_SUIT.get() || chestItem == ModInnet.NETHERITE_SPACE_SUIT.get()) {
				int oxygen = chest.getCapability(CapabilityOxygen.OXYGEN).map(s->s.getOxygenStored()).orElse(0);
				
				// Main Texture
				Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck.png"));
				// Math Textures
				if (oxygen >= 1920) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck1.png"));
				}
				if (oxygen >= 3840) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck2.png"));
				}
				if (oxygen >= 5760) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck3.png"));
				}
				if (oxygen >= 7680) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck4.png"));
				}
				if (oxygen >= 9600) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck5.png"));
				}
				if (oxygen >= 11520) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck6.png"));
				}
				if (oxygen >= 13440) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck7.png"));
				}
				if (oxygen >= 15360) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck8.png"));
				}
				if (oxygen >= 17280) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck9.png"));
				}
				if (oxygen >= 19200) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck10.png"));
				}
				if (oxygen >= 21120) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck11.png"));
				}
				if (oxygen >= 23040) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck12.png"));
				}
				if (oxygen >= 24960) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck13.png"));
				}
				if (oxygen >= 26880) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck14.png"));
				}
				if (oxygen >= 28800) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck15.png"));
				}
				if (oxygen >= 30720) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck16.png"));
				}
				if (oxygen >= 32640) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck17.png"));
				}
				if (oxygen >= 34560) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck18.png"));
				}
				if (oxygen >= 36480) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck19.png"));
				}
				if (oxygen >= 38400) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck20.png"));
				}
				if (oxygen >= 40320) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck21.png"));
				}
				if (oxygen >= 42240) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck22.png"));
				}
				if (oxygen >= 44160) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck23.png"));
				}
				if (oxygen >= 46080) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck24.png"));
				}
				if (oxygen >= 48000) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck25.png"));
				}
				
				MainWindow window = event.getWindow();
				Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), 0, 0, 0, 0, window.getScaledWidth(), window.getScaledHeight(), window.getScaledWidth(), window.getScaledHeight());
			}
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
