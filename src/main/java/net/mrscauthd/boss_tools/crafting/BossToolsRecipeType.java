package net.mrscauthd.boss_tools.crafting;

import java.util.List;

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
		return this.getRecipes(world).stream().filter(t -> t.matches(inventory, world)).findFirst().orElse(null);
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
