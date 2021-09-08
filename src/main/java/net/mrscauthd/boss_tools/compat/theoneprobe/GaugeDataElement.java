package net.mrscauthd.boss_tools.compat.theoneprobe;

import mcjty.theoneprobe.api.IElement;
import net.minecraft.network.PacketBuffer;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataRenderer;

public class GaugeDataElement extends GaugeDataRenderer implements IElement {

	public GaugeDataElement(GaugeData data) {
		super(data);
	}

	public GaugeDataElement(PacketBuffer buffer) {
		super(buffer);
	}

	@Override
	public int getID() {
		return ProbeInfoProvider.ELEMENT_ID;
	}

}
