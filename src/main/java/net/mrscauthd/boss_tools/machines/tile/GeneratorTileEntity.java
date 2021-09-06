package net.mrscauthd.boss_tools.machines.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.capability.EnergyStorageExtractaOnly;

public abstract class GeneratorTileEntity extends AbstractMachineTileEntity {

	private IEnergyStorage internalEnergyStorage;
	private IEnergyStorage energyStorage;

	protected GeneratorTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	protected void tickProcessing() {
		if (this.canGenerateEnergy() && this.hasSpaceInOutput() && this.consumePowerForOperation() != null) {
			this.generateEnergy();
		}

		this.ejectEnergy();
	}

	protected abstract boolean canGenerateEnergy();

	protected abstract void generateEnergy();

	protected void generateEnergy(int energy) {
		this.getInternalEnergyStorage().receiveEnergy(energy, false);
		this.setProcessedInThisTick();
	}

	@Override
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
		super.createEnergyStorages(registry);

		this.internalEnergyStorage = this.createGeneratingEnergyStorage();
		this.energyStorage = new EnergyStorageExtractaOnly(this.internalEnergyStorage, this.internalEnergyStorage.getMaxEnergyStored());
		registry.put(this.internalEnergyStorage);
	}

	protected IEnergyStorage createGeneratingEnergyStorage() {
		return this.createEnergyStorageCommon();
	}

	protected void ejectEnergy() {
		for (Direction direction : this.getEjectDirections()) {
			this.ejectEnergy(direction);
		}
	}

	protected int getAutoEjectingEnergy() {
		return 1;
	}

	protected int ejectEnergy(Direction direction) {
		IEnergyStorage source = this.getGeneratingEnergyStorage();
		World world = this.getWorld();
		BlockPos pos = this.getPos();

		TileEntity tileEntity = world.getTileEntity(pos.offset(direction));

		if (tileEntity != null) {
			LazyOptional<IEnergyStorage> capability = tileEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite());

			if (capability != null && capability.isPresent()) {
				IEnergyStorage sink = capability.resolve().get();
				int extractEnergy = source.extractEnergy(this.getAutoEjectingEnergy(), true);
				int receiveEnergy = sink.receiveEnergy(extractEnergy, true);

				int real = source.extractEnergy(receiveEnergy, false);
				return sink.receiveEnergy(real, false);
			}
		}
		return 0;
	}

	protected List<Direction> getEjectDirections() {
		return new ArrayList<>();
	}

	@Override
	public boolean hasSpaceInOutput() {
		return this.getInternalEnergyStorage().receiveEnergy(1, true) > 0;
	}

	@Override
	public IEnergyStorage getPrimaryEnergyStorage() {
		return this.getGeneratingEnergyStorage();
	}

	protected IEnergyStorage getInternalEnergyStorage() {
		return this.internalEnergyStorage;
	}

	public IEnergyStorage getGeneratingEnergyStorage() {
		return this.energyStorage;
	}

}
