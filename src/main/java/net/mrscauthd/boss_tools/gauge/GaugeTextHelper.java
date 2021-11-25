package net.mrscauthd.boss_tools.gauge;

import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public class GaugeTextHelper {

	public static final ResourceLocation USING_NAME = new ResourceLocation("boss_tools", "using");
	public static final ResourceLocation USING2_NAME = new ResourceLocation("boss_tools", "using2");
	public static final ResourceLocation GENERATING_NAME = new ResourceLocation("boss_tools", "generating");
	public static final ResourceLocation MAXGENERATION_NAME = new ResourceLocation("boss_tools", "maxgeneration");
	public static final ResourceLocation TOTAL_NAME = new ResourceLocation("boss_tools", "total");
	public static final ResourceLocation STORED_NAME = new ResourceLocation("boss_tools", "stored");
	public static final ResourceLocation CAPACITY_NAME = new ResourceLocation("boss_tools", "capacity");
	public static final ResourceLocation TRANSFER_NAME = new ResourceLocation("boss_tools", "transfer");

	public static final String USING_KEY = makeTranslationKey(USING_NAME);
	public static final String USING2_KEY = makeTranslationKey(USING2_NAME);
	public static final String GENERATING_KEY = makeTranslationKey(GENERATING_NAME);
	public static final String MAXGNERATION_KEY = makeTranslationKey(MAXGENERATION_NAME);
	public static final String TOTAL_KEY = makeTranslationKey(TOTAL_NAME);
	public static final String STORED_KEY = makeTranslationKey(STORED_NAME);
	public static final String CAPACITY_KEY = makeTranslationKey(CAPACITY_NAME);
	public static final String TRANSFER_KEY = makeTranslationKey(TRANSFER_NAME);

	public static final String PER_TICK_UNIT_SUFFIX = "/t";

	public static String makeTranslationKey(ResourceLocation name) {
		return "gague_text." + name.getNamespace() + "." + name.getPath();
	}

	public static GaugeTextBuilder getText(IGaugeValue value, String key, Object... values) {
		return new GaugeTextBuilder(value, key, Lists.newArrayList(values));
	}

	public static GaugeTextBuilder getValueText(IGaugeValue value) {
		return getText(value, "%1$s: %2$s");
	}

	public static GaugeTextBuilder getStorageText(IGaugeValue value) {
		return getText(value, "%1$s: %2$s / %3$s");
	}

	public static GaugeTextBuilder getUsingText(IGaugeValue value) {
		return getText(value, USING_KEY);
	}

	public static GaugeTextBuilder getUsingPerTickText(IGaugeValue value) {
		return getUsingText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getUsingText2(IGaugeValue value, int interval) {
		return getText(value, USING2_KEY, String.valueOf(interval));
	}

	public static GaugeTextBuilder getTotalText(IGaugeValue value) {
		return getText(value, TOTAL_KEY);
	}

	public static GaugeTextBuilder getGeneratingText(IGaugeValue value) {
		return getText(value, GENERATING_KEY);
	}

	public static GaugeTextBuilder getGeneratingPerTickText(IGaugeValue value) {
		return getGeneratingText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getMaxGenerationText(IGaugeValue value) {
		return getText(value, MAXGNERATION_KEY);
	}

	public static GaugeTextBuilder getMaxGenerationPerTickText(IGaugeValue value) {
		return getMaxGenerationText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getStoredText(IGaugeValue value) {
		return getText(value, STORED_KEY);
	}

	public static GaugeTextBuilder getCapacityText(IGaugeValue value) {
		return getText(value, CAPACITY_KEY);
	}

	public static GaugeTextBuilder getTransferText(IGaugeValue value) {
		return getText(value, TRANSFER_KEY);
	}

	public static GaugeTextBuilder getTransferPerTickText(IGaugeValue value) {
		return getTransferText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static IFormattableTextComponent buildSpacesuitOxygenTooltip(IOxygenStorage oxygenStorage) {
		IGaugeValue oxygen;

		if (oxygenStorage != null) {
			oxygen = GaugeValueHelper.getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
		} else {
			oxygen = GaugeValueHelper.getOxygen(0, 0);
		}

		GaugeTextBuilder builder = GaugeTextHelper.getText(oxygen, "%1$s: %2$s \u00A78| %3$s");
		builder.setTextStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE));
		builder.setAmountStyle(Style.EMPTY.setFormatting(TextFormatting.GOLD));
		builder.setCapacityStyle(Style.EMPTY.setFormatting(TextFormatting.RED));
		return builder.build();
	}

	public static IFormattableTextComponent buildBlockTooltip(GaugeTextBuilder builder) {
		builder.setTextStyle(Style.EMPTY.setFormatting(TextFormatting.BLUE));
		builder.setAmountStyle(Style.EMPTY.setFormatting(TextFormatting.GRAY));
		return builder.build();
	}
}
