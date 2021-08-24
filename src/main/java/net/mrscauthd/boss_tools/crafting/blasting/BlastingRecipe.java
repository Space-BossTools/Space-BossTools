package net.mrscauthd.boss_tools.crafting.blasting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock;

public class BlastingRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	private final Ingredient ingredient;
	private final ItemStack output;
	private final int cookTime;

	public BlastingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		this.id = id;
		this.ingredient = ingredient;
		this.output = output;
		this.cookTime = cookTime;
	}

	@Override
	public boolean matches(IInventory var1, World var2) {
		if (!this.ingredient.test(var1.getStackInSlot(BlastingFurnaceBlock.SLOT_INGREDIENT)))
			return false;

		return true;
	}

	@Override
	public ItemStack getCraftingResult(IInventory var1) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int var1, int var2) {
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public String getGroup() {
		return ModInnet.BLAST_FURNACE_BLOCK.get().getRegistryName().toString();
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_BLASTING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.BLASTING;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = IRecipe.super.getIngredients();
		list.add(this.getIngredient());
		return list;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public ItemStack getOutput() {
		return this.output;
	}

	public int getCookTime() {
		return this.cookTime;
	}

}
