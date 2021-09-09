package net.mrscauthd.boss_tools.compat.theoneprobe;

import java.util.function.Function;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class ProbeInfoProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {

	public static int ELEMENT_ID;

	public ProbeInfoProvider() {

	}

	@Override
	public Void apply(ITheOneProbe top) {
		top.registerProvider(this);
		top.registerProbeConfigProvider(ProbeConfigProvider.INSTANCE);
		ELEMENT_ID = top.registerElementFactory(GaugeDataElement::new);

		return null;
	}

	@Override
	public void addProbeInfo(ProbeMode probeMod, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData hitData) {
		TileEntity tileEntity = world.getTileEntity(hitData.getPos());

		if (tileEntity instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) tileEntity;
			machineTileEntity.getGaugeDataList().forEach(g -> probeInfo.element(new GaugeDataElement(g)));
		}
	}

	@Override
	public String getID() {
		return new ResourceLocation("boss_tools", "top").toString();
	}
}
