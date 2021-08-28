package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class PowerSystemEnergy extends PowerSystem implements IEnergyStorageDelegateHolder {
	private final IEnergyStorageExtends energyStorage;

	public PowerSystemEnergy(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);

		this.energyStorage = this.createEnergyStorage();
	}

	public boolean canProvideCapability() {
		return true;
	}

	@Override
	public int getStored() {
		return this.getEnergyStorage().getEnergyStored();
	}

	@Override
	public int getCapacity() {
		return this.getEnergyStorage().getMaxEnergyStored();
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return this.getEnergyStorage().receiveEnergy(amount, simulate);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return this.getEnergyStorage().extractEnergy(amount, simulate);
	}

	@Override
	public void onEnergyReceived(IEnergyStorage energyStorage, int energyReceived) {
		this.getTileEntity().markDirty();
	}

	@Override
	public void onEnergyExtracted(IEnergyStorage energyStorage, int energyExtracted) {
		this.getTileEntity().markDirty();
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		this.getEnergyStorage().read(compound.getCompound("energyStorage"));
	}

	@Override
	public CompoundNBT write() {
		CompoundNBT compound = super.write();

		compound.put("energyStorage", this.getEnergyStorage().write());

		return compound;
	}

	protected IEnergyStorageExtends createEnergyStorage() {
		return new EnergyStorageDelegate(this);
	}

	public final IEnergyStorageExtends getEnergyStorage() {
		return this.energyStorage;
	}

}
