package net.mrscauthd.boss_tools.machines.machinetileentities;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractMachineTileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {

	public AbstractMachineTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	public void markDirty() {
		super.markDirty();

		World world = this.getWorld();
		if (world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.getChunkProvider().markBlockChanged(this.getPos());
		}
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(this.getBlockState(), pkt.getNbtCompound());
	}

	public abstract boolean isIngredientReady();

}
