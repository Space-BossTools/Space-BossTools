package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.mrscauthd.boss_tools.machines.machinetileentities.ItemStackToItemStackTileEntity;

public abstract class ItemStackToItemStackRecipe extends BossToolsRecipe {
	private final Ingredient ingredient;
	private final ItemStack output;
	private final int cookTime;

	public ItemStackToItemStackRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = JSONUtils.getJsonObject(json, "input");
		this.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(inputJson, "ingredient"));
		this.output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		this.cookTime = JSONUtils.getInt(json, "cookTime");
	}

	public ItemStackToItemStackRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);
		this.ingredient = Ingredient.read(buffer);
		this.output = buffer.readItemStack();
		this.cookTime = buffer.readInt();
	}

	public ItemStackToItemStackRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		super(id);
		this.ingredient = ingredient;
		this.output = output;
		this.cookTime = cookTime;
	}

	public void write(PacketBuffer buffer) {
		this.getIngredient().write(buffer);
		buffer.writeItemStack(this.getOutput());
		buffer.writeInt(this.getCookTime());
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
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output.copy();
	}

	public int getIngredientSlot(IInventory inventory, World world) {
		return ItemStackToItemStackTileEntity.SLOT_INGREDIENT;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = super.getIngredients();
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
