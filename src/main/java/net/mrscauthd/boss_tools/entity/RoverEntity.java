
package net.mrscauthd.boss_tools.entity;

import net.mrscauthd.boss_tools.procedures.RoverOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.procedures.RoverEntityIsHurtProcedure;
import net.mrscauthd.boss_tools.entity.renderer.RoverRenderer;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class RoverEntity extends BossToolsModElements.ModElement {
	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire()
			.size(2.5f, 0.5f)).build("rover").setRegistryName("rover");
	public static double wheel = 0;
	public RoverEntity(BossToolsModElements instance) {
		super(instance, 394);
		FMLJavaModLoadingContext.get().getModEventBus().register(new RoverRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		NetworkLoader.registerMessages();
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}
	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 10);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 0);
			event.put(entity, ammma.create());
		}
	}

	public static class CustomEntity extends CreatureEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
			enablePersistence();
		}

		// test
		protected void applyYawToEntity(Entity entityToUpdate) {
			entityToUpdate.setRenderYawOffset(this.rotationYaw);
			float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
			float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
			entityToUpdate.prevRotationYaw += f1 - f;
			entityToUpdate.rotationYaw += f1 - f;
			entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
		}

		@OnlyIn(Dist.CLIENT)
		public void applyOrientationToEntity(Entity entityToUpdate) {
			this.applyYawToEntity(entityToUpdate);
		}

		// Lead FIX
		@Override
		public boolean canBeLeashedTo(PlayerEntity player) {
			return false;
		}

		// HitBox
		@Override
		public boolean func_241845_aY() {
			return true;
		}

		// Hit Box FIX
		@Override
		protected void collideWithEntity(Entity p_82167_1_) {
		}

		@Override
		public void applyEntityCollision(Entity entityIn) {
		}

		@Override
		protected void removePassenger(Entity passenger) {
			if (passenger.isSneaking() && !passenger.world.isRemote) {
				if (passenger instanceof ServerPlayerEntity) {
					ServerPlayerEntity playerEntity = (ServerPlayerEntity) passenger;
					wheel = 0;
				}
			}
			super.removePassenger(passenger);
		}

		@Override
		public double getMountedYOffset() {
			return super.getMountedYOffset() + -0.1;
		}

		// public float getDamageTaken() {
		// return 0;
		// }

		// test end
		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			Entity sourceentity = source.getTrueSource();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				RoverEntityIsHurtProcedure.executeProcedure($_dependencies);
			}
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source.getImmediateSource() instanceof PlayerEntity)
				return false;
			if (source.getImmediateSource() instanceof PotionEntity)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.CACTUS)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			if (source == DamageSource.LIGHTNING_BOLT)
				return false;
			if (source.isExplosion())
				return false;
			if (source.getDamageType().equals("trident"))
				return false;
			if (source == DamageSource.ANVIL)
				return false;
			if (source == DamageSource.DRAGON_BREATH)
				return false;
			if (source == DamageSource.WITHER)
				return false;
			if (source.getDamageType().equals("witherSkull"))
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			super.func_230254_b_(sourceentity, hand);
			sourceentity.startRiding(this);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			// damage remove
			// entity.isInvulnerable();
			return retval;
		}

		@Override
		public void baseTick() {
			super.baseTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			// fall damage
			// entity.getAlwaysRenderNameTagForRender();
			this.fallDistance = (float) (0);
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				RoverOnEntityTickUpdateProcedure.executeProcedure($_dependencies);
			}
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new RotationSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("Rotation")));
			// new Nbt
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new WheelSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("Wheel")));
		}

		@Override
		public void travel(Vector3d dir) {
			Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
			if (this.isBeingRidden()) {
				// entity.fallDistance = (float) (0);
				this.rotationYaw = this.rotationYaw = (float) this.getPersistentData().getDouble("Rotation"); // change
				this.prevRotationYaw = this.rotationYaw;
				this.rotationPitch = entity.rotationPitch * 0.5F;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.15F;
				this.renderYawOffset = this.rotationYaw; // change
				this.rotationYawHead = this.rotationYaw; // change
				this.stepHeight = 1.0F;
				// test
				if (entity instanceof LivingEntity) {
					float forward = ((LivingEntity) entity).moveForward;
					if (forward >= 0) {
						// System.out.println(forward + "test1");
						float strafe = 0;
						if (this.getAIMoveSpeed() >= 0.01) {
							// wheel
							if (wheel <= 0.35) {
								wheel = wheel + 0.02;
							}
						}
						if (this.getAIMoveSpeed() <= 0.25) {
							this.setAIMoveSpeed(this.getAIMoveSpeed() + (float) 0.02);
							// System.out.println(this.getAIMoveSpeed());
						}
						if (forward == 0) {
							this.setAIMoveSpeed(0f);
							// wheel
							if (wheel != 0) {
								if (wheel >= 0) {
									wheel = wheel - 0.02;
								}
							}
						}
						this.getPersistentData().putDouble("Wheel", this.getPersistentData().getDouble("Wheel") + wheel);// 0.35
						super.travel(new Vector3d(strafe, 0, forward));
					}
					if (forward <= -0.01) {
						// System.out.println(forward + "test2");
						wheel = 0;
						float strafe = 0;
						if (this.getAIMoveSpeed() >= 0.01) {
							this.getPersistentData().putDouble("Wheel", this.getPersistentData().getDouble("Wheel") - 0.14);
						}
						if (this.getAIMoveSpeed() <= 0.06) { // weil es ja erst gemacht werden muss das ist nur der
																// block
							this.setAIMoveSpeed(this.getAIMoveSpeed() + (float) 0.02);
						}
						if (this.getAIMoveSpeed() >= 0.08) { // FIX
							this.setAIMoveSpeed((float) 0);
						}
						if (forward == 0) {
							this.setAIMoveSpeed(0f);
							// wheel = 0;
						}
						super.travel(new Vector3d(strafe, 0, forward));
					}
				}
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.getPosX() - this.prevPosX;
				double d0 = this.getPosZ() - this.prevPosZ;
				float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
				this.limbSwing += this.limbSwingAmount;
				return;
			} else {
				// wheel = 0;
			}
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(dir);
		}
	}

	// packages System
	private static class NetworkLoader {
		public static SimpleChannel INSTANCE;
		private static int id = 1;
		public static int nextID() {
			return id++;
		}

		public static void registerMessages() {
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "rover_link"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), RotationSpinPacket.class, RotationSpinPacket::encode, RotationSpinPacket::decode,
					RotationSpinPacket::handle);
			// new animationpitch
			INSTANCE.registerMessage(nextID(), WheelSpinPacket.class, WheelSpinPacket::encode, WheelSpinPacket::decode, WheelSpinPacket::handle);
		}
	}

	// First Animation
	private static class RotationSpinPacket {
		private double rotation;
		private int entityId;
		public RotationSpinPacket(int entityId, double rotation) {
			this.rotation = rotation;
			this.entityId = entityId;
		}

		public static void encode(RotationSpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.rotation);
		}

		public static RotationSpinPacket decode(PacketBuffer buf) {
			return new RotationSpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(RotationSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("Rotation", msg.rotation);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}

	// Wheel Animation
	private static class WheelSpinPacket {
		private double wheel;
		private int entityId;
		public WheelSpinPacket(int entityId, double wheel) {
			this.wheel = wheel;
			this.entityId = entityId;
		}

		public static void encode(WheelSpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.wheel);
		}

		public static WheelSpinPacket decode(PacketBuffer buf) {
			return new WheelSpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(WheelSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("Wheel", msg.wheel);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
