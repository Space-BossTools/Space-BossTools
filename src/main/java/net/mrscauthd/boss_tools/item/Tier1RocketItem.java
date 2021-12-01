package net.mrscauthd.boss_tools.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;

public class Tier1RocketItem extends RocketAbstractFuelItem {

	public Tier1RocketItem(Properties properties) {
		super(properties);
	}

	@Override
	public EntityType<? extends RocketAbstractEntity> getRocketEntityType() {
		return ModInnet.TIER_1_ROCKET.get();
	}

	@Override
	public int getTier(@Nullable RocketAbstractEntity rocket) {
		return 1;
	}
}
