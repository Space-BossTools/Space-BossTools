
package net.mrscauthd.boss_tools.gui.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

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
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			
			PlayerEntity entity = Minecraft.getInstance().player;
			ItemStack chest = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);
			Item chestItem = chest.getItem();
			
			if (chestItem == ModInnet.SPACE_SUIT.get() || chestItem == ModInnet.NETHERITE_SPACE_SUIT.get()) {
				double oxygenStoredRatio = chest.getCapability(CapabilityOxygen.OXYGEN).map(s -> s.getOxygenStoredRatio()).orElse(0.0D);
				ResourceLocation empty = new ResourceLocation("boss_tools:textures/oxygentankcheck_empty.png");
				ResourceLocation full = new ResourceLocation("boss_tools:textures/oxygentankcheck_full.png");
				double scale = event.getWindow().getScaledWidth() / 1280.0D;
				GuiHelper.drawVerticalReverse(event.getMatrixStack(), 5, 5, (int)(124 * scale), (int)(104 * scale), empty, oxygenStoredRatio);
				GuiHelper.drawVertical(event.getMatrixStack(), 5, 5, (int)(124 * scale), (int)(104 * scale), full, oxygenStoredRatio);
			}
			
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
