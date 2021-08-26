package net.mrscauthd.boss_tools.crafting.compressing;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class CompressingRecipe extends ItemStackToItemStackRecipe {

	public CompressingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		super(id, ingredient, output, cookTime);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_COMPRESSING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.COMPRESSING;
	}

}
