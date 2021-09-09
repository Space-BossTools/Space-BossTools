package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import mekanism.api.Action;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismGases;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.capability.GasHandlerEmpty;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.gauge.GaugeData;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;

public abstract class PowerSystemFuelOxygen extends PowerSystemFuel {

	private boolean canExtreactNearGas;

	public PowerSystemFuelOxygen(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	@Override
	public void update() {
		super.update();

		this.canExtreactNearGas = this.extractNearGas(1, true) > 0;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (CompatibleManager.MEKANISM.isLoaded()) {
			if (capability == Capabilities.GAS_HANDLER_CAPABILITY) {
				return LazyOptional.of(() -> new GasHandlerEmpty()).cast();
			}
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		int extractedNearGas = this.extractNearGas(amount, simulate);
		int extracted = super.extract(amount - extractedNearGas, simulate);
		return extractedNearGas + extracted;
	}

	public int extractNearGas(int amount, boolean simulate) {
		if (CompatibleManager.MEKANISM.isLoaded()) {
			AbstractMachineTileEntity tileEntity = this.getTileEntity();
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
		} else {
			return 0;
		}
	}

	@Override
	public List<GaugeData> getGaugeDataList() {
		List<GaugeData> list = super.getGaugeDataList();

		if (!this.isCanExtreactNearGas()) {
			list.add(GaugeDataHelper.getOxygen(this));
		}

		return list;
	}

	public BossToolsRecipeType<? extends OxygenMakingRecipe> getRecipeType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	@Override
	protected int getFuelInternal(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		OxygenMakingRecipe recipe = this.getRecipeType().findFirst(this.getTileEntity().getWorld(), f -> f.test(fuel));
		return recipe != null ? recipe.getOxygen() : -1;
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

	public boolean isCanExtreactNearGas() {
		return this.canExtreactNearGas;
	}

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/oxygen");
	}

}
