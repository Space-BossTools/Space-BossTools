package net.mrscauthd.boss_tools.compat;

import net.minecraft.util.ResourceLocation;

public class MekanismCompat extends CompatibleMod {
	public static final String MODID = "mekanism";

	public static ResourceLocation rl(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID() {
		return MODID;
	}

	@Override
	protected void onLoad() {

	}

}
