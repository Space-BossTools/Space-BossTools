package net.mrscauthd.boss_tools.gauge;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

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
		StringTextComponent builder = new StringTextComponent("");
		ITextComponent displayName = value.getDisplayName();

		if (displayName != null) {
			builder.append(displayName).appendString(": ");
		}

		builder.append(this.getAmountText(value));
		return builder;
	}

	@Override
	public ITextComponent getAmountText(IGaugeValue value) {
		int capacity = value.getCapacity();
		int amount = value.getAmount();
		String unit = value.getUnit();
		StringTextComponent builder = new StringTextComponent(String.valueOf(amount));

		if (!StringUtils.isEmpty(unit)) {
			builder.appendString(" ").appendString(unit);
		}

		if (this.isShowCapacity()) {
			builder.appendString(" / ").appendString(String.valueOf(capacity));

			if (!StringUtils.isEmpty(unit)) {
				builder.appendString(" ").appendString(unit);
			}

		}

		return builder;
	}

}
