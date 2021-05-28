package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.potion.EffectInstance;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.Collections;

public class RocketoverworldumlaufbahnProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Rocketoverworldumlaufbahn!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure Rocketoverworldumlaufbahn!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cWARNING! \u00A77Hold \u00A7cSPACE\u00A77."), (false));
		}
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cWARNING! \u00A77Hold \u00A7cSPACE\u00A77."), (true));
		}
		if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:orbit_overworld")))))) {
			{
				Entity _ent = entity;
				if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
					RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
							new ResourceLocation("boss_tools:orbit_overworld"));
					ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
					if (nextWorld != null) {
						((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
						((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 700, entity.getPosZ(), _ent.rotationYaw,
								_ent.rotationPitch);
						((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
						for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
							((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
						}
					}
				}
			}
		}
		if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
				new ResourceLocation("boss_tools:orbit_overworld"))))) {
			{
				Entity _ent = entity;
				_ent.setPositionAndUpdate((entity.getPosX()), 700, (entity.getPosZ()));
				if (_ent instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), 700, (entity.getPosZ()), _ent.rotationYaw,
							_ent.rotationPitch, Collections.emptySet());
				}
			}
		}
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_2", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_3", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_4", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu_back", 0);
		entity.getPersistentData().putDouble("Tier_2_open_main_menu", 0);
		entity.getPersistentData().putDouble("Player_movement", 0);
		entity.getPersistentData().putDouble("LanderSpawn", 1);
		entity.getPersistentData().putDouble("Landersit", 2);
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).closeScreen();
	}
}
