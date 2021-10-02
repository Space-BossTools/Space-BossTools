package net.mrscauthd.boss_tools.crafting;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BossToolsRecipeTypes {
	public static final ItemStackToItemStackRecipeType<BlastingRecipe> BLASTING = create(new ItemStackToItemStackRecipeType<>("blasting"));
	public static final ItemStackToItemStackRecipeType<CompressingRecipe> COMPRESSING = create(new ItemStackToItemStackRecipeType<>("compressing"));
	public static final BossToolsRecipeType<GeneratingRecipe> GENERATING = create(new BossToolsRecipeType<>("generating"));
	public static final BossToolsRecipeType<OxygenLoaderRecipe> OXYGENLOADER = create(new BossToolsRecipeType<>("oxygenloader"));
	public static final BossToolsRecipeType<OxygenBubbleDistributorRecipe> OXYGENBUBBLEDISTRIBUTOR = create(new BossToolsRecipeType<>("oxygenbubbledistributor"));
	public static final BossToolsRecipeType<WorkbenchingRecipe> WORKBENCHING = create(new BossToolsRecipeType<>("workbenching"));
	public static final BossToolsRecipeType<FuelRefiningRecipe> FUELREFINING = create(new BossToolsRecipeType<>("fuelrefining"));

	/**
	 * for initialize static final fields
	 */
	public static void init() {

	}

	private static <T extends BossToolsRecipeType<?>> T create(T value) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("boss_tools", value.getName()), value);
		return value;
	}

}
