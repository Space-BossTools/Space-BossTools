package net.mrscauthd.boss_tools.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class ServerDataProvider implements IServerDataProvider<TileEntity> {

	public static final ResourceLocation DATA_KEY = new ResourceLocation("boss_tools", "datakey");
	public static final ServerDataProvider INSTANCE = new ServerDataProvider();

	public static ListNBT write(List<GaugeData> list) {
		ListNBT nbt = new ListNBT();
		list.stream().map(GaugeData::serializeNBT).forEach(nbt::add);
		return nbt;
	}

	public static List<GaugeData> read(ListNBT nbt) {
		List<GaugeData> list = new ArrayList<>();
		nbt.stream().map(h -> (CompoundNBT) h).map(GaugeData::new).forEach(list::add);
		return list;
	}

	public static ListNBT get(CompoundNBT compound) {
		return compound.getList(DATA_KEY.toString(), NBT.TAG_COMPOUND);
	}

	public static void put(CompoundNBT compound, ListNBT nbt) {
		compound.put(DATA_KEY.toString(), nbt);
	}

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity t) {
		if (t instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) t;

			List<GaugeData> list = machineTileEntity.getGaugeDataList();
			IEnergyStorage energyStorage = machineTileEntity.getPrimaryEnergyStorage();

			if (energyStorage != null) {
				list.add(0, GaugeDataHelper.getEnergy(energyStorage));
			}

			put(data, write(list));
		}
	}
}
