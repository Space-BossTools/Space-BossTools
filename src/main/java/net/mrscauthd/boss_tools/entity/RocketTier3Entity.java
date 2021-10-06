package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;

public class RocketTier3Entity extends RocketAbstractEntity {

	public static final int FUEL_CAPACITY = 3000;

	public RocketTier3Entity(EntityType<? extends RocketTier3Entity> type, World world) {
		super(type, world);
	}

	@Override
	public Item getItem() {
		return Tier3RocketItemItem.block;
	}

	@Override
	public int getFuelCapacity() {
		return FUEL_CAPACITY;
	}

	@Override
	public String getGUISystemKey() {
		return "Tier_3_open_main_menu";
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.65;
	}

}