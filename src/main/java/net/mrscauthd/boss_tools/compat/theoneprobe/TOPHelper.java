package net.mrscauthd.boss_tools.compat.theoneprobe;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IOverlayStyle;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import mcjty.theoneprobe.config.Config;
import mcjty.theoneprobe.rendering.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;

public class TOPHelper {
	public static final Class<OverlayRenderer> overlayRendererClass;
	@Nullable
	public static final Field lastPairField;

	static {
		overlayRendererClass = OverlayRenderer.class;

		Field lastPairFieldTemp = null;

		try {
			lastPairFieldTemp = overlayRendererClass.getDeclaredField("lastPair");
			lastPairFieldTemp.setAccessible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		lastPairField = lastPairFieldTemp;
	}

	public static RayTraceResult getMouseOver(float partialTicks) {
		double dist = (Double) Config.probeDistance.get();
		Minecraft mc = Minecraft.getInstance();
		RayTraceResult mouseOver = mc.objectMouseOver;

		if (mouseOver != null && mouseOver.getType() == Type.ENTITY) {
			return mouseOver;
		} else {
			PlayerEntity entity = mc.player;
			Vector3d start = entity.getEyePosition(partialTicks);
			Vector3d vec31 = entity.getLook(partialTicks);
			Vector3d end = start.add(vec31.x * dist, vec31.y * dist, vec31.z * dist);
			RayTraceContext context = new RayTraceContext(start, end, BlockMode.OUTLINE, (Boolean) Config.showLiquids.get() ? FluidMode.ANY : FluidMode.NONE, entity);
			RayTraceResult mouseOver2 = entity.getEntityWorld().rayTraceBlocks(context);

			if (mouseOver2 != null && mouseOver2.getType() == Type.BLOCK) {
				return mouseOver;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static Pair<Long, ProbeInfo> getLastPair() {
		try {
			return (Pair<Long, ProbeInfo>) lastPairField.get(overlayRendererClass);
		} catch (Exception e) {
			return null;
		}
	}

	public static int getOxygenOverlayOffset(float partialTicks) {
		try {
			Pair<Long, ProbeInfo> lastPair = getLastPair();

			if (lastPair == null) {
				return 0;
			}

			RayTraceResult mouseOver = getMouseOver(partialTicks);

			if (mouseOver != null) {
				IOverlayStyle style = TheOneProbe.theOneProbeImp.getOverlayRenderer().createDefaultStyle();

				// TOP overlay placed leftTop
				if (style.getTopY() != -1 && style.getLeftX() != -1) {
					int offset = style.getTopY() + lastPair.getValue().getHeight();
					int borderThick = style.getBorderThickness();

					if (borderThick > 0) {
						int borderOffset = style.getBorderOffset();
						offset += borderOffset + borderThick + 5;
					}

					return offset;
				}
			}

		} catch (Exception e) {
		}

		return 0;
	}
}
