package net.mrscauthd.boss_tools.events.forgeevents;

import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemSpaceGravityEvent extends ItemEvent {
	private double divisor;
	private double offset;

	public ItemSpaceGravityEvent(ItemEntity itemEntity, double divisor, double offset) {
		super(itemEntity);
		this.divisor = divisor;
		this.offset = offset;
	}

	public double getDivisor() {
		return divisor;
	}

	public void setDivisor(double divisor) {
		this.divisor = divisor;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}

}
