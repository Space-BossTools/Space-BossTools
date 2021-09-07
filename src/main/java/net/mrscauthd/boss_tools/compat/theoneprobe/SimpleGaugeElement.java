package net.mrscauthd.boss_tools.compat.theoneprobe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.gauge.SimpleGaugeValue;

public class SimpleGaugeElement extends GaugeElement {

	public SimpleGaugeElement(SimpleGaugeValue gaugeValue) {
		super(gaugeValue);
	}

	public SimpleGaugeElement(PacketBuffer buffer) {
		super(new SimpleGaugeValue(buffer));
	}

	@Override
	public TextureAtlasSprite getTileTexture() {
		TextureAtlasSprite apply = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation("boss_tools", "blocks/simplegaugevalue"));
		System.out.println(apply);
		return apply;
	}

	@Override
	public int getTileColor() {
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
