package net.mrscauthd.boss_tools.machines.tile;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;

public abstract class OxygenUsingTileEntity extends AbstractMachineTileEntity {

	private PowerSystemFuelOxygen oxygenPowerSystem;

	public OxygenUsingTileEntity(TileEntityType<?> type) {
		super(type);
	}

	public abstract int getBaseOxygenForOperation();

	public BossToolsRecipeType<? extends OxygenMakingRecipe> getOxygenMakingRecipeType() {
		return BossToolsRecipeTypes.OXYGENMAKING;
	}

	public abstract int getActivatingSlot();

	@Override
	protected void tickProcessing() {
		if (this.canUsingOxygen() && this.hasSpaceInOutput()) {
			Map<PowerSystem, Integer> consumes = this.consumePowerForOperation();

			if (consumes != null) {
				int consumedOxygen = consumes.get(this.oxygenPowerSystem).intValue();

				this.onUsingOxygen(consumedOxygen);
				this.setProcessedInThisTick();
			}
		}
	}

	protected abstract boolean canUsingOxygen();

	protected abstract void onUsingOxygen(int consumedOxygen);

	public boolean hasSpaceInOutput(IOxygenStorage oxygenStorage) {
		return oxygenStorage != null ? oxygenStorage.getOxygenStored() < oxygenStorage.getMaxOxygenStored() : false;
	}

	public IOxygenStorage getItemOxygenStorage(ItemStack itemStack) {
		return itemStack.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
	}

	@Override
	protected void createPowerSystems(PowerSystemMap map) {
		super.createPowerSystems(map);
		map.put(this.oxygenPowerSystem = new PowerSystemFuelOxygen(this, this.getActivatingSlot()) {
			@Override
			public int getBasePowerForOperation() {
				return OxygenUsingTileEntity.this.getBaseOxygenForOperation();
			}
		});
	}

	public PowerSystemFuelOxygen getOxygenPowerSystem() {
		return this.oxygenPowerSystem;
	}

}
