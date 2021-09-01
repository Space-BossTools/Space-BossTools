package net.mrscauthd.boss_tools.machines.tile;

import java.util.HashMap;

public class PowerSystemMap extends HashMap<String, PowerSystem> {

	private static final long serialVersionUID = 1L;

	public PowerSystemMap() {

	}

	public void put(PowerSystem powerSystem) {
		this.put(powerSystem.getName(), powerSystem);
	}

}
