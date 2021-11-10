package net.mrscauthd.boss_tools.gauge;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GaugeFormat implements IGaugeFormat {

	private boolean showCapacity;

	public GaugeFormat() {

	}

	public GaugeFormat(boolean showCapacity) {
		this.showCapacity(showCapacity);
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
	public void deserializeNBT(CompoundNBT compound) {
		this.showCapacity(compound.getBoolean("showCapacity"));
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putBoolean("showCapacity", this.isShowCapacity());
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
