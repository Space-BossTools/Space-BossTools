package net.mrscauthd.boss_tools.crafting;

public enum RocketPart {
	NOSE(1),
	BODY(6),
	TANK(2),
	FIN_LEFT(2),
	FIN_RIGHT(2),
	ENGINE(1),
	// EOL
	;

	private int slots;

	private RocketPart(int slots) {
		this.slots = slots;
	}
	
	public int getSlots() {
		return this.slots;
	}
}
