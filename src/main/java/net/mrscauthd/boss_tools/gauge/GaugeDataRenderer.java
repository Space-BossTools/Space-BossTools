package net.mrscauthd.boss_tools.gauge;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public class GaugeDataRenderer extends AbstractGaugeDataRenderer {

	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("boss_tools", "textures/simplegaugevalue.png");

	public GaugeDataRenderer(GaugeData data) {
		super(data);
	}

	public GaugeDataRenderer(PacketBuffer buffer) {
		super(new GaugeData(buffer));
	}

	@Override
	public TextureAtlasSprite getBackgroundTileTexture() {
		IGaugeValue value = this.getData().getValue();

		if (value instanceof GaugeValueFluidStack) {
			return GuiHelper.getStillFluidSprite(((GaugeValueFluidStack) value).getStack());
		} else {
			return null;
		}
	}

	@Override
	public ResourceLocation getBackgroundTexture() {
		IGaugeValue value = this.getData().getValue();
		return value instanceof GaugeValueSimple ? DEFAULT_TEXTURE : null;
	}

	@Override
	public int getBackgroundColor() {
		IGaugeValue value = this.getData().getValue();

		if (value instanceof GaugeValueFluidStack) {
			FluidStack fluidStack = ((GaugeValueFluidStack) value).getStack();
			FluidAttributes attributes = fluidStack.getFluid().getAttributes();
			return attributes.getColor(fluidStack);
		}
		else if (value instanceof GaugeValueSimple) {
			return ((GaugeValueSimple) value).getColor();
		} else {
			return super.getBackgroundColor();
		}

	}

	@Override
	public GaugeData getData() {
		return (GaugeData) super.getData();
	}

}
