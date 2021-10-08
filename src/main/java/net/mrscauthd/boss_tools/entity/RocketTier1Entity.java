package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;

public class RocketTier1Entity extends RocketAbstractEntity {

	public static final int FUEL_CAPACITY = 1000;
	public static final int FUEL_TRANSFER = 3;

	public RocketTier1Entity(EntityType<? extends RocketTier1Entity> type, World world) {
		super(type, world);
	}

	@Override
	public Item getItem() {
		return Tier1RocketItemItem.block;
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
		return "Tier_1_open_main_menu";
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 2.35;
	}

}