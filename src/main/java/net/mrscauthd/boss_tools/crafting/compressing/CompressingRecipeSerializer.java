package net.mrscauthd.boss_tools.crafting.compressing;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeSerializer;

public class CompressingRecipeSerializer extends ItemStackToItemStackRecipeSerializer<CompressingRecipe> {

	@Override
	protected CompressingRecipe onRead(ResourceLocation id, JsonObject json, Ingredient ingredient, ItemStack output, int cookTime) {
		return new CompressingRecipe(id, ingredient, output, cookTime);
	}

	@Override
	protected CompressingRecipe onRead(ResourceLocation id, PacketBuffer buffer, Ingredient ingredient, ItemStack output, int cookTime) {
		return new CompressingRecipe(id, ingredient, output, cookTime);
	}

	@Override
	protected void onWrite(PacketBuffer buffer, CompressingRecipe recipe) {

	}

}
