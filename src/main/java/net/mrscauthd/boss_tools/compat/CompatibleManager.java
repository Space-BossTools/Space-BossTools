package net.mrscauthd.boss_tools.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mrscauthd.boss_tools.compat.theoneprobe.TOPCompat;
import net.mrscauthd.boss_tools.compat.tinkers.TinkersCompat;

public class CompatibleManager {

	public static final List<CompatibleMod> MODS;
	public static final TinkersCompat TINKERS;
	public static final TOPCompat TOP;

	static {
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TINKERS = new TinkersCompat());
		mods.add(TOP = new TOPCompat());

		MODS = Collections.unmodifiableList(mods);
	}

	public static void loadAll() {
		for (CompatibleMod mod : MODS) {
			mod.tryLoad();
		}
	}

}
