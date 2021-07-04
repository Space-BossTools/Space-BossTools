
package net.mrscauthd.boss_tools.item;

import net.minecraft.util.SoundEvent;
import net.mrscauthd.boss_tools.procedures.AlienspitentityWhileBulletFlyingTickProcedure;
import net.mrscauthd.boss_tools.entity.renderer.AlienspitentityRenderer;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.IPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class AlienspitentityItem extends BossToolsModElements.ModElement {
    // @ObjectHolder("boss_tools:alienspitentity")
    // public static final Item block = null;
    public static final EntityType arrow = (EntityType.Builder.<ArrowCustomEntity>create(ArrowCustomEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
            .size(0.5f, 0.5f)).build("alien_spit_entity").setRegistryName("alien_spit_entity");
    public AlienspitentityItem(BossToolsModElements instance) {
        super(instance, 376);
        FMLJavaModLoadingContext.get().getModEventBus().register(new AlienspitentityRenderer.ModelRegisterHandler());
    }

    @Override
    public void initElements() {
        // elements.items.add(() -> new ItemRanged());
        elements.entities.add(() -> arrow);
    }
    /*
     * public static class ItemRanged extends Item { public ItemRanged() { super(new
     * Item.Properties().group(null).maxStackSize(1));
     * setRegistryName("alienspitentity"); }
     *
     * @Override public ActionResult<ItemStack> onItemRightClick(World world,
     * PlayerEntity entity, Hand hand) { entity.setActiveHand(hand); return new
     * ActionResult(ActionResultType.SUCCESS, entity.getHeldItem(hand)); }
     *
     * @Override public UseAction getUseAction(ItemStack itemstack) { return
     * UseAction.NONE; }
     *
     * @Override public int getUseDuration(ItemStack itemstack) { return 72000; }
     *
     * @Override public Multimap<Attribute, AttributeModifier>
     * getAttributeModifiers(EquipmentSlotType slot) { if (slot ==
     * EquipmentSlotType.MAINHAND) { ImmutableMultimap.Builder<Attribute,
     * AttributeModifier> builder = ImmutableMultimap.builder();
     * builder.putAll(super.getAttributeModifiers(slot));
     * builder.put(Attributes.ATTACK_DAMAGE, new
     * AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Ranged item modifier", (double)
     * -2, AttributeModifier.Operation.ADDITION));
     * builder.put(Attributes.ATTACK_SPEED, new
     * AttributeModifier(ATTACK_SPEED_MODIFIER, "Ranged item modifier", -2.4,
     * AttributeModifier.Operation.ADDITION)); return builder.build(); } return
     * super.getAttributeModifiers(slot); }
     *
     * @Override public void onPlayerStoppedUsing(ItemStack itemstack, World world,
     * LivingEntity entityLiving, int timeLeft) { if (!world.isRemote &&
     * entityLiving instanceof ServerPlayerEntity) { ServerPlayerEntity entity =
     * (ServerPlayerEntity) entityLiving; double x = entity.getPosX(); double y =
     * entity.getPosY(); double z = entity.getPosZ(); if (true) { ArrowCustomEntity
     * entityarrow = shoot(world, entity, random, 1f, 5, 5); itemstack.damageItem(1,
     * entity, e -> e.sendBreakAnimation(entity.getActiveHand()));
     * entityarrow.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED; } } }
     * }
     */
    @OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
    public static class ArrowCustomEntity extends AbstractArrowEntity implements IRendersAsItem {
        public ArrowCustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            super(arrow, world);
        }

        public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, World world) {
            super(type, world);
        }

        public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, double x, double y, double z, World world) {
            super(type, x, y, z, world);
        }

        public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, LivingEntity entity, World world) {
            super(type, entity, world);
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack getItem() {
            return new ItemStack(IceshardItem.block, (int) (1));
        }

        @Override
        protected ItemStack getArrowStack() {
            return null;
        }

        @Override
        protected SoundEvent getHitEntitySound() {
            return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
        }


        @Override
        protected void arrowHit(LivingEntity entity) {
            super.arrowHit(entity);
            entity.setArrowCountInEntity(entity.getArrowCountInEntity() - 1);
        }

        @Override
        public void tick() {
            super.tick();
            double x = this.getPosX();
            double y = this.getPosY();
            double z = this.getPosZ();
            World world = this.world;
            Entity entity = this.func_234616_v_();
            {
                Map<String, Object> $_dependencies = new HashMap<>();
                $_dependencies.put("x", x);
                $_dependencies.put("y", y);
                $_dependencies.put("z", z);
                $_dependencies.put("world", world);
                AlienspitentityWhileBulletFlyingTickProcedure.executeProcedure($_dependencies);
            }
            if (this.inGround) {
                this.remove();
            }
        }
    }
    public static ArrowCustomEntity shoot(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, world);
        entityarrow.shoot(entity.getLookVec().x, entity.getLookVec().y, entity.getLookVec().z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setIsCritical(false);
        entityarrow.setDamage(damage);
        entityarrow.setKnockbackStrength(knockback);
        world.addEntity(entityarrow);
        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();
        world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
                (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static ArrowCustomEntity shoot(LivingEntity entity, LivingEntity target) {
        ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, entity.world);
        double d0 = target.getPosY() + (double) target.getEyeHeight() - 1.1;
        double d1 = target.getPosX() - entity.getPosX();
        double d3 = target.getPosZ() - entity.getPosZ();
        entityarrow.shoot(d1, d0 - entityarrow.getPosY() + (double) MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 1f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setDamage(5);
        entityarrow.setKnockbackStrength(5);
        entityarrow.setIsCritical(false);
        entity.world.addEntity(entityarrow);
        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();
        entity.world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
                (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                SoundCategory.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
