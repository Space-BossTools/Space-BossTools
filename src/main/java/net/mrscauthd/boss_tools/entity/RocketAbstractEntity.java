package net.mrscauthd.boss_tools.entity;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.RocketGUI;

public abstract class RocketAbstractEntity extends CreatureEntity implements INamedContainerProvider, IVehicleEntity {

	public static final String KEY_ROCKET_START = "rocket_start";
	public static final String KEY_FUEL_LOADING = "fuel_loading";
	public static final String KEY_FUEL = "fuel";
	public static final String KEY_START_TIMER = "start_timer";

	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final DataParameter<Boolean> ROCKET_START = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Integer> FUEL_LOADING = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> FUEL = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> START_TIMER = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.VARINT);

	public RocketAbstractEntity(EntityType<? extends RocketAbstractEntity> type, World world) {
		super(type, world);
		this.dataManager.register(ROCKET_START, false);
		this.dataManager.register(FUEL_LOADING, 0);
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

	public abstract Item getItem();

	public ItemStack createItemStack() {
		ItemStack itemStack = new ItemStack(this.getItem());
		this.writeItemStackData(itemStack.getOrCreateTag());
		return itemStack;
	}

	public void readItemStackData(CompoundNBT compound) {
		this.setFuelLoading(compound.getInt(KEY_FUEL_LOADING));
		this.setFuel(compound.getInt(KEY_FUEL));
	}

	public void writeItemStackData(CompoundNBT compound) {
		int fuelLoading = this.getFuelLoading();

		if (fuelLoading > 0) {
			compound.putInt(KEY_FUEL_LOADING, fuelLoading);
		}

		int fuel = this.getFuel();

		if (fuel > 0) {
			compound.putInt(KEY_FUEL, fuel);
		}

	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return this.createItemStack();
	}

	@Override
	public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
		return null;
	}

	@Override
	public net.minecraft.util.SoundEvent getDeathSound() {
		return null;
	}

	// Save Entity Dismount
	@Override
	public Vector3d func_230268_c_(LivingEntity livingEntity) {
		Vector3d[] avector3d = new Vector3d[] { func_233559_a_((double) this.getWidth(), (double) livingEntity.getWidth(), livingEntity.rotationYaw), func_233559_a_((double) this.getWidth(), (double) livingEntity.getWidth(), livingEntity.rotationYaw - 22.5F), func_233559_a_((double) this.getWidth(), (double) livingEntity.getWidth(), livingEntity.rotationYaw + 22.5F), func_233559_a_((double) this.getWidth(), (double) livingEntity.getWidth(), livingEntity.rotationYaw - 45.0F), func_233559_a_((double) this.getWidth(), (double) livingEntity.getWidth(), livingEntity.rotationYaw + 45.0F) };
		Set<BlockPos> set = Sets.newLinkedHashSet();
		double d0 = this.getBoundingBox().maxY;
		double d1 = this.getBoundingBox().minY - 0.5D;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (Vector3d vector3d : avector3d) {
			blockpos$mutable.setPos(this.getPosX() + vector3d.x, d0, this.getPosZ() + vector3d.z);

			for (double d2 = d0; d2 > d1; --d2) {
				set.add(blockpos$mutable.toImmutable());
				blockpos$mutable.move(Direction.DOWN);
			}
		}

		for (BlockPos blockpos : set) {
			if (!this.world.getFluidState(blockpos).isTagged(FluidTags.LAVA)) {
				double d3 = this.world.func_242403_h(blockpos);
				if (TransportationHelper.func_234630_a_(d3)) {
					Vector3d vector3d1 = Vector3d.copyCenteredWithVerticalOffset(blockpos, d3);

					for (Pose pose : livingEntity.getAvailablePoses()) {
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
	protected void dropInventory() {
		super.dropInventory();

		for (int i = 0; i < inventory.getSlots(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
				this.entityDropItem(itemstack);
			}
		}

		this.entityDropItem(this.createItemStack());
	}

	@Override
	public void onKillCommand() {
		this.onDeath(DamageSource.OUT_OF_WORLD);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity sourceentity = source.getTrueSource();

		if (!source.isProjectile() && sourceentity != null && sourceentity.isSneaking() && !this.isBeingRidden()) {
			this.onDeath(DamageSource.OUT_OF_WORLD);
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

	public IItemHandlerModifiable getInventory() {
		return this.inventory;
	}

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

		compound.putBoolean(KEY_ROCKET_START, this.isRocketStart());
		compound.putInt(KEY_FUEL_LOADING, this.getFuelLoading());
		compound.putInt(KEY_FUEL, this.getFuel());
		compound.putInt(KEY_START_TIMER, this.getStartTimer());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		INBT inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundNBT) {
			inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}

		this.setRocketStart(compound.getBoolean(KEY_ROCKET_START));
		this.setFuelLoading(compound.getInt(KEY_FUEL_LOADING));
		this.setFuel(compound.getInt(KEY_FUEL));
		this.setStartTimer(compound.getInt(KEY_START_TIMER));
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
		return new RocketGUI.GuiContainerMod(id, inventory, this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getPosX();
		double y = this.getPosY();
		double z = this.getPosZ();

		if (this.isRocketStart()) {

			// Rocket Animation
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

			int timerTarget = 200;

			if (this.getStartTimer() < timerTarget) {
				this.setStartTimer(this.getStartTimer() + 1);
			}

			if (this.getStartTimer() == timerTarget) {
				if (this.getMotion().getY() < 0.5) {
					this.setMotion(this.getMotion().getX(), this.getMotion().getY() + 0.1, this.getMotion().getZ());
				} else {
					this.setMotion(this.getMotion().getX(), 0.63, this.getMotion().getZ());
				}
			}

			if (y > 600 && this.getPassengers().isEmpty() == false) {
				Entity pass = this.getPassengers().get(0);

				pass.getPersistentData().putDouble(this.getGUISystemKey(), 1); // TODO Remove it if you Reworked the GUI SYSTEM
				pass.getPersistentData().putDouble("Player_movement", 1); // TODO Remove it if you Reworked the GUI SYSTEM

				this.remove();
			} else if (y > 600 && this.getPassengers().isEmpty() == true) {
				this.remove();
			}

			// Particle Spawn
			if (this.getStartTimer() == timerTarget) {
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

		this.putFuelLoading();
		this.loadFuel();

		if (this.isOnGround() || this.isInWater()) {

			BlockState state = world.getBlockState(new BlockPos(Math.floor(x), y - 0.1, Math.floor(z)));

			if (!world.isAirBlock(new BlockPos(Math.floor(x), y - 0.01, Math.floor(z))) && state.getBlock() instanceof RocketLaunchPadBlock.CustomBlock && state.get(RocketLaunchPadBlock.CustomBlock.STAGE) == false || world.getBlockState(new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z))).getBlock() != RocketLaunchPadBlock.block.getDefaultState().getBlock()) {
				this.onDeath(DamageSource.OUT_OF_WORLD);
			}

		}

	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);
		this.remove();
	}

	protected void loadFuel() {
		int loadingSpeed = this.getFuelLoadingSpeed();
		int fuelLoading = this.getFuelLoading();

		if (fuelLoading > 0) {
			int loading = Math.min(fuelLoading, loadingSpeed);
			this.setFuelLoading(fuelLoading - loading);
			this.setFuel(this.getFuel() + loading);
		}
	}

	protected void putFuelLoading() {
		IItemHandlerModifiable inventory = this.getInventory();
		int fuelLoading = this.getFuelLoading();
		int willFuel = this.getFuel() + fuelLoading;
		int fuelCapacity = this.getFuelCapacity();

		if (willFuel < fuelCapacity) {
			ItemStack itemStack = inventory.getStackInSlot(0);

			if (FluidUtil2.canDrain(itemStack, ModInnet.FUEL_STILL.get())) {
				if (itemStack.getItem() == ModInnet.FUEL_BUCKET.get()) {
					inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
					this.setFuelLoading(fuelLoading + 1000);
				} else {
					IFluidHandlerItem fluidHandler = FluidUtil2.getItemStackFluidHandler(itemStack);

					if (fluidHandler != null) {
						int loadingSpeed = this.getFuelLoadingSpeed();
						int extract = Math.min(fuelCapacity - willFuel, loadingSpeed);

						if (fluidHandler.drain(extract, FluidAction.SIMULATE).getAmount() == extract) {
							fluidHandler.drain(extract, FluidAction.EXECUTE);
							this.setFuelLoading(fuelLoading + extract);
						}
					}
				}
			}
		}
	}

	public int getFuelLoadingSpeed() {
		return 10;
	}

	public void startLaunch() {
		this.setRocketStart(true);
		Methodes.RocketSounds(this, this.world);
	}

	public void rotation(int amount) {
		this.rotationYaw = this.rotationYaw + amount;
		this.setRenderYawOffset(this.rotationYaw);
		this.prevRotationYaw = this.rotationYaw;
		this.prevRenderYawOffset = this.rotationYaw;
	}

	public int getStartTimer() {
		return this.dataManager.get(START_TIMER);
	}

	public void setStartTimer(int startTimer) {
		this.dataManager.set(START_TIMER, Math.max(startTimer, 0));
	}

	public boolean isRocketStart() {
		return this.dataManager.get(ROCKET_START);
	}

	public void setRocketStart(boolean rocketStart) {
		this.dataManager.set(ROCKET_START, rocketStart);
	}

	public void setFuelLoading(int fuelLoading) {
		fuelLoading = MathHelper.clamp(fuelLoading, 0, this.getFuelCapacity());
		this.dataManager.set(FUEL_LOADING, fuelLoading);
	}

	public int getFuelLoading() {
		return this.dataManager.get(FUEL_LOADING);
	}

	public void setFuel(int fuel) {
		fuel = MathHelper.clamp(fuel, 0, this.getFuelCapacity());
		this.dataManager.set(FUEL, fuel);
	}

	public int getFuel() {
		return this.dataManager.get(FUEL);
	}

	public boolean isFuelFull() {
		return this.getFuel() == this.getFuelCapacity();
	}

	public abstract int getFuelCapacity();

	public abstract String getGUISystemKey();

}