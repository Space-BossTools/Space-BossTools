package net.mrscauthd.boss_tools.gauge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GaugeTextHelper {

	public static final ResourceLocation USING_NAME = new ResourceLocation("boss_tools", "using");
	public static final ResourceLocation GENERATING_NAME = new ResourceLocation("boss_tools", "generating");
	public static final ResourceLocation MAXGENERATION_NAME = new ResourceLocation("boss_tools", "maxgeneration");
	public static final ResourceLocation TOTAL_NAME = new ResourceLocation("boss_tools", "total");
	public static final ResourceLocation STORED_NAME = new ResourceLocation("boss_tools", "stored");
	public static final ResourceLocation CAPACITY_NAME = new ResourceLocation("boss_tools", "capacity");

	public static final String USING_KEY = makeTranslationKey(USING_NAME);
	public static final String GENERATING_KEY = makeTranslationKey(GENERATING_NAME);
	public static final String MAXGNERATION_KEY = makeTranslationKey(MAXGENERATION_NAME);
	public static final String TOTAL_KEY = makeTranslationKey(TOTAL_NAME);
	public static final String STORED_KEY = makeTranslationKey(STORED_NAME);
	public static final String CAPACITY_KEY = makeTranslationKey(CAPACITY_NAME);

	public static String makeTranslationKey(ResourceLocation name) {
		return "gague_text." + name.getNamespace() + "." + name.getPath();
	}

	public static ITextComponent getText(IGaugeData data, String key) {
		return new TranslationTextComponent(key, data.getValue().getDisplayName(), data.getAmountText());
	}

	public static ITextComponent getUsingText(IGaugeData data) {
		return getText(data, USING_KEY);
	}

	public static ITextComponent getTotalText(IGaugeData data) {
		return getText(data, TOTAL_KEY);
	}

	public static ITextComponent getGeneratingText(IGaugeData data) {
		return getText(data, GENERATING_KEY);
	}

	public static ITextComponent getMaxGenerationText(IGaugeData data) {
		return getText(data, MAXGNERATION_KEY);
	}

	public static ITextComponent getStoredText(IGaugeData data) {
		return getText(data, STORED_KEY);
	}

	public static ITextComponent getCapacityText(IGaugeData data) {
		return getText(data, CAPACITY_KEY);
	}

}
