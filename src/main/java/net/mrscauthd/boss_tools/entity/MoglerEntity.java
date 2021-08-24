package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.*;
import net.minecraft.world.server.ServerWorld;
//import net.mrscauthd.boss_tools.entity.renderer.MoglerRenderer;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.World;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import javax.annotation.Nullable;

@BossToolsModElements.ModElement.Tag
public class MoglerEntity extends BossToolsModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
            .size(1.4f, 1.4f)).build("mogler").setRegistryName("mogler");
    public MoglerEntity(BossToolsModElements instance) {
        super(instance, 456);
      //  FMLJavaModLoadingContext.get().getModEventBus().register(new MoglerRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -13312, -3407872, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs))
                .setRegistryName("mogler_spawn_egg"));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }
    private static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
            ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
            ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 40);
            ammma = ammma.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.6);
            ammma = ammma.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.6);
            ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
            event.put(entity, ammma.create());
        }
    }

    public static class CustomEntity extends HoglinEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<CustomEntity> type, World world) {
            super(type, world);
            setNoAI(false);
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        public CreatureAttribute getCreatureAttribute() {
            return CreatureAttribute.UNDEAD;
        }

        @Nullable
        @Override
        public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
            MoglerEntity.CustomEntity moglerentity = (MoglerEntity.CustomEntity) MoglerEntity.entity.create(p_241840_1_);
            if (moglerentity != null) {
                moglerentity.enablePersistence();
            }

            return moglerentity;
        }
    }
}
