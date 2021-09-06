package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.events.Methodes;

import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.enchantment.EnchantmentHelper;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import io.netty.buffer.Unpooled;
import net.mrscauthd.boss_tools.gui.screens.RocketGUI;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;

import java.util.Set;

public class RocketTier2Entity extends CreatureEntity {
	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final DataParameter<Boolean> ROCKET_START = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Integer> BUCKETS = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> FUEL = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> START_TIMER = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);


	public RocketTier2Entity(EntityType type, World world) {
		super(type, world);
		this.dataManager.register(ROCKET_START, false);
		this.dataManager.register(BUCKETS, 0);
		this.dataManager.register(FUEL, 0);
		this.dataManager.register(START_TIMER, 0);
		enablePersistence();
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean canBeLeashedTo(PlayerEntity player) {
		return false;
	}

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
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(Tier2RocketItemItem.block);
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() -2.1;
	}

	@Override
	public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
		return null;
	}

	@Override
	public net.minecraft.util.SoundEvent getDeathSound() {
		return null;
	}

	//Save Entity Dismount
	@Override
	public Vector3d func_230268_c_(LivingEntity livingEntity) {
		Vector3d[] avector3d = new Vector3d[]{func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw - 22.5F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw + 22.5F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw - 45.0F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw + 45.0F)};
		Set<BlockPos> set = Sets.newLinkedHashSet();
		double d0 = this.getBoundingBox().maxY;
		double d1 = this.getBoundingBox().minY - 0.5D;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(Vector3d vector3d : avector3d) {
			blockpos$mutable.setPos(this.getPosX() + vector3d.x, d0, this.getPosZ() + vector3d.z);

			for(double d2 = d0; d2 > d1; --d2) {
				set.add(blockpos$mutable.toImmutable());
				blockpos$mutable.move(Direction.DOWN);
			}
		}

		for(BlockPos blockpos : set) {
			if (!this.world.getFluidState(blockpos).isTagged(FluidTags.LAVA)) {
				double d3 = this.world.func_242403_h(blockpos);
				if (TransportationHelper.func_234630_a_(d3)) {
					Vector3d vector3d1 = Vector3d.copyCenteredWithVerticalOffset(blockpos, d3);

					for(Pose pose : livingEntity.getAvailablePoses()) {
						AxisAlignedBB axisalignedbb = livingEntity.getPoseAABB(pose);
						if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d1))) {
							livingEntity.setPose(pose);
							return vector3d1;
						}
					}
				}
			}
		}

		return new Vector3d(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
	}

	@Override
	public void onKillCommand() {
		double x = this.getPosX();
		double y = this.getPosY();
		double z = this.getPosZ();

		//Drop Inv
		for (int i = 0; i < inventory.getSlots(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
				this.entityDropItem(itemstack);
			}
		}

		//Spawn Rocket Item
		ItemStack item = new ItemStack(Tier2RocketItemItem.block,1);

		if (world instanceof World && !world.isRemote()) {
			ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, item);
			entityToSpawn.setPickupDelay(10);
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
		Entity sourceentity = source.getTrueSource();

		if (!source.isProjectile() && sourceentity != null && sourceentity.isSneaking() && !this.isBeingRidden()) {

			//Drop Rocket Item
			if (!world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, new ItemStack(Tier2RocketItemItem.block, 1));
				entityToSpawn.setPickupDelay(10);
				world.addEntity(entityToSpawn);
			}

			//Drop Inv
			for (int i = 0; i < inventory.getSlots(); ++i) {
				ItemStack itemstack = inventory.getStackInSlot(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.entityDropItem(itemstack);
				}
			}

			//Remove Entity
			if (!this.world.isRemote())
				this.remove();

		}
		return false;
	}

	private final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public int getSlotLimit(int slot) {
			return 64;
		}
	};

	private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
		if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null) {
			return LazyOptional.of(() -> combined).cast();
		}
		return super.getCapability(capability, side);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("InventoryCustom", inventory.serializeNBT());

		compound.putBoolean("rocket_start", this.dataManager.get(ROCKET_START));
		compound.putInt("buckets", this.dataManager.get(BUCKETS));
		compound.putInt("fuel", this.dataManager.get(FUEL));
		compound.putInt("start_timer", this.dataManager.get(START_TIMER));
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		INBT inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundNBT) {
			inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}

		this.dataManager.set(ROCKET_START, compound.getBoolean("rocket_start"));
		this.dataManager.set(BUCKETS, compound.getInt("buckets"));
		this.dataManager.set(FUEL, compound.getInt("fuel"));
		this.dataManager.set(START_TIMER, compound.getInt("start_timer"));
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
		super.func_230254_b_(sourceentity, hand);
		ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());

		if (sourceentity instanceof ServerPlayerEntity && sourceentity.isSneaking()) {
			NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return new StringTextComponent("Tier 2 Rocket");
				}

				@Override
				public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
					PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
					packetBuffer.writeBlockPos(new BlockPos(sourceentity.getPosition()));
					packetBuffer.writeByte(0);
					packetBuffer.writeVarInt(RocketTier2Entity.this.getEntityId());
					return new RocketGUI.GuiContainerMod(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeBlockPos(new BlockPos(sourceentity.getPosition()));
				buf.writeByte(0);
				buf.writeVarInt(this.getEntityId());
			});

			return retval;
		}

		sourceentity.startRiding(this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getPosX();
		double y = this.getPosY();
		double z = this.getPosZ();

		if (this.dataManager.get(ROCKET_START) == true) {

			//Rocket Animation
			ar = ar + 1;
			if (ar == 1) {
				ay = ay + 0.006;
				ap = ap + 0.006;
			}
			if (ar == 2) {
				ar = 0;
				ay = 0;
				ap = 0;
			}

			if (this.dataManager.get(START_TIMER) < 200) {
				this.dataManager.set(START_TIMER, this.dataManager.get(START_TIMER) + 1);
			}

			if (this.dataManager.get(START_TIMER) == 200) {
				if (this.getMotion().getY() < 0.5) {
					this.setMotion(this.getMotion().getX(), this.getMotion().getY() + 0.1, this.getMotion().getZ());
				} else {
					this.setMotion(this.getMotion().getX(), 0.63, this.getMotion().getZ());
				}
			}

			if (y > 600 && this.getPassengers().isEmpty() == false) {
				Entity pass = this.getPassengers().get(0);

				pass.getPersistentData().putDouble("Tier_2_open_main_menu", 1); //TODO Remove it if you Reworked the GUI SYSTEM
				pass.getPersistentData().putDouble("Player_movement", 1); //TODO Remove it if you Reworked the GUI SYSTEM

				this.remove();
			} else if (y > 600 && this.getPassengers().isEmpty() == true)  {
				this.remove();
			}

			//Particle Spawn
			if (this.dataManager.get(START_TIMER) == 200) {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, ParticleTypes.FLAME, true, this.getPosX(), this.getPosY() - 2.2, this.getPosZ(), 100, 0.1, 0.1, 0.1, 0.001);
						((ServerWorld) world).spawnParticle(p, ParticleTypes.SMOKE, true, this.getPosX(), this.getPosY() - 3.2, this.getPosZ(), 50, 0.1, 0.1, 0.1, 0.04);
					}
				}
			} else {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getPosX(), this.getPosY() - 0.5, this.getPosZ(), 6, 0.1, 0.1, 0.1, 0.013);
					}
				}
			}

		}

		//Fuel Load up
		if (this.inventory.getStackInSlot(0).getItem() == ModInnet.FUEL_BUCKET.get() && this.dataManager.get(BUCKETS) < 3) {
			this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
			this.getDataManager().set(BUCKETS, this.getDataManager().get(BUCKETS) + 1);
		}

		if (this.dataManager.get(BUCKETS) == 1 && this.dataManager.get(FUEL) < 100) {
			this.getDataManager().set(FUEL, this.dataManager.get(FUEL) + 1);

		} else if (this.dataManager.get(BUCKETS) == 2 && this.dataManager.get(FUEL) < 200) {
			this.getDataManager().set(FUEL, this.dataManager.get(FUEL) + 1);

		} else if (this.dataManager.get(BUCKETS) == 3 && this.dataManager.get(FUEL) < 300) {
			this.getDataManager().set(FUEL, this.dataManager.get(FUEL) + 1);
		}

		if (this.isOnGround() || this.isInWater()) {

			BlockState state = world.getBlockState(new BlockPos(Math.floor(x), y - 0.1, Math.floor(z)));

			if (!world.isAirBlock(new BlockPos(Math.floor(x), y - 0.01, Math.floor(z)))
					&& state.getBlock() instanceof RocketLaunchPadBlock.CustomBlock
					&& state.get(RocketLaunchPadBlock.CustomBlock.STAGE) == false
					|| world.getBlockState(new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z))).getBlock() != RocketLaunchPadBlock.block.getDefaultState().getBlock()) {

				//Drop Inv
				for (int i = 0; i < inventory.getSlots(); ++i) {
					ItemStack itemstack = inventory.getStackInSlot(i);
					if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
						this.entityDropItem(itemstack);
					}
				}

				//Spawn Rocket Item //TODO In Future add FUEL SYSTEM
				ItemStack item = new ItemStack(Tier2RocketItemItem.block,1);

				if (world instanceof World && !world.isRemote()) {
					ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, item);
					entityToSpawn.setPickupDelay(10);
					world.addEntity(entityToSpawn);
				}
				this.remove();

			}

		}

	}
}