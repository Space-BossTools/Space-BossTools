package net.mrscauthd.boss_tools.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityOxygen {
	@CapabilityInject(IOxygenStorage.class)
	public static Capability<IOxygenStorage> OXYGEN = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(OxygenStorage.class, new IStorage<OxygenStorage>() {
			@Override
			public void readNBT(Capability<OxygenStorage> capability, OxygenStorage instance, Direction direction, INBT nbt) {
				if (nbt instanceof CompoundNBT) {
					CompoundNBT compound = (CompoundNBT) nbt;
					instance.oxygen = compound.getInt("oxygen");
				}
			}

			@Override
			public INBT writeNBT(Capability<OxygenStorage> capability, OxygenStorage instance, Direction direction) {
				CompoundNBT compound = new CompoundNBT();
				compound.putInt("oxygen", instance.oxygen);
				return compound;
			}
		}, () -> {
			return new OxygenStorage(null, 1000);
		});
	}
}
