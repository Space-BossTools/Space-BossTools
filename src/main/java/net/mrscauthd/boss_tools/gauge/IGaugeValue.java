package net.mrscauthd.boss_tools.gauge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGaugeValue extends INBTSerializable<CompoundNBT> {

	ITextComponent getDisplayName();

	String getUnit();

	int getAmount();

	int getCapacity();
	
	boolean isReverse();

}
