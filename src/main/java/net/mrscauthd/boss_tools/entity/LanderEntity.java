
package net.mrscauthd.boss_tools.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.entity.renderer.LanderRenderer;
import net.mrscauthd.boss_tools.gui.LandinggearGuiGui;

@BossToolsModElements.ModElement.Tag
public class LanderEntity extends BossToolsModElements.ModElement {
	public static EntityType<? extends CustomEntity> entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(100).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 2f)).build("lander");

	public LanderEntity(BossToolsModElements instance) {
		super(instance, 92);
		FMLJavaModLoadingContext.get().getModEventBus().register(new LanderRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
	}

	@Override
	public void initElements() {
		entity.setRegistryName("lander");
		elements.entities.add(() -> entity);
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 1);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
			event.put(entity, ammma.create());
		}
	}

	public static class CustomEntity extends CreatureEntity implements INamedContainerProvider, IVehicleEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
			enablePersistence();
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		// Lead FIX
		@Override
		public boolean canBeLeashedTo(PlayerEntity player) {
			return false;
		}

		// HitBox
		public boolean canBePushed() {
			return false;
		}

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

		// Riding water
		@Deprecated
		public boolean canBeRiddenInWater() {
			return true;
		}

		public boolean canBeHitWithPotion() {
			return false;
		}

		@Override
		protected boolean canTriggerWalking() {
			return false;
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

		@SuppressWarnings("resource")
		@Override
		@OnlyIn(Dist.CLIENT)
		public double getMountedYOffset() {
			boolean isFirstPerson = Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON);
			return super.getMountedYOffset() + (isFirstPerson ? -0.25 : -0.70);
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return null;
		}

		@Override
		public SoundEvent getDeathSound() {
			return null;
		}

		@Override
		public void onKillCommand() {
			this.onDeath(DamageSource.OUT_OF_WORLD);
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.FALL) {
				super.attackEntityFrom(source, amount);
				return true;
			} else {
				Entity sourceentity = source.getTrueSource();

				if (sourceentity != null) {
					if (sourceentity.isSneaking() && !this.isBeingRidden()) {
						if (!this.world.isRemote()) {
							this.onDeath(DamageSource.OUT_OF_WORLD);
						}
					}
				}

				return false;
			}

		}

		@Override
		public void onDeath(DamageSource source) {
			super.onDeath(source);

			if (source == DamageSource.FALL) {
				BlockPos pos = this.getPosition();

				if (!this.world.isRemote) {
					this.world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10.0F, Explosion.Mode.BREAK);
				}
			}

			this.remove();
		}

		private final ItemStackHandler inventory = new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}
		};
		private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));

		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
			if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null)
				return LazyOptional.of(() -> combined).cast();
			return super.getCapability(capability, side);
		}

		@Override
		protected void dropInventory() {
			super.dropInventory();
			for (int i = 0; i < inventory.getSlots(); ++i) {
				ItemStack itemstack = inventory.getStackInSlot(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.entityDropItem(itemstack);
				}
			}
		}

		@Override
		public void writeAdditional(CompoundNBT compound) {
			super.writeAdditional(compound);
			compound.put("InventoryCustom", inventory.serializeNBT());
		}

		@Override
		public void readAdditional(CompoundNBT compound) {
			super.readAdditional(compound);
			INBT inventoryCustom = compound.get("InventoryCustom");
			if (inventoryCustom instanceof CompoundNBT)
				inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			if (sourceentity instanceof ServerPlayerEntity && sourceentity.isSneaking()) {
				openGui((ServerPlayerEntity) sourceentity);
			} else {
				sourceentity.startRiding(this);
			}

			return ActionResultType.func_233537_a_(this.world.isRemote());
		}

		public void openGui(ServerPlayerEntity player) {
			NetworkHooks.openGui(player, this, buffer -> buffer.writeInt(this.getEntityId()));
		}

		@Override
		public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
			return new LandinggearGuiGui.GuiContainerMod(id, inventory, this);
		}

		public boolean isOnGroundOrInWater() {
			return this.isOnGround() || this.isInWater();
		}

		@Override
		public void baseTick() {
			super.baseTick();

			World world = this.world;
			Vector3d pos = this.getPositionVec();

			if (this.isLander1()) {
				if (world instanceof ServerWorld) {
					CommandSource source = new CommandSource(ICommandSource.DUMMY, pos, Vector2f.ZERO, (ServerWorld) world, 4, "", new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled();
					((ServerWorld) world).getServer().getCommandManager().handleCommand(source, "/particle minecraft:spit ~ ~-0.3 ~ .1 .1 .1 .001 3 force");
				}

				if (this.isOnGroundOrInWater()) {
					this.setLander1(false);
					this.setLander2(false);
				}

			}

			this.setAir(300);
		}

		public void reduceFallSpeed() {
			if (!this.isOnGroundOrInWater()) {
				Vector3d motion = this.getMotion();

				if (motion.getY() <= -0.05) {
					this.setMotion(motion.getX(), motion.getY() * 0.85, motion.getZ());
				}

				this.setLander1(true);
				this.setLander2(true);
			}

			this.fallDistance = (float) (this.getMotion().getY() * -4.5D);
		}

		public boolean isLander1() {
			return this.getPersistentData().getBoolean("Lander1");
		}

		public void setLander1(boolean is) {
			this.getPersistentData().putBoolean("Lander1", is);
		}

		public boolean isLander2() {
			return this.getPersistentData().getBoolean("Lander2");
		}

		public void setLander2(boolean is) {
			this.getPersistentData().putBoolean("Lander2", is);
		}

		public IItemHandlerModifiable getInventory() {
			return this.inventory;
		}
	}
}
