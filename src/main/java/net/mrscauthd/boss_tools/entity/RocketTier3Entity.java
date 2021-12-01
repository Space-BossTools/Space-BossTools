package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.item.RocketAbstractItem;

public class RocketTier3Entity extends RocketAbstractFuelEntity {

	public static final int FUEL_BUCKETS = 3;

	public RocketTier3Entity(EntityType<? extends RocketTier3Entity> type, World world) {
		super(type, world);
	}

	@Override
	public RocketAbstractItem getRocketItem() {
		return ModInnet.TIER_3_ROCKET_ITEM.get();
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.65D;
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