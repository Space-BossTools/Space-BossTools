
package net.mrscauthd.boss_tools.gui.overlay;

import net.mrscauthd.boss_tools.item.SpaceArmorItem;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
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
			int posX = (event.getWindow().getScaledWidth()) / 2;
			int posY = (event.getWindow().getScaledHeight()) / 2;
			PlayerEntity entity = Minecraft.getInstance().player;
			World world = entity.world;
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			if ((((entity instanceof LivingEntity)
					? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
					: ItemStack.EMPTY).getItem() == new ItemStack(SpaceArmorItem.body, (int) (1)).getItem())) {
				//set Energy
				double EnergyNBT = (double) (((entity instanceof LivingEntity)
						? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
						: ItemStack.EMPTY).getOrCreateTag().getDouble("Energy"));
				//Main Texture
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck.png"));
				//Math Textures
				if (EnergyNBT >= 1920) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck1.png"));
				}
				if (EnergyNBT >= 3840) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck2.png"));
				}
				if (EnergyNBT >= 5760) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck3.png"));
				}
				if (EnergyNBT >= 7680) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck4.png"));
				}
				if (EnergyNBT >= 9600) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck5.png"));
				}
				if (EnergyNBT >= 11520) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck6.png"));
				}
				if (EnergyNBT >= 13440) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck7.png"));
				}
				if (EnergyNBT >= 15360) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck8.png"));
				}
				if (EnergyNBT >= 17280) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck9.png"));
				}
				if (EnergyNBT >= 19200) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck10.png"));
				}
				if (EnergyNBT >= 21120) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck11.png"));
				}
				if (EnergyNBT >= 23040) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck12.png"));
				}
				if (EnergyNBT >= 24960) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck13.png"));
				}
				if (EnergyNBT >= 26880) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck14.png"));
				}
				if (EnergyNBT >= 28800) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck15.png"));
				}
				if (EnergyNBT >= 30720) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck16.png"));
				}
				if (EnergyNBT >= 32640) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck17.png"));
				}
				if (EnergyNBT >= 34560) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck18.png"));
				}
				if (EnergyNBT >= 36480) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck19.png"));
				}
				if (EnergyNBT >= 38400) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck20.png"));
				}
				if (EnergyNBT >= 40320) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck21.png"));
				}
				if (EnergyNBT >= 42240) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck22.png"));
				}
				if (EnergyNBT >= 44160) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck23.png"));
				}
				if (EnergyNBT >= 46080) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck24.png"));
				}
				if (EnergyNBT >= 48000) {
					Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/oxygentankcheck25.png"));
				}
					Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getScaledWidth(),
							event.getWindow().getScaledHeight(), event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
			}
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
