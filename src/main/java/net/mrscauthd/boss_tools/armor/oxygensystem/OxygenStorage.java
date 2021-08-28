package net.mrscauthd.boss_tools.armor.oxygensystem;

import java.util.Optional;

public class OxygenStorage implements IOxygenStorage {
	private IOxygenStorageHolder holder;
	protected int oxygen;
	protected int capacity;

	public OxygenStorage(IOxygenStorageHolder holder, int capacity) {
		this(holder, capacity, 0);
	}

	public OxygenStorage(IOxygenStorageHolder holder, int capacity, int oxygen) {
		this.holder = holder;
		this.capacity = capacity;
		this.oxygen = Math.max(0, Math.min(capacity, oxygen));
	}

	public int receiveOxygen(int maxReceive, boolean simulate) {
		int oxygenReceived = Math.min(this.capacity - this.oxygen, Math.max(0, maxReceive));
		if (!simulate) {
			this.oxygen += oxygenReceived;
			if (oxygenReceived > 0) {
				Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onOxygenChanged(this, +oxygenReceived));
			}
		}

		return oxygenReceived;
	}

	public int extractOxygen(int maxExtract, boolean simulate) {
		int oxygenExtracted = Math.min(this.oxygen, Math.max(0, maxExtract));
		if (!simulate) {
			this.oxygen -= oxygenExtracted;

			if (oxygenExtracted > 0) {
				Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onOxygenChanged(this, -oxygenExtracted));
			}
		}

		return oxygenExtracted;
	}

	public IOxygenStorageHolder getHolder() {
		return this.holder;
	}

	public int getOxygenStored() {
		return this.oxygen;
	}

	@Override
	public void setOxygenStored(int oxygen) {
		this.oxygen = Math.max(Math.min(oxygen, this.getMaxOxygenStored()), 0);
	}

	public int getMaxOxygenStored() {
		return this.capacity;
	}

}
