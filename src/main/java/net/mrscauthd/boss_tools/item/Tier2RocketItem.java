package net.mrscauthd.boss_tools.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;

public class Tier2RocketItem extends RocketAbstractFuelItem {

	public Tier2RocketItem(Properties properties) {
		super(properties);
	}

	@Override
	public EntityType<? extends RocketAbstractEntity> getRocketEntityType() {
		return ModInnet.TIER_2_ROCKET.get();
	}

	@Override
	public int getTier(@Nullable RocketAbstractEntity rocket) {
		return 2;
	}
}
