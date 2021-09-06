package net.mrscauthd.boss_tools.machines.tile;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;

public class NamedComponentRegistry<T> extends HashMap<ResourceLocation, T> {
	public static final ResourceLocation UNNAMED = new ResourceLocation("boss_tools", "unnmaed");
	private static final long serialVersionUID = 1L;

	public void put(T value) {
		if (this.containsKey(UNNAMED)) {
			throw new IllegalArgumentException("UNNAMED component is already added");
		}

		this.put(UNNAMED, value);
	}
}
