package net.mrscauthd.boss_tools.gauge;

import javax.annotation.Nonnull;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SimpleGaugeValue implements IGaugeValue {

	private ResourceLocation name;
	private int stored;
	private int capacity;
	private GaugeValueFormat format;
	private String displayeName;
	private String unit;
	private int color;

	public SimpleGaugeValue(@Nonnull ResourceLocation name) {
		this(name, 0, 0);
	}

	public SimpleGaugeValue(@Nonnull ResourceLocation name, int stored, int capacity) {
		this(name, stored, capacity, new GaugeValueFormat());
	}

	public SimpleGaugeValue(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull GaugeValueFormat format) {
		this(name, stored, capacity, format, "");
	}

	public SimpleGaugeValue(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull GaugeValueFormat format, @Nonnull String displayeName) {
		this(name, stored, capacity, format, displayeName, "");
	}

	public SimpleGaugeValue(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull GaugeValueFormat format, @Nonnull String displayeName, @Nonnull String unit) {
		this(name, stored, capacity, format, displayeName, unit, 0xFFFFFFFF);
	}
	
	public SimpleGaugeValue(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull GaugeValueFormat format, @Nonnull String displayeName, @Nonnull String unit, int color) {
		this.name = name;
		this.stored = stored;
		this.capacity = capacity;
		this.format = format;
		this.displayeName = displayeName;
		this.unit = unit;
		this.color = color;
	}

	public SimpleGaugeValue(PacketBuffer buffer) {
		this.name(buffer.readResourceLocation());
		this.stored(buffer.readInt());
		this.capacity(buffer.readInt());
		this.format(new GaugeValueFormat(buffer));
		this.displayeName(buffer.readString());
		this.unit(buffer.readString());
		this.color(buffer.readInt());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeResourceLocation(this.getName());
		buffer.writeInt(this.getStored());
		buffer.writeInt(this.getCapacity());
		this.getFormat().write(buffer);
		buffer.writeString(this.getDisplayName());
		buffer.writeString(this.getUnit());
		buffer.writeInt(this.getColor());
	}

	public ResourceLocation getName() {
		return name;
	}

	public SimpleGaugeValue name(@Nonnull ResourceLocation name) {
		this.name = name;
		return this;
	}

	@Override
	public String getDisplayName() {
		return displayeName;
	}

	public SimpleGaugeValue displayeName(@Nonnull String displayeName) {
		this.displayeName = displayeName;
		return this;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	public SimpleGaugeValue unit(@Nonnull String unit) {
		this.unit = unit;
		return this;
	}

	@Override
	public int getStored() {
		return stored;
	}

	public SimpleGaugeValue stored(int stored) {
		this.stored = stored;
		return this;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	public SimpleGaugeValue capacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	@Override
	public GaugeValueFormat getFormat() {
		return format;
	}

	public SimpleGaugeValue format(@Nonnull GaugeValueFormat format) {
		this.format = format;
		return this;
	}
	
	public int getColor() {
		return color;
	}
	
	public SimpleGaugeValue color(int color) {
		this.color = color;
		return this;
	}

}
