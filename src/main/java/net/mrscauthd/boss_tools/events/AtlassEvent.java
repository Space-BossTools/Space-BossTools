package net.mrscauthd.boss_tools.events;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "boss_tools", bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlassEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void AtlasEvent(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation("boss_tools", "entities/tile_entity_box_oxygen_generator"));
    }
}
