package net.mrscauthd.boss_tools.gauge;

import net.minecraft.network.PacketBuffer;

public class GaugeValueFormat implements IGaugeValueFormat {

	private boolean showCapacity;
	private boolean reversePercenage;

	public GaugeValueFormat() {

	}

	public GaugeValueFormat(boolean showCapacity) {
		this(showCapacity, false);
	}

	public GaugeValueFormat(boolean showCapacity, boolean reversePercenge) {
		this.showCapacity(showCapacity);
		this.reversePercenage(reversePercenge);
	}

	public GaugeValueFormat(PacketBuffer buffer) {
		this.showCapacity(buffer.readBoolean());
		this.reversePercenage(buffer.readBoolean());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeBoolean(this.isShowCapacity());
		buffer.writeBoolean(this.isReversePercenage());
	}

	@Override
	public boolean isShowCapacity() {
		return showCapacity;
	}

	public GaugeValueFormat showCapacity(boolean showCapacity) {
		this.showCapacity = showCapacity;
		return this;
	}

	public boolean isReversePercenage() {
		return reversePercenage;
	}

	public GaugeValueFormat reversePercenage(boolean reversePercenage) {
		this.reversePercenage = reversePercenage;
		return this;
	}

}
