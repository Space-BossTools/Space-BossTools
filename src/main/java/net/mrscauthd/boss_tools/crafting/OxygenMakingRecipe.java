package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;

public class OxygenMakingRecipe extends BossToolsRecipe {

	public static final int SLOT_INGREDIENT = 0;

	private final Ingredient ingredient;
	private final int activaingTime;

	public OxygenMakingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = JSONUtils.getJsonObject(json, "input");
		this.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(inputJson, "ingredient"));
		this.activaingTime = JSONUtils.getInt(json, "activaingTime");
	}

	public OxygenMakingRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);

		this.ingredient = Ingredient.read(buffer);
		this.activaingTime = buffer.readInt();

	}

	public OxygenMakingRecipe(ResourceLocation id, Ingredient ingredient, int activaingTime) {
		super(id);
		this.ingredient = ingredient;
		this.activaingTime = activaingTime;
	}

	@Override
	public void write(PacketBuffer buffer) {
		super.write(buffer);

		this.getIngredient().write(buffer);
		buffer.writeInt(this.getActivaingTime());
	}

	@Override
	public boolean canFit(int var1, int var2) {
		return true;
	}

	@Override
	public ItemStack getCraftingResult(IInventory var1) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_OXYGENMAKING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	public int getIngredientSlot(IInventory inventory, World world) {
		return SLOT_INGREDIENT;
	}

	public boolean testIngredient(ItemStack itemStack) {
		return this.ingredient.test(itemStack);
	}

	@Override
	public boolean matches(IInventory inventory, World world) {
		if (!this.testIngredient(inventory.getStackInSlot(this.getIngredientSlot(inventory, world))))
			return false;

		return true;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = super.getIngredients();
		list.add(this.getIngredient());
		return list;
	}

	public int getActivaingTime() {
		return this.activaingTime;
	}

}
