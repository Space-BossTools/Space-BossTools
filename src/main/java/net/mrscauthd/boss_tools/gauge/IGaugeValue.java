package net.mrscauthd.boss_tools.gauge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGaugeValue extends INBTSerializable<CompoundNBT> {

	String getDisplayName();

	String getUnit();

	int getStored();

	int getCapacity();

}
