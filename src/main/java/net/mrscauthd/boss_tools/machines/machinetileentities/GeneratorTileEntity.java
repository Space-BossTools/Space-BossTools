package net.mrscauthd.boss_tools.machines.machinetileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class GeneratorTileEntity extends AbstractMachineTileEntity {

	protected GeneratorTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	protected void tickProcessing() {
		this.generateEnergy();
		this.ejectEnergy();
	}

	protected abstract void generateEnergy();

	@Override
	protected EnergyStorageBasic createEnergyStorage() {
		return new EnergyStorageBasic(this, 9000, 0, 200, 0);
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
		IEnergyStorage source = this.getEnergyStorage();
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
	public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, Direction facing) {
		return LazyOptional.of(() -> this.getEnergyStorage()).cast();
	}

}
