package net.mrscauthd.boss_tools.crafting.blasting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeSerializer;

public class BlastingRecipeSerializer extends ItemStackToItemStackRecipeSerializer<BlastingRecipe> {

	@Override
	protected BlastingRecipe onRead(ResourceLocation id, JsonObject json, Ingredient ingredient, ItemStack output, int cookTime) {
		return new BlastingRecipe(id, ingredient, output, cookTime);
	}

	@Override
	protected BlastingRecipe onRead(ResourceLocation id, PacketBuffer buffer, Ingredient ingredient, ItemStack output, int cookTime) {
		return new BlastingRecipe(id, ingredient, output, cookTime);
	}

	@Override
	protected void onWrite(PacketBuffer buffer, BlastingRecipe recipe) {

	}

}
