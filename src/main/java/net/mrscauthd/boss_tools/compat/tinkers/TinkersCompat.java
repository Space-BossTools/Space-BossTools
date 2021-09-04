package net.mrscauthd.boss_tools.compat.tinkers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TinkersCompat {
	public static final String MODID = "tconstruct";

	public TinkersCompat() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		TinkersBossToolsFluids.FLUIDS.register(bus);
	}

	public static ResourceLocation rl(String path) {
		return new ResourceLocation(MODID, path);
	}
}
