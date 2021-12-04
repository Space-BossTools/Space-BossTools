package net.mrscauthd.boss_tools.compat.hwyla;

import java.awt.Dimension;

import com.mojang.blaze3d.matrix.MatrixStack;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;
import net.mrscauthd.boss_tools.gauge.GaugeValueSerializer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class TooltipRenderer implements ITooltipRenderer {

	public static final TooltipRenderer INSTANCE = new TooltipRenderer();

	@Override
	public Dimension getSize(CompoundNBT compound, ICommonAccessor accessor) {
		return new Dimension(102, 15 * HwylaPlugin.get(compound).size());
	}

	@Override
	public void draw(CompoundNBT compound, ICommonAccessor accessor, int x, int y) {
		ListNBT list = HwylaPlugin.get(compound);
		MatrixStack matrix = new MatrixStack();

		for (int i = 0; i < list.size(); i++) {
			IGaugeValue value = GaugeValueSerializer.Serializer.deserialize(list.getCompound(i));
			GaugeValueRenderer renderer = new GaugeValueRenderer(value);
			renderer.render(matrix, x + 1, y);
			y += renderer.getHeight() + 1;
		}
	}
}
