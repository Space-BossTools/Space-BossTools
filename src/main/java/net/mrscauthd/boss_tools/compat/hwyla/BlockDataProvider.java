package net.mrscauthd.boss_tools.compat.hwyla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.gauge.IGaugeValuesProvider;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class BlockDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider {

	public static final BlockDataProvider INSTANCE = new BlockDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity tileEntity) {

		List<IGaugeValue> list = new ArrayList<>();

		if (tileEntity instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) tileEntity;

			IEnergyStorage energyStorage = machineTileEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);

			if (energyStorage != null) {
				list.add(GaugeValueHelper.getEnergy(energyStorage));
			}

			list.addAll(machineTileEntity.getGaugeValues());
			machineTileEntity.getFluidHandlers().values().stream().map(machineTileEntity::getFluidHandlerGaugeValues).flatMap(Collection::stream).forEach(list::add);
		}

		if (tileEntity instanceof IGaugeValuesProvider) {
			((IGaugeValuesProvider) tileEntity).getGaugeValues().forEach(list::add);
		}

		HwylaPlugin.put(data, HwylaPlugin.write(list));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
		IComponentProvider.super.appendBody(tooltip, accessor, config);
		HwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
