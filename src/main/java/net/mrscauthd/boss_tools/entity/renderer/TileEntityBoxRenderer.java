package net.mrscauthd.boss_tools.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.OxygenGeneratorBlock;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class TileEntityBoxRenderer extends TileEntityRenderer<OxygenGeneratorBlock.CustomTileEntity> {

    private Minecraft mc = Minecraft.getInstance();
    World world = mc.world;

    public TileEntityBoxRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(OxygenGeneratorBlock.CustomTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockPos blockpos = tileEntityIn.getPos();
        BlockState blockstate = Minecraft.getInstance().world.getBlockState(blockpos);
        if (blockstate.isAir(Minecraft.getInstance().world, blockpos) || !Minecraft.getInstance().world.getWorldBorder().contains(blockpos)) return;

        final Color SHAPE_COLOR = Color.BLUE;
        final Color RENDERSHAPE_COLOR = Color.BLUE;
        final Color COLLISIONSHAPE_COLOR = Color.BLUE;
        final Color RAYTRACESHAPE_COLOR = Color.BLUE;


        ActiveRenderInfo activeRenderInfo = renderDispatcher.renderInfo;
        ISelectionContext iSelectionContext = ISelectionContext.forEntity(activeRenderInfo.getRenderViewEntity());
        IRenderTypeBuffer renderTypeBuffers = bufferIn;
        MatrixStack matrixStack = matrixStackIn;

        VoxelShape shape = Block.makeCuboidShape(64, 64, 64, -32, -32, -32);

        drawSelectionBox(renderTypeBuffers, matrixStack, blockpos, activeRenderInfo, shape, SHAPE_COLOR);
        shape = blockstate.getRenderShape(world, blockpos);
        drawSelectionBox(renderTypeBuffers, matrixStack, blockpos, activeRenderInfo, shape, RENDERSHAPE_COLOR);
        shape = blockstate.getCollisionShape(world, blockpos, iSelectionContext);
        drawSelectionBox(renderTypeBuffers, matrixStack, blockpos, activeRenderInfo, shape, COLLISIONSHAPE_COLOR);
        shape = blockstate.getRaytraceShape(world, blockpos, iSelectionContext);
        drawSelectionBox( renderTypeBuffers, matrixStack, blockpos, activeRenderInfo, shape, RAYTRACESHAPE_COLOR);
    }

    private static void drawSelectionBox(IRenderTypeBuffer renderTypeBuffers, MatrixStack matrixStack, BlockPos blockPos, ActiveRenderInfo activeRenderInfo, VoxelShape shape, Color color) {
        RenderType renderType = RenderType.getLines();
        IVertexBuilder vertexBuilder = renderTypeBuffers.getBuffer(renderType);

        double eyeX = activeRenderInfo.getProjectedView().getX();
        double eyeY = activeRenderInfo.getProjectedView().getY();
        double eyeZ = activeRenderInfo.getProjectedView().getZ();
        final float ALPHA = 0.5f;
        drawShapeOutline(matrixStack, vertexBuilder, shape, blockPos.getX() - eyeX, blockPos.getY() - eyeY, blockPos.getZ() - eyeZ, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, ALPHA);
    }

    private static void drawShapeOutline(MatrixStack matrixStack, IVertexBuilder vertexBuilder, VoxelShape voxelShape, double originX, double originY, double originZ, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        voxelShape.forEachEdge((x0, y0, z0, x1, y1, z1) -> {
            vertexBuilder.pos(matrix4f, (float)(x0 + originX), (float)(y0 + originY), (float)(z0 + originZ)).color(red, green, blue, alpha).endVertex();
            vertexBuilder.pos(matrix4f, (float)(x1 + originX), (float)(y1 + originY), (float)(z1 + originZ)).color(red, green, blue, alpha).endVertex();
        });
    }

}
