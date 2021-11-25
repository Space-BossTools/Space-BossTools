package net.mrscauthd.boss_tools.gauge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class GaugeTextBuilder {
	private final IGaugeValue value;
	private final String translationKey;
	private final List<Object> extraValues;

	private Style textStyle;
	private Style amountStyle;
	private Style capacityStyle;
	private Style unitStyle;
	
	private String unitSuffix;

	public GaugeTextBuilder(IGaugeValue value, String translationKey, List<Object> extraValues) {
		this.value = value;
		this.translationKey = translationKey;
		this.extraValues = Collections.unmodifiableList(extraValues);

		this.setTextStyle(Style.EMPTY);
		this.setAmountStyle(Style.EMPTY);
		this.setCapacityStyle(Style.EMPTY);
		this.setUnitStyle(Style.EMPTY);
		
		this.setUnitSuffix("");
	}

	public IFormattableTextComponent build() {
		IGaugeValue value = this.getValue();
		ITextComponent displayName = value.getDisplayName();
		int amount = value.getAmount();
		int capacity = value.getCapacity();
		String unit = value.getUnit();

		List<Object> list = new ArrayList<>();
		list.add(this.format(displayName, this.getTextStyle()));
		list.add(this.format(String.valueOf(amount), this.getAmountStyle(), unit, this.getUnitStyle()));
		list.add(this.format(String.valueOf(capacity), this.getCapacityStyle(), unit, this.getUnitStyle()));
		list.addAll(this.getExtraValues());

		return new TranslationTextComponent(this.getTranslationKey(), list.toArray()).setStyle(this.getTextStyle());
	}

	public IFormattableTextComponent format(String valueText, Style valueStyle, String unitText, Style unitStyle) {
		if (!StringUtils.isEmpty(unitText)) {
			return this.format(valueText, valueStyle).appendString(" ").append(this.format(unitText + this.getUnitSuffix(), unitStyle));
		} else {
			return this.format(valueText, valueStyle);
		}
	}

	public IFormattableTextComponent format(String text, Style style) {
		return new StringTextComponent(text).setStyle(style);
	}

	public IFormattableTextComponent format(ITextComponent text, Style style) {
		return new StringTextComponent("").append(text).setStyle(style);
	}

	public final IGaugeValue getValue() {
		return this.value;
	}

	public final String getTranslationKey() {
		return this.translationKey;
	}

	public final List<Object> getExtraValues() {
		return extraValues;
	}

	public Style getTextStyle() {
		return textStyle;
	}

	public GaugeTextBuilder setTextStyle(Style textStyle) {
		this.textStyle = textStyle;
		return this;
	}

	public Style getAmountStyle() {
		return amountStyle;
	}

	public GaugeTextBuilder setAmountStyle(Style amountStyle) {
		this.amountStyle = amountStyle;
		return this;
	}

	public Style getCapacityStyle() {
		return capacityStyle;
	}

	public GaugeTextBuilder setCapacityStyle(Style capacityStyle) {
		this.capacityStyle = capacityStyle;
		return this;
	}

	public Style getUnitStyle() {
		return unitStyle;
	}

	public GaugeTextBuilder setUnitStyle(Style unitStyle) {
		this.unitStyle = unitStyle;
		return this;
	}
	
	public String getUnitSuffix() {
		return unitSuffix;
	}
	
	public GaugeTextBuilder setUnitSuffix(String unitSuffix) {
		this.unitSuffix = unitSuffix;
		return this;
	}

}
