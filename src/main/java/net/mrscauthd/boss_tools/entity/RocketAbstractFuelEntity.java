package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui.GuiContainer;

public abstract class RocketAbstractFuelEntity extends RocketAbstractEntity {

	public static final DataParameter<Integer> BUCKETS = EntityDataManager.createKey(RocketAbstractFuelEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> FUEL = EntityDataManager.createKey(RocketAbstractFuelEntity.class, DataSerializers.VARINT);

	public RocketAbstractFuelEntity(EntityType<? extends RocketAbstractFuelEntity> type, World world) {
		super(type, world);

		this.dataManager.register(BUCKETS, 0);
		this.dataManager.register(FUEL, 0);
	}

	public abstract int getBucketsOfFull();

	public abstract int getFuelPerBucket();

	@Override
	public void tryRocketStart(PlayerEntity player) {
		if (this.getFuel() == this.getFuelOfFull()) {
			super.tryRocketStart(player);
			Methodes.RocketSounds(player.getRidingEntity(), world);
		} else {
			Methodes.noFuelMessage(player);
		}
	}

	@Override
	protected GuiContainer createMenu(int id, PlayerInventory inventory) {
		return new RocketGui.GuiContainer(id, inventory, this);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);

		compound.putInt("buckets", this.getBuckets());
		compound.putInt("fuel", this.getFuel());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setBuckets(compound.getInt("buckets"));
		this.setFuel(compound.getInt("fuel"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.loadupFuel();
	}

	protected void loadupFuel() {
		// Fuel Load up
		int fuel = this.getFuel();
		int buckets = this.getBuckets();
		int bucketOfFull = this.getBucketsOfFull();
		int fuelOfBuckets = buckets * this.getFuelPerBucket();

		if (fuel == fuelOfBuckets) {
			if (buckets < bucketOfFull) {
				IItemHandlerModifiable itemHandler = this.getItemHandler();
				ItemStack itemstack = itemHandler.getStackInSlot(0);

				if (this.testBucket(itemstack)) {
					itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
					buckets++;
					this.setBuckets(buckets);
				}
			}
		} else if (fuel < fuelOfBuckets) {
			fuel++;
			this.setFuel(fuel);
		}
	}

	public boolean testBucket(ItemStack itemstack) {
		return this.testBucket(itemstack.getItem());
	}

	private boolean testBucket(Item item) {
		return Methodes.tagCheck(FluidUtil2.findBucketFluid(item), ModInnet.FLUID_VEHICLE_FUEL_TAG);
	}

	public int getFuel() {
		return this.getDataManager().get(FUEL);
	}

	public void setFuel(int fuel) {
		fuel = MathHelper.clamp(fuel, 0, this.getFuelOfFull());
		this.getDataManager().set(FUEL, fuel);
	}

	public int getFuelOfFull() {
		return this.getBucketsOfFull() * this.getFuelPerBucket();
	}

	public int getBuckets() {
		return this.getDataManager().get(BUCKETS);
	}

	public void setBuckets(int buckets) {
		buckets = MathHelper.clamp(buckets, 0, this.getBucketsOfFull());
		this.getDataManager().set(BUCKETS, buckets);
	}
}
