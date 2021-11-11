package net.mrscauthd.boss_tools.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.entity.renderer.RenderHelper;

@Mod.EventBusSubscriber(modid = "boss_tools", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlassEvent {
    @SubscribeEvent
    public static void AtlasEvent(TextureStitchEvent.Pre event) {
        event.addSprite(RenderHelper.TILE_SURFACE);
    }
}
