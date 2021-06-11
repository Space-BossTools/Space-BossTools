
package net.mrscauthd.boss_tools.entity;

import net.mrscauthd.boss_tools.procedures.RoverOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.procedures.RoverEntityIsHurtProcedure;
import net.mrscauthd.boss_tools.item.RoverItemItem;
import net.mrscauthd.boss_tools.gui.Rover1GUIGui;
import net.mrscauthd.boss_tools.entity.renderer.RoverRenderer;
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
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.MathHelper;
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
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

@BossToolsModElements.ModElement.Tag
public class RoverEntity extends BossToolsModElements.ModElement {
	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire()
			.size(2.5f, 1.0f)).build("rover").setRegistryName("rover");
	public static double speed = 0;
	public static boolean fw = false;
	public static float forward = 0;
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

	// packages System
	private static class NetworkLoader {
		public static SimpleChannel INSTANCE;
		private static int id = 1;
		public static int nextID() {
			return id++;
		}

		public static void registerMessages() {
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "rover_link"), () -> "100.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), RotationSpinPacket.class, RotationSpinPacket::encode, RotationSpinPacket::decode,
					RotationSpinPacket::handle);
			// new animationpitch
			INSTANCE.registerMessage(nextID(), WheelSpinPacket.class, WheelSpinPacket::encode, WheelSpinPacket::decode, WheelSpinPacket::handle);
			// fuel
			INSTANCE.registerMessage(nextID(), FuelSpinPacket.class, FuelSpinPacket::encode, FuelSpinPacket::decode, FuelSpinPacket::handle);
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

	// Animation
	private static class FuelSpinPacket {
		private double fuel;
		private int entityId;
		public FuelSpinPacket(int entityId, double fuel) {
			this.fuel = fuel;
			this.entityId = entityId;
		}

		public static void encode(FuelSpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.fuel);
		}

		public static FuelSpinPacket decode(PacketBuffer buf) {
			return new FuelSpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(FuelSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("fuel", msg.fuel);
				}
			});
			ctx.get().setPacketHandled(true);
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
		// @Override
		// public boolean func_241845_aY() {
		// return true;
		// }
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
					//wheel = 0;
					this.setAIMoveSpeed(0f);
				}
			}
			super.removePassenger(passenger);
		}

		@Override
		public double getMountedYOffset() {
			return super.getMountedYOffset() + -0.4;
		}
		// public float getDamageTaken() {
		// return 0;
		// }
		// test end
		// Inv
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

		// inv End
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

		// step sound
		@Override
		public void playStepSound(BlockPos pos, BlockState blockIn) {
			this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")), 0.0f, 0);
		}

		// end
		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			Entity sourceentity = source.getTrueSource();
			if (sourceentity != null) {
				if (sourceentity.isSneaking() == true) {
					for (int i = 0; i < inventory.getSlots(); ++i) {
						ItemStack itemstack = inventory.getStackInSlot(i);
						if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
							this.entityDropItem(itemstack);
						}
					}
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					$_dependencies.put("world", world);
					RoverEntityIsHurtProcedure.executeProcedure($_dependencies);
				}
			}
			if (sourceentity == null && entity.isBeingRidden() == false) {
				for (int i = 0; i < inventory.getSlots(); ++i) {
					ItemStack itemstack = inventory.getStackInSlot(i);
					if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
						this.entityDropItem(itemstack);
					}
				}
				ItemStack itemfuel = ItemStack.EMPTY;
				itemfuel = new ItemStack(RoverItemItem.block, (int) (1));
				(itemfuel).getOrCreateTag().putDouble("Rocketfuel", (entity.getPersistentData().getDouble("Rocketfuel")));
				(itemfuel).getOrCreateTag().putDouble("fuel", (entity.getPersistentData().getDouble("fuel")));
				(itemfuel).getOrCreateTag().putDouble("fuelgui", ((entity.getPersistentData().getDouble("fuel")) / 160));
				if (world instanceof World && !world.isRemote()) {
					ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, (itemfuel));
					entityToSpawn.setPickupDelay((int) 10);
					world.addEntity(entityToSpawn);
				}
				this.remove();
			}
			if (source == DamageSource.FALLING_BLOCK)
				return false;
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

		// inv open
		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			if (sourceentity.isSecondaryUseActive()) {
				if (sourceentity instanceof ServerPlayerEntity) {
					NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new StringTextComponent("Rover");
						}

						@Override
						public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
							PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
							packetBuffer.writeBlockPos(new BlockPos(sourceentity.getPosition()));
							packetBuffer.writeByte(0);
							packetBuffer.writeVarInt(CustomEntity.this.getEntityId());
							return new Rover1GUIGui.GuiContainerMod(id, inventory, packetBuffer);
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

		// inv open end
		@Override
		public void baseTick() {
			super.baseTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			Entity entity2 = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
/*			//movement
			float forward = 0;
			if (this.isBeingRidden()) {
				forward = ((LivingEntity) entity2).moveForward;
			}
			if (this.getPersistentData().getDouble("fuel") != 0) {
				if (this.getPersistentData().getDouble("fuel") >= 1) {
					if (forward >= 0.01) {
						if (speed <= 0.26) {
							speed = speed + 0.015;
							System.out.println("1");
							this.setAIMoveSpeed((float) speed);
							this.getPersistentData().putDouble("Wheel",  speed);
						}
					}
					if (forward <= -0.01) {
						if (speed >= -0.08) {
							speed = speed - 0.015;
							System.out.println("2");
							this.setAIMoveSpeed((float) - speed); //-
							this.getPersistentData().putDouble("Wheel",  speed);
						}
					}
				}
			}
			if (forward == 0) {
				if (speed != 0) {
					if (speed >= 0.008) {
						speed = speed - 0.015;
						System.out.println("3");
						this.setAIMoveSpeed((float) - speed); //-
						this.getPersistentData().putDouble("Wheel",  speed);
					}
					if (speed <= -0.008) {
						speed = speed + 0.015;
						System.out.println("4");
						this.setAIMoveSpeed((float) speed);
						this.getPersistentData().putDouble("Wheel",  speed);
					}
					System.out.println(speed);
				}
			}
			//}
		*/	// movement end
			//test 2
			if (entity2 instanceof LivingEntity) {
				forward = ((LivingEntity) entity2).moveForward;
			}
			if (forward >= 0.01) {
				//fw = true;
				this.getPersistentData().putDouble("Wheel", 1);
			}
			if (forward <= -0.01) {
				//fw = false;
				this.getPersistentData().putDouble("Wheel", 0);
			}
			//test2
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				RoverOnEntityTickUpdateProcedure.executeProcedure($_dependencies);
			}
			// fall damage
			// entity.getAlwaysRenderNameTagForRender();
			this.fallDistance = (float) (0);
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new RotationSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("Rotation")));
			// new Nbt
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new WheelSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("Wheel")));
			// new Nbt
			if (!this.world.isRemote)
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
						new FuelSpinPacket(this.getEntityId(), this.getPersistentData().getDouble("fuel")));
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
						if (this.getPersistentData().getDouble("fuel") >= 1) {
							if (this.getAIMoveSpeed() >= 0.01) {
								// wheel
								if (speed <= 0.32) {
									speed = speed + 0.02;
								}
							}
							if (this.getAIMoveSpeed() <= 0.25) {
								this.setAIMoveSpeed(this.getAIMoveSpeed() + (float) 0.02);
								// System.out.println(this.getAIMoveSpeed());
							}
						}
						if (forward == 0 || this.getPersistentData().getDouble("fuel") <= 1) {
							this.setAIMoveSpeed(0f);
							// wheel
							if (speed != 0) {
								if (speed >= 0) {
									speed = speed - 0.02;
								}
							}
						}
						//this.getPersistentData().putDouble("Wheel", this.getPersistentData().getDouble("Wheel") + speed);// 0.35
						super.travel(new Vector3d(strafe, 0, forward));
					}
					if (forward <= -0.01) {
						// System.out.println(forward + "test2");
						speed = 0;
						float strafe = 0;
						if (this.getPersistentData().getDouble("fuel") >= 1) {
							if (this.getAIMoveSpeed() >= 0.01) {
						//		this.getPersistentData().putDouble("Wheel", this.getPersistentData().getDouble("Wheel") - 0.08);
							}
							if (this.getAIMoveSpeed() <= 0.04) { // weil es ja erst gemacht werden muss das ist nur der
								// block
								this.setAIMoveSpeed(this.getAIMoveSpeed() + (float) 0.02);
							}
						}
						if (this.getAIMoveSpeed() >= 0.08) { // FIX
							this.setAIMoveSpeed((float) 0);
						}
						if (forward == 0 || this.getPersistentData().getDouble("fuel") <= 1) {
							this.setAIMoveSpeed(0f);
							// wheel = 0;
						}
						super.travel(new Vector3d(strafe, 0, forward));
					}
					if (this.getPersistentData().getDouble("fuel") == 2) {
						if (entity instanceof PlayerEntity) {
							((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cNO FUEL!"), (true));
							}
						}
					if (this.getPersistentData().getDouble("fuel") >= 1) {
						if (forward <= -0.01) {
							this.getPersistentData().putDouble("fuel", this.getPersistentData().getDouble("fuel") - 2);
						}
						if (forward >= 0.01) {
							this.getPersistentData().putDouble("fuel", this.getPersistentData().getDouble("fuel") - 2);
						}
					}
				}
				if (this.getPersistentData().getDouble("Wheel") == 1) {
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.getPosX() - this.prevPosX;
				double d0 = this.getPosZ() - this.prevPosZ;
				float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
				this.limbSwing += this.limbSwingAmount;
				}
				if (this.getPersistentData().getDouble("Wheel") == 0) {
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.getPosX() - this.prevPosX;
				double d0 = this.getPosZ() - this.prevPosZ;
				float f1 = - MathHelper.sqrt(d1 * d1 + d0 * d0) * 8.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.8F;
				this.limbSwing += this.limbSwingAmount;
				}
				return;
			} else {
				// wheel = 0;
			}
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(dir);
		}
	}
}