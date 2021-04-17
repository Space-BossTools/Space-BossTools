package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class OxygenBulletGeneratorProcedure extends BossToolsModElements.ModElement {
	public OxygenBulletGeneratorProcedure(BossToolsModElements instance) {
		super(instance, 676);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OxygenBulletGenerator!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("timer_oxygen", ((entity.getPersistentData().getDouble("timer_oxygen")) + 1));
		if (((entity.getPersistentData().getDouble("timer_oxygen")) >= 4)) {
			if (((entity.getPersistentData().getBoolean("Oxygen_Bullet_Generator")) == (true))) {
				entity.getPersistentData().putBoolean("Oxygen_Bullet_Generator", (false));
				entity.getPersistentData().putDouble("timer_oxygen", 0);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
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
			this.executeProcedure(dependencies);
		}
	}
}
