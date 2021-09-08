package net.mrscauthd.boss_tools.gauge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGaugeData extends INBTSerializable<CompoundNBT> {

	public default ITextComponent getText() {
		return this.getFormat().getText(this.getValue());
	}

	public default double getDisplayRatio() {
		return this.getFormat().getDisplayRatio(this.getValue());
	}

	IGaugeValue getValue();

	IGaugeFormat getFormat();

	void read(PacketBuffer buffer);

	void write(PacketBuffer buffer);

}