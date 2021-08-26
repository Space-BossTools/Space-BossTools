package net.mrscauthd.boss_tools.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.machines.ItemStackToItemStackTileEntity;

public abstract class ItemStackToItemStackRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	private final Ingredient ingredient;
	private final ItemStack output;
	private final int cookTime;

	public ItemStackToItemStackRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		this.id = id;
		this.ingredient = ingredient;
		this.output = output;
		this.cookTime = cookTime;
	}

	@Override
	public boolean matches(IInventory inventory, World world) {
		if (!this.ingredient.test(inventory.getStackInSlot(this.getIngredientSlot(inventory, world))))
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
		return this.getSerializer().getRegistryName().toString();
	}

	public int getIngredientSlot(IInventory inventory, World world) {
		return ItemStackToItemStackTileEntity.SLOT_INGREDIENT;
	}

	@Override
	public abstract IRecipeSerializer<?> getSerializer();

	@Override
	public abstract IRecipeType<?> getType();

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
