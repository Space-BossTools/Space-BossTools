
package net.mrscauthd.boss_tools.gui.overlay;

import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@BossToolsModElements.ModElement.Tag
public class LanderWarningOverlay extends BossToolsModElements.ModElement {
	double counter = 1;
	boolean check = false;
	public LanderWarningOverlay(BossToolsModElements instance) {
		super(instance, 693);
	}

	@Override
	public void initElements() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		PlayerEntity entity = event.player;
		if (event.phase == TickEvent.Phase.END) {
			if (check == false) {
				counter = counter - 0.025;
				if (counter <= 0.2) {
					check = true;
				}
			}
			if (check == true) {
				counter = counter + 0.025;
				if (counter >= 1.2) {
					check = false;
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
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
			if ((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity && entity.getRidingEntity().isOnGround() == false) {
		/*		if (check == false) {
					counter = counter - 0.01;
					if (counter <= 0.2) {
						check = true;
					}
				}
				if (check == true) {
					counter = counter + 0.01;
					if (counter >= 1.2) {
						check = false;
					}
				}*/
				RenderSystem.color4f((float) counter, (float) counter, (float) counter, (float) counter);
				// Plinken
				Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/warning1.png"));
				Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getScaledWidth(),
						event.getWindow().getScaledHeight(), event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
			}
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			if ((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity && entity.getRidingEntity().isOnGround() == false) {
				double speed = Math.round(100.0 * (entity.getRidingEntity()).getMotion().getY()) / 100.0;
				double speedcheck = speed;
				Minecraft.getInstance().fontRenderer.drawString(event.getMatrixStack(), "" + speedcheck + " Speed",
						event.getWindow().getScaledWidth() / 2 - 25, event.getWindow().getScaledHeight() / 2 - 100, -3407872);
			}
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
