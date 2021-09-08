package net.mrscauthd.boss_tools.compat.hwyla;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {

	public static final ResourceLocation TOOLTIP = new ResourceLocation("boss_tools", "hwlya_tooltip");

	@Override
	public void register(IRegistrar registrar) {
		ServerDataProvider dataProvider = new ServerDataProvider();
		TooltipRenderer dataProvider2 = new TooltipRenderer();

		registrar.registerBlockDataProvider(dataProvider, TileEntity.class);
		registrar.registerComponentProvider(dataProvider2, TooltipPosition.BODY, TileEntity.class);
		registrar.registerTooltipRenderer(TOOLTIP, dataProvider2);
	}
}