package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.item.RocketAbstractItem;

public class RocketTier2Entity extends RocketAbstractFuelEntity {

	public static final int FUEL_BUCKETS = 3;

	public RocketTier2Entity(EntityType<? extends RocketTier2Entity> type, World world) {
		super(type, world);
	}

	@Override
	public RocketAbstractItem getRocketItem() {
		return ModInnet.TIER_2_ROCKET_ITEM.get();
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.50D;
	}

	@Override
	public int getBucketsOfFull() {
		return FUEL_BUCKETS;
	}

	@Override
	public int getFuelPerBucket() {
		return 100;
	}

}