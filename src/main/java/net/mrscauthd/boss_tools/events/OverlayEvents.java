package net.mrscauthd.boss_tools.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.entity.*;

@Mod.EventBusSubscriber(modid = "boss_tools", value = Dist.CLIENT)
public class OverlayEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void Overlay(RenderGameOverlayEvent event) {
        //Disable Food Overlay
        {
            if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
                PlayerEntity entity = Minecraft.getInstance().player;
                if (entity.getRidingEntity() instanceof RocketTier1Entity.CustomEntity || entity.getRidingEntity() instanceof RocketTier3Entity.CustomEntity || entity.getRidingEntity() instanceof RocketTier2Entity.CustomEntity || entity.getRidingEntity() instanceof LanderEntity.CustomEntity || entity.getRidingEntity() instanceof RoverEntity.CustomEntity) {
                    event.setCanceled(true);
                }
            }
        }
        //Lander Warning Overlay
        {
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
                if ((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity && entity.getRidingEntity().isOnGround() == false && entity.areEyesInFluid(FluidTags.WATER) == (false)) {
                    RenderSystem.color4f((float) Events.counter, (float) Events.counter, (float) Events.counter, (float) Events.counter);
                    // Plinken
                    Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("boss_tools:textures/warning1.png"));
                    Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getScaledWidth(),
                            event.getWindow().getScaledHeight(), event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
                }
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                if ((entity.getRidingEntity()) instanceof LanderEntity.CustomEntity && entity.getRidingEntity().isOnGround() == false && entity.areEyesInFluid(FluidTags.WATER) == (false)) {
                    double speed = Math.round(100.0 * (entity.getRidingEntity()).getMotion().getY()) / 100.0;
                    double speedcheck = speed;
                    Minecraft.getInstance().fontRenderer.drawString(event.getMatrixStack(), "" + speedcheck + " Speed",
                            event.getWindow().getScaledWidth() / 2 - 29, event.getWindow().getScaledHeight() / 2 / 2.3f, -3407872);
                }
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.enableAlphaTest();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
