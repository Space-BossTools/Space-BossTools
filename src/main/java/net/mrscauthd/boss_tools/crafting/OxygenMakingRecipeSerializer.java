package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class OxygenMakingRecipeSerializer extends BossToolsRecipeSerializer<OxygenMakingRecipe> {

	@Override
	public OxygenMakingRecipe read(ResourceLocation id, JsonObject json) {
		return new OxygenMakingRecipe(id, json);
	}

	@Override
	public OxygenMakingRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new OxygenMakingRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, OxygenMakingRecipe recipe) {
		recipe.write(buffer);
	}

}
