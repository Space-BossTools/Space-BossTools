package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import java.util.Map;
import java.util.HashMap;

public class RocketHighCheckProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketHighCheck!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure RocketHighCheck!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure RocketHighCheck!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure RocketHighCheck!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure RocketHighCheck!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((((entity.getRidingEntity()) instanceof RocketTier1Entity.CustomEntity) == (true))) {
			if ((((entity.getRidingEntity()).getPosY()) >= 600)) {
				if ((((entity.getRidingEntity()).getPersistentData().getDouble("Powup_trigger")) == 1)) {
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/stopsound @p neutral boss_tools:rocketfly");
					}
					entity.getPersistentData().putDouble("Tier_1_open_main_menu", 1);
					entity.getPersistentData().putDouble("Player_movement", 1);
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
				}
			}
		}
		if ((((entity.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity) == (true))) {
			if ((((entity.getRidingEntity()).getPosY()) >= 600)) {
				if ((((entity.getRidingEntity()).getPersistentData().getDouble("Powup_trigger")) == 1)) {
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/stopsound @p neutral boss_tools:rocketfly");
					}
					entity.getPersistentData().putDouble("Tier_2_open_main_menu", 1);
					entity.getPersistentData().putDouble("Player_movement", 1);
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
				}
			}
		}
		if ((((entity.getRidingEntity()) instanceof RocketTier3Entity.CustomEntity) == (true))) {
			if ((((entity.getRidingEntity()).getPosY()) >= 600)) {
				if ((((entity.getRidingEntity()).getPersistentData().getDouble("Powup_trigger")) == 1)) {
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/stopsound @p neutral boss_tools:rocketfly");
					}
					entity.getPersistentData().putDouble("Tier_3_open_main_menu", 1);
					entity.getPersistentData().putDouble("Player_movement", 1);
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
				}
			}
		}
	}
}
