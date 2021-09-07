package net.mrscauthd.boss_tools.gauge;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface IGaugeValue {

	void write(PacketBuffer buffer);

	String getDisplayName();

	String getUnit();

	int getStored();

	int getCapacity();

	GaugeValueFormat getFormat();

	public default double getStoredRatio() {
		return (double) this.getStored() / (double) getCapacity();
	}

	public default double getDisplayRatio() {
		GaugeValueFormat format = this.getFormat();
		int stored = this.getStored();
		int capacity = this.getCapacity();
		return capacity == 0 ? 0.0D : ((format.isReversePercenage() ? (capacity - stored) : stored) / (double) capacity);
	}

	public default ITextComponent getText() {
		int capacity = this.getCapacity();
		int stored = this.getStored();
		String unit = this.getUnit();
		GaugeValueFormat format = this.getFormat();
		StringBuilder builder = new StringBuilder();

		String displayName = this.getDisplayName();

		if (!StringUtils.isEmpty(displayName)) {
			builder.append(displayName).append(": ");
		}

		builder.append(stored);

		if (!StringUtils.isEmpty(unit)) {
			builder.append(" ").append(unit);
		}

		if (format.isShowCapacity()) {
			builder.append(" / ").append(capacity);

			if (!StringUtils.isEmpty(unit)) {
				builder.append(" ").append(unit);
			}

		}

		return new TranslationTextComponent(builder.toString(), displayName);
	}

}
