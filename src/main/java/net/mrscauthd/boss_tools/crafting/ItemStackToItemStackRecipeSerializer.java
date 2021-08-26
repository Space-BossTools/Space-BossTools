package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ItemStackToItemStackRecipeSerializer<T extends ItemStackToItemStackRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

	@Override
	public T read(ResourceLocation recipeId, JsonObject json) {
		JsonObject inputJson = JSONUtils.getJsonObject(json, "input");
		Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(inputJson, "ingredient"));
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		int cookTime = JSONUtils.getInt(json, "cookTime");

		return this.onRead(recipeId, json, ingredient, output, cookTime);
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient ingredient = Ingredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		int cookTime = buffer.readInt();

		return this.onRead(recipeId, buffer, ingredient, output, cookTime);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe) {
		recipe.getIngredient().write(buffer);
		buffer.writeItemStack(recipe.getOutput());
		buffer.writeInt(recipe.getCookTime());
		
		this.onWrite(buffer, recipe);
	}

	protected abstract T onRead(ResourceLocation id, JsonObject json, Ingredient ingredient, ItemStack output, int cookTime);

	protected abstract T onRead(ResourceLocation id, PacketBuffer buffer, Ingredient ingredient, ItemStack output, int cookTime);
	
	protected abstract void onWrite(PacketBuffer buffer, T recipe);

}
