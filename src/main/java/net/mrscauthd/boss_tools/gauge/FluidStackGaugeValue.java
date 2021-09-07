package net.mrscauthd.boss_tools.gauge;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackGaugeValue implements IGaugeValue {

	private FluidStack stack;
	private int capacity;
	private GaugeValueFormat format;

	public FluidStackGaugeValue() {
		super();
	}

	public FluidStackGaugeValue(FluidStack stack, int capacity, GaugeValueFormat format) {
		this.setStack(stack);
		this.setCapacity(capacity);
		this.setFormat(format);
	}

	public FluidStackGaugeValue(PacketBuffer buffer) {
		this.setStack(buffer.readFluidStack());
		this.setCapacity(buffer.readInt());
		this.setFormat(new GaugeValueFormat(buffer));
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeFluidStack(this.getStack());
		buffer.writeInt(this.getCapacity());
		this.getFormat().write(buffer);
	}

	public FluidStack getStack() {
		return this.stack;
	}

	public void setStack(FluidStack stack) {
		this.stack = stack;
	}

	@Override
	public String getDisplayName() {
		return this.getStack().getDisplayName().getString();
	}

	@Override
	public String getUnit() {
		return "mb";
	}

	@Override
	public int getStored() {
		return this.getStack().getAmount();
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public GaugeValueFormat getFormat() {
		return format;
	}

	public void setFormat(GaugeValueFormat format) {
		this.format = format;
	}

}
