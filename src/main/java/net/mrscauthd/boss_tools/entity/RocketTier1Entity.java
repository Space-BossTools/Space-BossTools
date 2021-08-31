package net.mrscauthd.boss_tools.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.gui.RocketTier1GUIFuelGui;

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

public class RocketTier1Entity extends CreatureEntity {
	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final DataParameter<Boolean> ROCKET_START = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> BUCKET = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Integer> FUEL = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> START_TIMER = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);


	public RocketTier1Entity(EntityType type, World world) {
		super(type, world);
		this.dataManager.register(ROCKET_START, false);
		this.dataManager.register(BUCKET, false);
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
		return new ItemStack(Tier1RocketItemItem.block);
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() + -1.7;
	}

	@Override
	public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
		return null;
	}

	@Override
	public net.minecraft.util.SoundEvent getDeathSound() {
		return null;
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
		ItemStack item = new ItemStack(Tier1RocketItemItem.block,1);

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
			//Stop Rocket Sound
			SoundCategory soundCategory = SoundCategory.NEUTRAL;
			SStopSoundPacket sstopsoundpacket = new SStopSoundPacket(new ResourceLocation("boss_tools:rocketfly"), soundCategory);

			if (sourceentity instanceof ServerPlayerEntity) {
				((ServerPlayerEntity) sourceentity).connection.sendPacket(sstopsoundpacket);
			}

			//Drop Rocket Item
			if (!world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, new ItemStack(Tier1RocketItemItem.block, 1));
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
		compound.putBoolean("bucket", this.dataManager.get(BUCKET));
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
		this.dataManager.set(BUCKET, compound.getBoolean("bucket"));
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
					return new StringTextComponent("Tier 1 Rocket");
				}

				@Override
				public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
					PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
					packetBuffer.writeBlockPos(new BlockPos(sourceentity.getPosition()));
					packetBuffer.writeByte(0);
					packetBuffer.writeVarInt(RocketTier1Entity.this.getEntityId());
					return new RocketTier1GUIFuelGui.GuiContainerMod(id, inventory, packetBuffer);
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

			if (y > 625 && this.getPassengers() != null) {
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

		if (this.inventory.getStackInSlot(0).getItem() == ModInnet.FUEL_BUCKET.get() && this.dataManager.get(BUCKET) == false) {
			this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
			this.getDataManager().set(BUCKET, true);
		}

		if (this.dataManager.get(BUCKET) == true && this.dataManager.get(FUEL) < 300) {
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

				//Spawn Rocket Item
				ItemStack item = new ItemStack(Tier1RocketItemItem.block,1);

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