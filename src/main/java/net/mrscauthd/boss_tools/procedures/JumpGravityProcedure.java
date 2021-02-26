package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.entity.LivingEntity;

import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class JumpGravityProcedure extends BossToolsModElements.ModElement {
	public JumpGravityProcedure(BossToolsModElements instance) {
		super(instance, 490);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@SubscribeEvent
	public void onEntityJump(LivingEvent.LivingJumpEvent event) {
		LivingEntity entity = event.getEntityLiving();
		double i = entity.getPosX();
		double j = entity.getPosY();
		double k = entity.getPosZ();
		World world = entity.world;
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
