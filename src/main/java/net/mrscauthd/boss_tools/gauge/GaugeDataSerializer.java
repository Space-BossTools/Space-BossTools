package net.mrscauthd.boss_tools.gauge;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class GaugeDataSerializer<T extends INBTSerializable<CompoundNBT>> {

	public static final GaugeDataSerializer<IGaugeValue> GaugeValue = new GaugeDataSerializer<>();
	public static final GaugeDataSerializer<IGaugeFormat> GaugeFormat = new GaugeDataSerializer<>();

	static {
		GaugeValue.add(new ResourceLocation("boss_tools", "fluidstack"), GaugeValueFluidStack.class);
		GaugeValue.add(new ResourceLocation("boss_tools", "simple"), GaugeValueSimple.class);

		GaugeFormat.add(new ResourceLocation("boss_tools", "standard"), GaugeFormat.class);
	}

	private final Map<ResourceLocation, Class<? extends T>> location_class_map = new LinkedHashMap<>();
	private final Map<Class<? extends T>, ResourceLocation> class_location_map = new LinkedHashMap<>();

	private GaugeDataSerializer() {

	}

	public T deserialize(CompoundNBT compound) {
		String locationToString = compound.getString("location");
		CompoundNBT valueNBT = compound.getCompound("value");
		Class<? extends T> clazz = this.location_class_map.get(new ResourceLocation(locationToString));

		T format = null;

		try {
			format = clazz.getConstructor().newInstance();
			format.deserializeNBT(valueNBT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			new CrashReport("deserialize exception", e).getCompleteReport();
		}

		return format;
	}

	public T read(PacketBuffer buffer) {
		return deserialize(buffer.readCompoundTag());
	}

	public CompoundNBT serialize(T format) {
		CompoundNBT compound = new CompoundNBT();
		compound.putString("location", this.class_location_map.get(format.getClass()).toString());
		compound.put("value", format.serializeNBT());
		return compound;
	}

	public void write(T format, PacketBuffer buffer) {
		buffer.writeCompoundTag(this.serialize(format));
	}

	public void add(ResourceLocation location, Class<? extends T> clazz) {
		this.location_class_map.put(location, clazz);
		this.class_location_map.put(clazz, location);
	}

}
