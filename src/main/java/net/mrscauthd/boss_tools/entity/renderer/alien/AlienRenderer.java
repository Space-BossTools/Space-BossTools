package net.mrscauthd.boss_tools.entity.renderer.alien;


import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

public class AlienRenderer extends MobRenderer<AlienEntity, AlienModel<AlienEntity>> {
    public AlienRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AlienModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(AlienEntity entity) {
        if (entity.getVillagerData().getProfession() == VillagerProfession.FARMER) {
            return new ResourceLocation("boss_tools","textures/entity_alien1.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.FISHERMAN) {
            return new ResourceLocation("boss_tools","textures/entity_alien2.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.SHEPHERD) {
            return new ResourceLocation("boss_tools","textures/entity_alien3.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.FLETCHER) {
            return new ResourceLocation("boss_tools","textures/entity_alien4.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
            return new ResourceLocation("boss_tools","textures/entity_alien5.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.CARTOGRAPHER) {
            return new ResourceLocation("boss_tools","textures/entity_alien6.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.CLERIC) {
            return new ResourceLocation("boss_tools","textures/entity_alien7.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.ARMORER) {
            return new ResourceLocation("boss_tools","textures/entity_alien8.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.WEAPONSMITH) {
            return new ResourceLocation("boss_tools","textures/entity_alien9.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.TOOLSMITH) {
            return new ResourceLocation("boss_tools","textures/entity_alien10.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.BUTCHER) {
            return new ResourceLocation("boss_tools","textures/entity_alien11.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER) {
            return new ResourceLocation("boss_tools","textures/entity_alien12.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.MASON) {
            return new ResourceLocation("boss_tools","textures/entity_alien13.png");
        }

        return new ResourceLocation("boss_tools","textures/entity_alien.png");
    }
}