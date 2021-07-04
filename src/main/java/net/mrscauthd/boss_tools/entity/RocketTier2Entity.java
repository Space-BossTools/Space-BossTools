
package net.mrscauthd.boss_tools.entity;

import net.mrscauthd.boss_tools.procedures.Rockethurtentity2Procedure;
import net.mrscauthd.boss_tools.procedures.RocketOnEntityTicktier2Procedure;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.gui.RocketTier2GuiFuelGui;
import net.mrscauthd.boss_tools.entity.renderer.RocketTier2Renderer;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

@BossToolsModElements.ModElement.Tag
public class RocketTier2Entity extends BossToolsModElements.ModElement {
	public static EntityType entity = null;
	public RocketTier2Entity(BossToolsModElements instance) {
		super(instance, 13);
		FMLJavaModLoadingContext.get().getModEventBus().register(new RocketTier2Renderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		NetworkLoader.registerMessages();
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(100).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(1f, 4f))
						.build("rocket_tier_2").setRegistryName("rocket_tier_2");
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
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 1000);
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
			experienceValue = 5;
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

		// Hit Box FIX
		public boolean canBePushed() {
			return false;
		}

		@Override
		protected void collideWithEntity(Entity p_82167_1_) {
		}

		@Override
		public void applyEntityCollision(Entity entityIn) {
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

		@Override
		public double getMountedYOffset() {
			return super.getMountedYOffset() + -2.1;
		}

		@Override
		protected void removePassenger(Entity passenger) {
			if (passenger.isSneaking() && !passenger.world.isRemote) {
				if (passenger instanceof ServerPlayerEntity) {
					ServerPlayerEntity playerEntity = (ServerPlayerEntity) passenger;
					playerEntity.getPersistentData().putBoolean("dismount", true);
				}
			}
			super.removePassenger(passenger);
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
		public void onKillCommand() {
			Entity entity = this;
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			for (int i = 0; i < inventory.getSlots(); ++i) {
				ItemStack itemstack = inventory.getStackInSlot(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.entityDropItem(itemstack);
				}
			}
			ItemStack itemfuel = ItemStack.EMPTY;
			itemfuel = new ItemStack(Tier2RocketItemItem.block, (int) (1));
			(itemfuel).getOrCreateTag().putDouble("Rocketfuel", (entity.getPersistentData().getDouble("Rocketfuel")));
			(itemfuel).getOrCreateTag().putDouble("fuel", (entity.getPersistentData().getDouble("fuel")));
			(itemfuel).getOrCreateTag().putDouble("fuelgui", ((entity.getPersistentData().getDouble("fuel")) / 4));
			if (world instanceof World && !world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, (itemfuel));
				entityToSpawn.setPickupDelay((int) 10);
				world.addEntity(entityToSpawn);
			}
			this.remove();
			super.onKillCommand();
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
				$_dependencies.put("entity", entity);
				$_dependencies.put("sourceentity", sourceentity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				Rockethurtentity2Procedure.executeProcedure($_dependencies);
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
			// super.attackEntityFrom(source, amount);
			// return super.attackEntityFrom(source, amount);
			return false;
		}
		private final ItemStackHandler inventory = new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}
		};
		private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this),
				new EntityArmorInvWrapper(this));
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
			if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null)
				return LazyOptional.of(() -> combined).cast();
			return super.getCapability(capability, side);
		}

		/*
		 * @Override protected void dropInventory() { super.dropInventory(); for (int i
		 * = 0; i < inventory.getSlots(); ++i) { ItemStack itemstack =
		 * inventory.getStackInSlot(i); if (!itemstack.isEmpty() &&
		 * !EnchantmentHelper.hasVanishingCurse(itemstack)) {
		 * this.entityDropItem(itemstack); } } }
		 */
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
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			if (sourceentity.isSecondaryUseActive()) {
				if (sourceentity instanceof ServerPlayerEntity) {
					NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new StringTextComponent("Rocket");
						}

						@Override
						public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
							PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
							packetBuffer.writeBlockPos(new BlockPos(sourceentity.getPosition()));
							packetBuffer.writeByte(0);
							packetBuffer.writeVarInt(CustomEntity.this.getEntityId());
							return new RocketTier2GuiFuelGui.GuiContainerMod(id, inventory, packetBuffer);
						}
					}, buf -> {
						buf.writeBlockPos(new BlockPos(sourceentity.getPosition()));
						buf.writeByte(0);
						buf.writeVarInt(this.getEntityId());
					});
				}
				return ActionResultType.func_233537_a_(this.world.isRemote());
			}
			super.func_230254_b_(sourceentity, hand);
			sourceentity.startRiding(this);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			return retval;
		}

		@Override
		public void baseTick() {
			super.baseTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				RocketOnEntityTicktier2Procedure.executeProcedure($_dependencies);
			}
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new RocketSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("Animation")));
			// new Nbt
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new RocketSpin2Packet(this.getEntityId(), this.getPersistentData().getDouble("AnimationPitch")));
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
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools2", "rocket_link2"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), RocketSpinPacket.class, RocketSpinPacket::encode, RocketSpinPacket::decode, RocketSpinPacket::handle);
			// new animationpitch
			INSTANCE.registerMessage(nextID(), RocketSpin2Packet.class, RocketSpin2Packet::encode, RocketSpin2Packet::decode,
					RocketSpin2Packet::handle);
		}
	}

	// First Animation
	private static class RocketSpinPacket {
		private double animation;
		private int entityId;
		public RocketSpinPacket(int entityId, double animation) {
			this.animation = animation;
			this.entityId = entityId;
		}

		public static void encode(RocketSpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.animation);
		}

		public static RocketSpinPacket decode(PacketBuffer buf) {
			return new RocketSpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(RocketSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("Animation", msg.animation);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}

	// new animationpitch
	private static class RocketSpin2Packet {
		private double animationpitch;
		private int entityId;
		public RocketSpin2Packet(int entityId, double animationpitch) {
			this.animationpitch = animationpitch;
			this.entityId = entityId;
		}

		public static void encode(RocketSpin2Packet msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.animationpitch);
		}

		public static RocketSpin2Packet decode(PacketBuffer buf) {
			return new RocketSpin2Packet(buf.readInt(), buf.readDouble());
		}

		public static void handle(RocketSpin2Packet msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("AnimationPitch", msg.animationpitch);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
