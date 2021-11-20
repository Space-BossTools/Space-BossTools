package net.mrscauthd.boss_tools.entity.renderer.alienzombie;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlienZombieRenderer extends MobRenderer<MobEntity, AlienZombieModel<MobEntity>> {
    public AlienZombieRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new AlienZombieModel(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return new ResourceLocation("boss_tools:textures/entities/alien_zombie.png");
    }
}