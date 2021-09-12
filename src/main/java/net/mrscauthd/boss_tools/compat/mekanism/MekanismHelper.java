package net.mrscauthd.boss_tools.compat.mekanism;

import mekanism.api.Action;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismGases;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MekanismHelper {

	public static int extracteNearOxygen(TileEntity tileEntity, int amount, boolean simulate) {
		BlockPos pos = tileEntity.getPos();
		World world = tileEntity.getWorld();
		int extractedAmount = 0;

		for (Direction direction : Direction.values()) {
			if (amount <= 0) {
				break;
			} else {
				BlockPos nearPos = pos.offset(direction);
				TileEntity nearTileEntity = world.getTileEntity(nearPos);

				if (nearTileEntity != null) {
					IGasHandler gasHandler = nearTileEntity.getCapability(Capabilities.GAS_HANDLER_CAPABILITY, direction.getOpposite()).orElse(null);

					if (gasHandler != null) {
						GasStack extracted = gasHandler.extractChemical(new GasStack(MekanismGases.OXYGEN, amount), Action.get(!simulate));
						amount -= extracted.getAmount();
						extractedAmount += extracted.getAmount();
					}
				}
			}
		}

		return extractedAmount;
	}

	private MekanismHelper() {

	}
}
