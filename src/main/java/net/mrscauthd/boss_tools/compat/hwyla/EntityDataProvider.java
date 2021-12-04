package net.mrscauthd.boss_tools.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.gauge.IGaugeValuesProvider;

public class EntityDataProvider implements IServerDataProvider<Entity>, IEntityComponentProvider {

	public static final EntityDataProvider INSTANCE = new EntityDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, Entity entity) {

		List<IGaugeValue> list = new ArrayList<>();

		if (entity instanceof IGaugeValuesProvider) {
			((IGaugeValuesProvider) entity).getGaugeValues().forEach(list::add);
		}

		HwylaPlugin.put(data, HwylaPlugin.write(list));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
		IEntityComponentProvider.super.appendBody(tooltip, accessor, config);
		HwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
