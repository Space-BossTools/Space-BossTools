package net.mrscauthd.boss_tools.compat.theoneprobe;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.PacketBuffer;
import net.mrscauthd.boss_tools.gauge.FluidStackGaugeValue;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public class FluidGaugeElement extends GaugeElement {

	public FluidGaugeElement(FluidStackGaugeValue gaugeValue) {
		super(gaugeValue);
	}

	public FluidGaugeElement(PacketBuffer buffer) {
		super(new FluidStackGaugeValue(buffer));
	}

	@Override
	public TextureAtlasSprite getTileTexture() {
		return GuiHelper.getStillFluidSprite(this.getGaugeValue().getStack());
	}

	@Override
	public int getID() {
		return ProbeInfoProvider.FLUID_ELEMENT_ID;
	}

	@Override
	public FluidStackGaugeValue getGaugeValue() {
		return (FluidStackGaugeValue) super.getGaugeValue();
	}

}
