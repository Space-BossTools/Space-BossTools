package net.mrscauthd.boss_tools;

import net.mrscauthd.boss_tools.entity.AlienEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.goal.Goal;

public class TradeGoal extends Goal {
	private AlienEntity entity;
	public TradeGoal(AlienEntity alienEntity) {
		entity = alienEntity;
	}

	@Override
	public boolean shouldExecute() {
		if (!this.entity.isAlive()) {
			return false;
		} else if (this.entity.isInWater()) {
			return false;
		} else if (this.entity.velocityChanged) {
			return false;
		} else {
			PlayerEntity playerEntity = this.entity.getCustomer();
			if (playerEntity == null) {
				return false;
			} else if (this.entity.getDistanceSq(playerEntity) > 16.0D) {
				return false;
			}
			return playerEntity.openContainer != null;
		}
	}

	@Override
	public void startExecuting() {
		this.entity.getNavigator().clearPath();
	}

	@Override
	public void resetTask() {
		this.entity.setCustomer(null);
	}
}
