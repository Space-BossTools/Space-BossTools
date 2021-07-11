package net.mrscauthd.boss_tools.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;

public class SetPlanetWeatherProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onWorldTick(TickEvent.WorldTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				IWorld world = event.world;
				if ((((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_venus")))) || ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_overworld")))))))))))) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).setRainStrength(0);
						((ServerWorld) world).setThunderStrength(0);
					}
				}
				if ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus")))) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).setThunderStrength(0);
					}
				}
			}
		}
	}
}
