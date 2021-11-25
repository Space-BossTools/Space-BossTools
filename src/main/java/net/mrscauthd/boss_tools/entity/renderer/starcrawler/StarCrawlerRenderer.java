package net.mrscauthd.boss_tools.entity.renderer.starcrawler;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;

@OnlyIn(Dist.CLIENT)
public class StarCrawlerRenderer extends MobRenderer<MobEntity, StarCrawlerModel<MobEntity>> {
    public StarCrawlerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new StarCrawlerModel(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/entities/starfish.png");
    }
}