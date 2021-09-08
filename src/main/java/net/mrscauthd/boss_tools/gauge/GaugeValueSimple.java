package net.mrscauthd.boss_tools.gauge;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class GaugeValueSimple implements IGaugeValue {

	private ResourceLocation name;
	private int stored;
	private int capacity;
	private String displayeName;
	private String unit;
	private int color;

	public GaugeValueSimple() {
		this(null);
	}

	public GaugeValueSimple(@Nonnull ResourceLocation name) {
		this(name, 0, 0);
	}

	public GaugeValueSimple(@Nonnull ResourceLocation name, int stored, int capacity) {
		this(name, stored, capacity, "");
	}

	public GaugeValueSimple(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull String displayeName) {
		this(name, stored, capacity, displayeName, "");
	}

	public GaugeValueSimple(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull String displayeName, @Nonnull String unit) {
		this(name, stored, capacity, displayeName, unit, 0xA0FFFFFF);
	}

	public GaugeValueSimple(@Nonnull ResourceLocation name, int stored, int capacity, @Nonnull String displayeName, @Nonnull String unit, int color) {
		this.name = name;
		this.stored = stored;
		this.capacity = capacity;
		this.displayeName = displayeName;
		this.unit = unit;
		this.color = color;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putString("name", this.getName().toString());
		compound.putInt("stored", this.getStored());
		compound.putInt("capacity", this.getCapacity());
		compound.putString("displayName", this.getDisplayName());
		compound.putString("unit", this.getUnit());
		compound.putInt("color", this.getColor());

		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		this.name(new ResourceLocation(compound.getString("name")));
		this.stored(compound.getInt("stored"));
		this.capacity(compound.getInt("capacity"));
		this.displayeName(compound.getString("displayName"));
		this.unit(compound.getString("unit"));
		this.color(compound.getInt("color"));
	}

	public ResourceLocation getName() {
		return name;
	}

	public GaugeValueSimple name(@Nonnull ResourceLocation name) {
		this.name = name;
		return this;
	}

	@Override
	public String getDisplayName() {
		return displayeName;
	}

	public GaugeValueSimple displayeName(@Nonnull String displayeName) {
		this.displayeName = displayeName;
		return this;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	public GaugeValueSimple unit(@Nonnull String unit) {
		this.unit = unit;
		return this;
	}

	@Override
	public int getStored() {
		return stored;
	}

	public GaugeValueSimple stored(int stored) {
		this.stored = stored;
		return this;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	public GaugeValueSimple capacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public int getColor() {
		return color;
	}

	public GaugeValueSimple color(int color) {
		this.color = color;
		return this;
	}

}
