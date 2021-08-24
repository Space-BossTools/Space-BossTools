package net.mrscauthd.boss_tools.crafting.blasting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class BlastingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BlastingRecipe> {

	@Override
	public BlastingRecipe read(ResourceLocation recipeId, JsonObject json) {
		JsonObject inputJson = JSONUtils.getJsonObject(json, "input");
		Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(inputJson, "ingredient"));
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		int cookTime = JSONUtils.getInt(json, "cookTime");

		return new BlastingRecipe(recipeId, ingredient, output, cookTime);
	}

	@Override
	public BlastingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient ingredient = Ingredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		int cookTime = buffer.readInt();

		return new BlastingRecipe(recipeId, ingredient, output, cookTime);
	}

	@Override
	public void write(PacketBuffer buffer, BlastingRecipe recipe) {
		recipe.getIngredient().write(buffer);
		buffer.writeItemStack(recipe.getOutput());
		buffer.writeInt(recipe.getCookTime());
	}

}
