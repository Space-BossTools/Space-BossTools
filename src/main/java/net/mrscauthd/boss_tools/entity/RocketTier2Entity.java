package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;

public class RocketTier2Entity extends RocketAbstractEntity {

	public static final int FUEL_CAPACITY = 3000;
	public static final int FUEL_TRANSFER = 10;

	public RocketTier2Entity(EntityType<? extends RocketTier2Entity> type, World world) {
		super(type, world);
	}

	@Override
	public Item getItem() {
		return Tier2RocketItemItem.block;
	}

	@Override
	public int getFuelCapacity() {
		return FUEL_CAPACITY;
	}
	
	@Override
	public int getFuelTransfer() {
		return FUEL_TRANSFER;
	}

	@Override
	public String getGUISystemKey() {
		return "Tier_2_open_main_menu";
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.5;
	}

}