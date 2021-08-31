package net.mrscauthd.boss_tools.crafting;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;

public class BossToolsRecipeType<T extends IRecipe<IInventory>> implements IRecipeType<T> {
	private final String name;
	private List<T> cached;

	public BossToolsRecipeType(String name) {
		this.name = name;
		this.cached = null;
	}

	public String getName() {
		return this.name;
	}

	public T findFirst(World world, IInventory inventory) {
		return this.findFirst(world, f -> f.matches(inventory, world));
	}

	public T findFirst(World world, Predicate<T> filter) {
		return this.getRecipes(world).stream().filter(filter).findFirst().orElse(null);
	}

	public List<T> getRecipes(World world) {
		this.cached = null;
		if (this.cached == null) {
			RecipeManager recipeManager = world.getRecipeManager();
			this.cached = recipeManager.getRecipesForType(this);
		}

		return this.cached;
	}

}
