package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.mrscauthd.boss_tools.MobInnet;
import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsSpawnEggsItemGroup;
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
public class PygroEntity extends BossToolsModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire()
            .size(0.6f, 1.8f)).build("pygro").setRegistryName("pygro");
    public PygroEntity(BossToolsModElements instance) {
        super(instance, 453);
        //FMLJavaModLoadingContext.get().getModEventBus().register(new PygrosRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -3381760, -6750208, new Item.Properties().group(SpaceBosstoolsSpawnEggsItemGroup.tab))
                .setRegistryName("pygro_spawn_egg"));
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
        protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinEntity>>> field_234405_b_ = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, MobInnet.PYGRO_SENSOR.get() /*SensorType.PIGLIN_SPECIFIC_SENSOR*/);
        protected static final ImmutableList<MemoryModuleType<?>> field_234414_c_ = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.OPENED_DOORS, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEAREST_ADULT_PIGLINS, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.AVOID_TARGET, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryModuleType.CELEBRATE_LOCATION, MemoryModuleType.DANCING, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.RIDE_TARGET, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN/*, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD*/, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.NEAREST_REPELLENT);
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<CustomEntity> type, World world) {
            super(type, world);
            experienceValue = 0;
            setNoAI(false);
        }

     /*   @Override
        public void baseTick() {
            super.baseTick();
            Brain<PiglinEntity> brain = this.getBrain();
            brain.removeMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        }*/

        @Override
        protected Brain.BrainCodec<PiglinEntity> getBrainCodec() {
            return Brain.createCodec(field_234414_c_, field_234405_b_);
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