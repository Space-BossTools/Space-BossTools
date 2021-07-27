
package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsSpawnEggsItemGroup;
import net.mrscauthd.boss_tools.entity.renderer.PygrosRenderer;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureAttribute;

@BossToolsModElements.ModElement.Tag
public class PygrosEntity extends BossToolsModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire()
            .size(0.6f, 1.8f)).build("pygros").setRegistryName("pygros");
    public PygrosEntity(BossToolsModElements instance) {
        super(instance, 453);
        FMLJavaModLoadingContext.get().getModEventBus().register(new PygrosRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -3381760, -6750208, new Item.Properties().group(SpaceBosstoolsSpawnEggsItemGroup.tab))
                .setRegistryName("pygros_spawn_egg"));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }
    private static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
            ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35);
            ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 16);
            ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 5);
            event.put(entity, ammma.create());
        }
    }

    public static class CustomEntity extends PiglinEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<CustomEntity> type, World world) {
            super(type, world);
            experienceValue = 0;
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
    }
}