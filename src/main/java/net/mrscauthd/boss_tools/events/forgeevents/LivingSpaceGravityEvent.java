package net.mrscauthd.boss_tools.events.forgeevents;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingSpaceGravityEvent extends LivingEvent {
	private double divisor;
	private double offset;
	private double fallDistanceMultiplier;

	public LivingSpaceGravityEvent(LivingEntity entity, double divisor, double offset, double fallDistanceMultiplier) {
		super(entity);
		this.divisor = divisor;
		this.offset = offset;
		this.fallDistanceMultiplier = fallDistanceMultiplier;
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

	public double getFallDistanceMultiplier() {
		return fallDistanceMultiplier;
	}

	public void setFallDistanceMultiplier(double fallDistanceMultiplier) {
		this.fallDistanceMultiplier = fallDistanceMultiplier;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}

}