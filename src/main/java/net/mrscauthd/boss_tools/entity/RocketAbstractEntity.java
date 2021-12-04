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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui.GuiContainer;
import net.mrscauthd.boss_tools.inventory.ItemHandlerHelper2;
import net.mrscauthd.boss_tools.item.RocketAbstractItem;

public abstract class RocketAbstractEntity extends CreatureEntity implements INamedContainerProvider {

	public static final String SELECTION_GUI_KEY_BUILTIN = new ResourceLocation(BossToolsMod.ModId, "builtin").toString();

	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final DataParameter<Boolean> ROCKET_START = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Integer> START_TIMER = EntityDataManager.createKey(RocketAbstractEntity.class, DataSerializers.VARINT);

	public RocketAbstractEntity(EntityType<? extends RocketAbstractEntity> type, World world) {
		super(type, world);
		this.dataManager.register(ROCKET_START, false);
		this.dataManager.register(START_TIMER, 0);
	}

	public void tryRocketStart(PlayerEntity player) {
		this.setRocketStart(true);
	}

	public abstract RocketAbstractItem getRocketItem();

	protected abstract GuiContainer createMenu(int id, PlayerInventory inventory);

	public GuiContainer createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
		return this.createMenu(id, inventory);
	}

	protected void writeContainerData(PacketBuffer buf) {
		buf.writeVarInt(this.getEntityId());
	}

	public int getTier() {
		return this.getRocketItem().getTier(this);
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
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEFINED;
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public ItemStack getPickedResult(@Nullable RayTraceResult target) {
		RocketAbstractItem item = this.getRocketItem();
		ItemStack itemStack = new ItemStack(item, 1);
		item.fetchItemNBT(itemStack, this);
		itemStack.setDisplayName(this.getCustomName());
		return itemStack;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return null;
	}

	@Override
	public SoundEvent getDeathSound() {
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
	public void onKillCommand() {
		this.dropInventory();
		this.spawnRocketItem();
		this.remove();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity sourceentity = source.getTrueSource();

		if (!source.isProjectile() && sourceentity != null && sourceentity.isSneaking() && !this.isBeingRidden()) {
			if (sourceentity instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) sourceentity;

				if (!playerEntity.isCreative()) {
					this.spawnRocketItem();
				}
			}

			this.dropInventory();
			this.remove();
		}

		return false;
	}

	protected void spawnRocketItem() {
		if (!world.isRemote()) {
			ItemStack itemStack = this.getPickedResult(null);
			ItemEntity entityToSpawn = new ItemEntity(world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack);
			entityToSpawn.setPickupDelay(10);
			world.addEntity(entityToSpawn);
		}
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

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("InventoryCustom", inventory.serializeNBT());

		compound.putBoolean("rocket_start", this.isRocketStart());
		compound.putInt("start_timer", this.getStartTimer());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		INBT inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundNBT) {
			inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}

		this.setRocketStart(compound.getBoolean("rocket_start"));
		this.setStartTimer(compound.getInt("start_timer"));
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
		super.func_230254_b_(sourceentity, hand);

		if (sourceentity instanceof ServerPlayerEntity && sourceentity.isSneaking()) {
			this.openGui((ServerPlayerEntity) sourceentity);
		} else {
			sourceentity.startRiding(this);
		}

		return ActionResultType.func_233537_a_(this.world.isRemote());
	}

	public void openGui(ServerPlayerEntity sourceentity) {
		NetworkHooks.openGui(sourceentity, this, this::writeContainerData);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getPosX();
		double y = this.getPosY();
		double z = this.getPosZ();

		if (this.dataManager.get(ROCKET_START)) {

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

			int maximumStarTimer = this.getMaximumStarTimer();

			if (this.getStartTimer() < maximumStarTimer) {
				this.setStartTimer(this.getStartTimer() + 1);
			}

			if (this.getStartTimer() == maximumStarTimer) {
				Vector3d motion = this.getMotion();
				if (motion.getY() < 0.5) {
					this.setMotion(motion.getX(), motion.getY() + 0.1, motion.getZ());
				} else {
					this.setMotion(motion.getX(), 0.63, motion.getZ());
				}
			}

			if (y > 600) {
				if (!this.getPassengers().isEmpty()) {
					Entity pass = this.getPassengers().get(0);
					this.requestSelectionGuiOpen(pass);
				}

				this.remove();
			}

			// Particle Spawn
			if (this.getStartTimer() == maximumStarTimer) {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, (IParticleData) ModInnet.LARGE_FLAME_PARTICLE.get(), true, this.getPosX(), this.getPosY() - 2.2, this.getPosZ(), 20, 0.1, 0.1, 0.1, 0.001);
						((ServerWorld) world).spawnParticle(p, (IParticleData) ModInnet.SMOKE_PARTICLE.get(), true, this.getPosX(), this.getPosY() - 3.2, this.getPosZ(), 10, 0.1, 0.1, 0.1, 0.04);
					}
				}
			} else {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getPosX(), this.getPosY() - 0.1, this.getPosZ(), 6, 0.1, 0.1, 0.1, 0.023);
					}
				}
			}

		}

		if (this.isOnGround() || this.isInWater()) {

			BlockState state = world.getBlockState(new BlockPos(Math.floor(x), y - 0.1, Math.floor(z)));

			if (!world.isAirBlock(new BlockPos(Math.floor(x), y - 0.01, Math.floor(z))) && state.getBlock() instanceof RocketLaunchPad && !state.get(RocketLaunchPad.STAGE) || world.getBlockState(new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z))).getBlock() != ModInnet.ROCKET_LAUNCH_PAD.get().getDefaultState().getBlock()) {

				this.dropInventory();
				this.spawnRocketItem();
				this.remove();
			}
		}
	}

	public void requestSelectionGuiOpen(Entity pass) {
		ItemStack itemStack = new ItemStack(this.getRocketItem());
		itemStack.setDisplayName(this.getCustomName());
		
		Methodes.setPlanetSelectionGuiKey(pass, this.getSelectionGuiKey());
		Methodes.setPlanetSelectionRocketTier(pass, this.getTier());
		Methodes.setPlanetSelectionRocketItem(pass, itemStack);
		Methodes.setPlanetSelectionItemSlots(pass, ItemHandlerHelper2.getStacks(this.getItemHandler()));
		pass.setNoGravity(true);
	}

	public String getSelectionGuiKey() {
		return RocketAbstractEntity.SELECTION_GUI_KEY_BUILTIN;
	}

	public boolean isRocketStart() {
		return this.getDataManager().get(ROCKET_START);
	}

	public void setRocketStart(boolean rocketStart) {
		this.getDataManager().set(ROCKET_START, rocketStart);
	}

	public int getStartTimer() {
		return this.getDataManager().get(START_TIMER);
	}

	public void setStartTimer(int timer) {
		timer = MathHelper.clamp(timer, 0, this.getMaximumStarTimer());
		this.getDataManager().set(START_TIMER, timer);
	}

	public int getMaximumStarTimer() {
		return 200;
	}

}