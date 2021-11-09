package net.mrscauthd.boss_tools.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.mrscauthd.boss_tools.machines.tile.WaterPumpTileEntity;

public class WaterPump extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public WaterPump(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(world, pos);
        switch (state.get(FACING)) {
            case SOUTH :
            default :
                return VoxelShapes.or(makeCuboidShape(5.5, 0, 10.5, 10.5, 1, 5.5), makeCuboidShape(6, 1, 10, 10, 13, 6), makeCuboidShape(5.5, 13, 10.5, 10.5, 16, 5.5), makeCuboidShape(6, 6, 15, 10, 10, 6), makeCuboidShape(5.5, 5.5, 16, 10.5, 10.5, 15)).withOffset(offset.x, offset.y, offset.z);
                case NORTH :
                return VoxelShapes.or(makeCuboidShape(5.5, 0, 10.5, 10.5, 1, 5.5), makeCuboidShape(6, 1, 10, 10, 13, 6), makeCuboidShape(5.5, 13, 10.5, 10.5, 16, 5.5), makeCuboidShape(6, 6, 1, 10, 10, 6), makeCuboidShape(5.5, 5.5, 1, 10.5, 10.5, 0)).withOffset(offset.x, offset.y, offset.z);
            case EAST :
                return VoxelShapes.or(makeCuboidShape(5.5, 0, 10.5, 10.5, 1, 5.5), makeCuboidShape(6, 1, 10, 10, 13, 6), makeCuboidShape(5.5, 13, 10.5, 10.5, 16, 5.5), makeCuboidShape(15, 6, 10, 10, 10, 6), makeCuboidShape(16, 5.5, 10.5, 15, 10.5, 5.5)).withOffset(offset.x, offset.y, offset.z);
            case WEST :
                return VoxelShapes.or(makeCuboidShape(5.5, 0, 10.5, 10.5, 1, 5.5), makeCuboidShape(6, 1, 10, 10, 13, 6), makeCuboidShape(5.5, 13, 10.5, 10.5, 16, 5.5), makeCuboidShape(1, 6, 6, 10, 10, 10), makeCuboidShape(0, 5.5, 5.5, 1, 10.5, 10.5)).withOffset(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WaterPumpTileEntity();
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }

}
