package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.*;

public class Methodes {
    public static void PlayerFallToPlanet(PlayerEntity entity, World world) {
        if (entity.getPosY() <= 1 && !(entity.getRidingEntity() instanceof LanderEntity.CustomEntity)) {
            RegistryKey<World> world2 = world.getDimensionKey();
            //Overworld
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:overworld_orbit"))) {
                Entity _ent = entity;
                if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
                    RegistryKey<World> destinationType = World.OVERWORLD;
                    ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
                    if (nextWorld != null) {
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                        ((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), _ent.rotationYaw, _ent.rotationPitch);
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
                        for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
                            ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
                        }
                    }
                }
            }
            //Moon
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon_orbit"))) {
                Entity _ent = entity;
                if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
                    RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"));
                    ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
                    if (nextWorld != null) {
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                        ((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), _ent.rotationYaw, _ent.rotationPitch);
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
                        for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
                            ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
                        }
                    }
                }
            }
            //Mars
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars_orbit"))) {
                Entity _ent = entity;
                if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
                    RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"));
                    ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
                    if (nextWorld != null) {
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                        ((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), _ent.rotationYaw, _ent.rotationPitch);
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
                        for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
                            ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
                        }
                    }
                }
            }
            //Mercury
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury_orbit"))) {
                Entity _ent = entity;
                if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
                    RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"));
                    ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
                    if (nextWorld != null) {
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                        ((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), _ent.rotationYaw, _ent.rotationPitch);
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
                        for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
                            ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
                        }
                    }
                }
            }
            //Venus
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus_orbit"))) {
                Entity _ent = entity;
                if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
                    RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"));
                    ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
                    if (nextWorld != null) {
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                        ((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), _ent.rotationYaw, _ent.rotationPitch);
                        ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
                        for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
                            ((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
                        }
                    }
                }
            }
        }
    }

    public static boolean Nethrite_Space_Suit_Check(PlayerEntity player) {
        Boolean item3 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 3)).getItem() == ModInnet.NETHERITE_OXYGEN_MASK.get();
        Boolean item2 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 2)).getItem() == ModInnet.NETHERITE_SPACE_SUIT.get();
        Boolean item1 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 1)).getItem() == ModInnet.NETHERITE_SPACE_PANTS.get();
        Boolean item0 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 0)).getItem() == ModInnet.NETHERITE_SPACE_BOOTS.get();

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean Space_Suit_Check(PlayerEntity player) {
        Boolean item3 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 3)).getItem() == ModInnet.OXYGEN_MASK.get();
        Boolean item2 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 2)).getItem() == ModInnet.SPACE_SUIT.get();
        Boolean item1 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 1)).getItem() == ModInnet.SPACE_PANTS.get();
        Boolean item0 = player.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 0)).getItem() == ModInnet.SPACE_BOOTS.get();

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean DimCheck(World world) {
        RegistryKey<World> world2 = world.getDimensionKey();
        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon_orbit"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars_orbit"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury_orbit"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus_orbit"))
                || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:overworld_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean RocketCheckAnd(Entity entity) {
        if (entity instanceof RocketTier1Entity.CustomEntity && entity instanceof RocketTier2Entity.CustomEntity && entity instanceof RocketTier3Entity.CustomEntity) {
            return true;
        }
        return false;
    }

    public static boolean RocketCheckOr(Entity entity) {
        if (entity instanceof RocketTier1Entity.CustomEntity || entity instanceof RocketTier2Entity.CustomEntity || entity instanceof RocketTier3Entity.CustomEntity) {
            return true;
        }
        return false;
    }

    public static boolean AllVehiclesOr(Entity entity) {
        if (entity instanceof RocketTier1Entity.CustomEntity || entity instanceof RocketTier2Entity.CustomEntity || entity instanceof RocketTier3Entity.CustomEntity || entity instanceof LanderEntity.CustomEntity || entity instanceof RoverEntity.CustomEntity) {
            return true;
        }
        return false;
    }
}
