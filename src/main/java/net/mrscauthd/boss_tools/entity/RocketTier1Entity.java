package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.item.RocketAbstractItem;

public class RocketTier1Entity extends RocketAbstractFuelEntity {

	public static final int FUEL_BUCKETS = 1;

	public RocketTier1Entity(EntityType<? extends RocketTier1Entity> type, World world) {
		super(type, world);
	}

	@Override
	public RocketAbstractItem getRocketItem() {
		return ModInnet.TIER_1_ROCKET_ITEM.get();
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.35D;
	}

	@Override
	public int getBucketsOfFull() {
		return FUEL_BUCKETS;
	}

	@Override
	public int getFuelPerBucket() {
		return 300;
	}

}