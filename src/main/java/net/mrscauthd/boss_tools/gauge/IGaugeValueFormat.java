package net.mrscauthd.boss_tools.gauge;

import net.minecraft.network.PacketBuffer;

public interface IGaugeValueFormat {

	void write(PacketBuffer buffer);

	boolean isShowCapacity();

}
