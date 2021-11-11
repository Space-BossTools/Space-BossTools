package net.mrscauthd.boss_tools.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexConsumer;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;

@OnlyIn(Dist.CLIENT)
public class TileEntityBoxRenderer extends TileEntityRenderer<OxygenBubbleDistributorBlock.CustomTileEntity> {

	public TileEntityBoxRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public boolean isGlobalRenderer(OxygenBubbleDistributorBlock.CustomTileEntity te) {
		return true;
	}

	@Override
	public void render(OxygenBubbleDistributorBlock.CustomTileEntity tileEntityIn, float partialTicks, MatrixStack matrix, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (tileEntityIn.isWorkingAreaVisible()) {
			IVertexConsumer builder = (IVertexConsumer) bufferIn.getBuffer(RenderType.getLines());
			AxisAlignedBB workingArea = tileEntityIn.getWorkingArea(BlockPos.ZERO, tileEntityIn.getRange());
			// 255 is default
			int rl = 78;
			int gl = 197;
			int bl = 231;

			int r = 41;
			int g = 148;
			int b = 204;

			RenderHelper.renderBox(matrix, bufferIn, builder, workingArea, rl, gl, bl, r, g, b);
		}
	}
}
