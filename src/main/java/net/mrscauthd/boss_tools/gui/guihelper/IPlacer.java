package net.mrscauthd.boss_tools.gui.guihelper;

import net.minecraft.client.renderer.Rectangle2d;

@FunctionalInterface
public interface IPlacer {
	Rectangle2d place(int index, int left, int top, int mod);
}
