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
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.mrscauthd.boss_tools.gauge.FluidStackGaugeValue;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.gauge.SimpleGaugeValue;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystem;

public class ProbeInfoProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {

	public static int FLUID_ELEMENT_ID;
	public static int SIMPLE_ELEMENT_ID;

	public ProbeInfoProvider() {

	}

	@Override
	public Void apply(ITheOneProbe top) {
		top.registerProvider(this);
		top.registerProbeConfigProvider(ProbeConfigProvider.INSTANCE);
		FLUID_ELEMENT_ID = top.registerElementFactory(FluidGaugeElement::new);
		SIMPLE_ELEMENT_ID = top.registerElementFactory(SimpleGaugeElement::new);

		return null;
	}

	@Override
	public void addProbeInfo(ProbeMode probeMod, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData hitData) {
		TileEntity tileEntity = world.getTileEntity(hitData.getPos());

		if (tileEntity instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) tileEntity;
			machineTileEntity.getPowerSystems().values().forEach(h -> this.elementPowerSystem(probeInfo, h));
			machineTileEntity.getFluidHandlers().values().forEach(h -> this.elementFluidHandler(probeInfo, h));
			machineTileEntity.getGaugeValues().forEach(h -> this.elementGauge(probeInfo, h));
		}
	}

	private void elementPowerSystem(IProbeInfo probeInfo, PowerSystem powerSystem) {
		this.elementGauge(probeInfo, powerSystem.getGaugeValue());
	}

	private void elementFluidHandler(IProbeInfo probeInfo, IFluidHandler fluidHandler) {
		for (int i = 0; i < fluidHandler.getTanks(); i++) {
			this.elementGauge(probeInfo, GaugeValueHelper.getFluid(fluidHandler.getFluidInTank(i), fluidHandler.getTankCapacity(i)));
		}
	}

	private void elementGauge(IProbeInfo probeInfo, IGaugeValue gaugeValue) {
		if (gaugeValue instanceof FluidStackGaugeValue) {
			probeInfo.element(new FluidGaugeElement((FluidStackGaugeValue) gaugeValue));
		} else if (gaugeValue instanceof SimpleGaugeValue) {
			probeInfo.element(new SimpleGaugeElement((SimpleGaugeValue) gaugeValue));
		}

	}

	@Override
	public String getID() {
		return TOPCompat.rl("top").toString();
	}

}
