package net.mrscauthd.boss_tools.gauge;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class GaugeData implements IGaugeData {
	private IGaugeValue value;
	private IGaugeFormat format;

	public GaugeData() {

	}

	public GaugeData(PacketBuffer buffer) {
		this.read(buffer);
	}

	public GaugeData(CompoundNBT compound) {
		this.deserializeNBT(compound);
	}

	public GaugeData(@Nonnull IGaugeValue value, @Nonnull IGaugeFormat format) {
		this.setValue(value);
		this.setFormat(format);
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		this.setValue(GaugeDataSerializer.GaugeValue.deserialize(compound.getCompound("value")));
		this.setFormat(GaugeDataSerializer.GaugeFormat.deserialize(compound.getCompound("format")));
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.put("value", GaugeDataSerializer.GaugeValue.serialize(this.getValue()));
		compound.put("format", GaugeDataSerializer.GaugeFormat.serialize(this.getFormat()));
		return compound;
	}

	@Override
	public void read(PacketBuffer buffer) {
		this.setValue(GaugeDataSerializer.GaugeValue.read(buffer));
		this.setFormat(GaugeDataSerializer.GaugeFormat.read(buffer));
	}

	@Override
	public void write(PacketBuffer buffer) {
		GaugeDataSerializer.GaugeValue.write(this.getValue(), buffer);
		GaugeDataSerializer.GaugeFormat.write(this.getFormat(), buffer);
	}

	@Override
	public IGaugeValue getValue() {
		return value;
	}

	public void setValue(@Nonnull IGaugeValue value) {
		this.value = value;
	}

	@Override
	public IGaugeFormat getFormat() {
		return format;
	}

	public void setFormat(@Nonnull IGaugeFormat format) {
		this.format = format;
	}

}
