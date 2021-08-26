package net.mrscauthd.boss_tools.crafting;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.boss_tools.crafting.blasting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.compressing.CompressingRecipe;

public class BossToolsRecipeTypes {
	public static final ItemStackToItemStackRecipeType<BlastingRecipe> BLASTING = createItemStackToItemStack("blasting");
	public static final ItemStackToItemStackRecipeType<CompressingRecipe> COMPRESSING = createItemStackToItemStack("compressing");

	public static void init() {

	}

	private static <T extends BossToolsRecipeType<?>> T create(T value) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("boss_tools", value.getName()), value);
		return value;
	}

	private static <T extends ItemStackToItemStackRecipe> ItemStackToItemStackRecipeType<T> createItemStackToItemStack(String name) {
		return create(new ItemStackToItemStackRecipeType<T>(name));
	}

}
