package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.ModInnet;

public class WaterPumpTileEntity extends AbstractMachineTileEntity {
    public WaterPumpTileEntity() {
        super(ModInnet.WATER_PUMP.get());
    }

    public static double WATER_TIMER = 0;

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }

    @Override
    public void tick() {
        BlockPos pos = new BlockPos(this.pos.getX(),this.pos.getY() - 1, this.pos.getZ());

        if (this.world.getBlockState(pos).getBlock() == Blocks.WATER) {

            if (this.consumePowerForOperation() != null) {

                WATER_TIMER = WATER_TIMER + 1;

                if (WATER_TIMER > 10) {

                    ((IBucketPickupHandler) this.world.getBlockState(pos).getBlock()).pickupFluid(this.world, pos, this.world.getBlockState(pos));
                    WATER_TIMER = 0;
                }
            }
        }
    }

    @Override
    protected void tickProcessing() {
    }

    @Override
    protected void createPowerSystems(PowerSystemRegistry map) {
        super.createPowerSystems(map);

        map.put(new PowerSystemEnergyCommon(this) {
            @Override
            public int getBasePowerForOperation() {
                return WaterPumpTileEntity.this.getBasePowerForOperation();
            }
        });
    }

    @Override
    protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
        super.createEnergyStorages(registry);
        registry.put(this.createEnergyStorageCommon());
    }

    public int getBasePowerForOperation() {
        return 1;
    }

    @Override
    public boolean hasSpaceInOutput() {
        return false;
    }

}
