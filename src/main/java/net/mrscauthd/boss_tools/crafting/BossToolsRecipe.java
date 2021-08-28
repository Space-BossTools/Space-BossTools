package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public abstract class BossToolsRecipe implements IRecipe<IInventory> {

	private final ResourceLocation id;

	public BossToolsRecipe(ResourceLocation id, JsonObject json) {
		this.id = id;
	}

	public BossToolsRecipe(ResourceLocation id, PacketBuffer buffer) {
		this.id = id;
	}

	public BossToolsRecipe(ResourceLocation id) {
		this.id = id;
	}

	public void write(PacketBuffer buffer) {

	}

	@Override
	public final ResourceLocation getId() {
		return this.id;
	}

}
