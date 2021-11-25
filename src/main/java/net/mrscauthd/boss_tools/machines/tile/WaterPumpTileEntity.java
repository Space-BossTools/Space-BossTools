package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.waterpump.WaterPumpGui;
import net.mrscauthd.boss_tools.machines.WaterPump;

import java.util.function.Predicate;

public class WaterPumpTileEntity extends AbstractMachineTileEntity {
	public static final int TRANSFER_PER_TICK = 10;
	
    public WaterPumpTileEntity() {
        super(ModInnet.WATER_PUMP.get());
    }

    public static final ResourceLocation WATER_TANK = new ResourceLocation("boss_tools", "water_tank");
    public static final int TANK_CAPACITY = 6000;
    public double WATER_TIMER = 0;
    private FluidTank waterTank;

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new WaterPumpGui.GuiContainer(id, player, this);
    }

    @Override
    public void tick() {
        BlockPos pos = new BlockPos(this.pos.getX(),this.pos.getY() - 1, this.pos.getZ());

        if (this.world.getFluidState(pos) == Fluids.WATER.getStillFluidState(false)) {

            if (hasSpaceInWaterTank(this.getWaterTank().getFluid().getAmount())) {

                if (this.consumePowerForOperation() != null) {

                    WATER_TIMER = WATER_TIMER + 1;

                    if (WATER_TIMER > 10) {

                        ((IBucketPickupHandler) this.world.getBlockState(pos).getBlock()).pickupFluid(this.world, pos, this.world.getBlockState(pos));

                        this.getWaterTank().fill(new FluidStack(Fluids.WATER,1000), IFluidHandler.FluidAction.EXECUTE);

                        WATER_TIMER = 0;
                    }
                }
            }

            if (this.getWaterTank().getFluid().getAmount() > 1) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(this.pos.getX(),this.pos.getY() + 1, this.pos.getZ()));

                if (tileEntity != null) {

                    if (getFluidTankAmount(tileEntity) < getFluidTankCapacity(tileEntity)) {
                        if (this.consumePowerForOperation() != null) {

                            tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(capability -> capability.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE));

                            this.getWaterTank().drain(new FluidStack(Fluids.WATER, this.getTransferPerTick()), IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                }
            }
        }
    }
    
    public int getTransferPerTick() {
		return TRANSFER_PER_TICK;
	}

    public int getFluidTankCapacity(TileEntity tileEntity) {
        return tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(fluid -> fluid.getTankCapacity(1)).orElse(0);
    }

    public int getFluidTankAmount(TileEntity tileEntity) {
        return tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(fluid -> fluid.getFluidInTank(1).getAmount()).orElse(0);
    }

    @Override
    protected void tickProcessing() {
    }

    public boolean hasSpaceInWaterTank(int water) {
        return hasSpaceIn(water, this.getWaterTank().getFluid());
    }

    public boolean hasSpaceIn(int water, FluidStack storage) {
        return water < TANK_CAPACITY - 999;
    }

    @Override
    public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, Direction facing) {
        BlockState blockState = this.getBlockState();

        if (blockState.hasProperty(WaterPump.FACING)) {
            Direction blockDirection = blockState.get(WaterPump.FACING);

            if (facing == null || facing == blockDirection) {
                return super.getCapabilityEnergy(capability, facing);
            }
        }

        return LazyOptional.empty();
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
    protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry) {
        super.createFluidHandlers(registry);
        this.waterTank = (FluidTank) registry.computeIfAbsent(this.getTankName(), k -> this.creatTank(k));
    }

    protected int getInitialTankCapacity(ResourceLocation name) {
        return TANK_CAPACITY;
    }

    protected Predicate<FluidStack> getInitialTankValidator(ResourceLocation name) {
        Fluid fluid = this.getTankFluid(name);
        return fluid != null ? fs -> FluidUtil2.isEquivalentTo(fs, fluid) : null;
    }

    protected Fluid getTankFluid(ResourceLocation name) {
        if (name.equals(this.getTankName())) {
            return Fluids.WATER;
        } else {
            return null;
        }
    }

    protected FluidTank creatTank(ResourceLocation name) {
        return new FluidTank(this.getInitialTankCapacity(name), this.getInitialTankValidator(name)) {
            @Override
            protected void onContentsChanged() {
                super.onContentsChanged();
                WaterPumpTileEntity.this.markDirty();
            }
        };
    }

    public ResourceLocation getTankName() {
        return WATER_TANK;
    }

    public FluidTank getWaterTank() {
        return this.waterTank;
    }

    @Override
    public boolean hasSpaceInOutput() {
        return false;
    }

}
