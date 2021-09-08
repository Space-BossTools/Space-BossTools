package net.mrscauthd.boss_tools.compat.theoneprobe;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.gauge.SimpleGaugeValue;

public class SimpleGaugeElement extends GaugeElement {

	public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("boss_tools", "textures/simplegaugevalue.png");

	public SimpleGaugeElement(SimpleGaugeValue gaugeValue) {
		super(gaugeValue);
	}

	public SimpleGaugeElement(PacketBuffer buffer) {
		super(new SimpleGaugeValue(buffer));
	}

	@Override
	public ResourceLocation getBackgroundTexture() {
		return new ResourceLocation("boss_tools", "textures/simplegaugevalue.png");
	}

	@Override
	public int getBackgroundColor() {
		return this.getGaugeValue().getColor();
	}

	@Override
	public int getID() {
		return ProbeInfoProvider.SIMPLE_ELEMENT_ID;
	}

	@Override
	public SimpleGaugeValue getGaugeValue() {
		return (SimpleGaugeValue) super.getGaugeValue();
	}

}
