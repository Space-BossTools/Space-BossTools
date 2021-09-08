package net.mrscauthd.boss_tools.gauge;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GaugeFormat implements IGaugeFormat {

	private boolean showCapacity;
	private boolean reversePercenage;

	public GaugeFormat() {

	}

	public GaugeFormat(boolean showCapacity) {
		this(showCapacity, false);
	}

	public GaugeFormat(boolean showCapacity, boolean reversePercenge) {
		this.showCapacity(showCapacity);
		this.reversePercenage(reversePercenge);
	}

	@Override
	public boolean isShowCapacity() {
		return showCapacity;
	}

	public GaugeFormat showCapacity(boolean showCapacity) {
		this.showCapacity = showCapacity;
		return this;
	}

	@Override
	public boolean isReversePercenage() {
		return reversePercenage;
	}

	public GaugeFormat reversePercenage(boolean reversePercenage) {
		this.reversePercenage = reversePercenage;
		return this;
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		this.showCapacity(compound.getBoolean("showCapacity"));
		this.reversePercenage(compound.getBoolean("reversePercenage"));
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putBoolean("showCapacity", this.isShowCapacity());
		compound.putBoolean("reversePercenage", this.isReversePercenage());
		return compound;
	}

	@Override
	public ITextComponent getText(IGaugeValue value) {
		int capacity = value.getCapacity();
		int stored = value.getStored();
		String unit = value.getUnit();
		StringBuilder builder = new StringBuilder();

		String displayName = value.getDisplayName();

		if (!StringUtils.isEmpty(displayName)) {
			builder.append(displayName).append(": ");
		}

		builder.append(stored);

		if (!StringUtils.isEmpty(unit)) {
			builder.append(" ").append(unit);
		}

		if (this.isShowCapacity()) {
			builder.append(" / ").append(capacity);

			if (!StringUtils.isEmpty(unit)) {
				builder.append(" ").append(unit);
			}

		}

		return new TranslationTextComponent(builder.toString(), displayName);
	}

}
