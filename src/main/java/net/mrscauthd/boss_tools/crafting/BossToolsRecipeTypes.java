package net.mrscauthd.boss_tools.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BossToolsRecipeTypes {
	public static final BossToolsRecipeType<BlastingRecipe> BLASTING = create("blasting");

	public static void init() {

	}

	private static <T extends IRecipe<IInventory>> BossToolsRecipeType<T> create(String name) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("boss_tools", name), new BossToolsRecipeType<T>(name));
	}

}
