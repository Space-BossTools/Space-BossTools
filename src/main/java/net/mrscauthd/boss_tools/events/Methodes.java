package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.*;

public class Methodes {
    public static void PlayerFallToPlanet(PlayerEntity entity, ResourceLocation Planet) {
        if (entity.getPosY() <= 1 && !(entity.getRidingEntity() instanceof LanderEntity.CustomEntity) && !entity.world.isRemote) {

            RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, Planet);
            ServerWorld nextWorld = entity.getServer().getWorld(destinationType);

            if (nextWorld != null) {
                ((ServerPlayerEntity) entity).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                ((ServerPlayerEntity) entity).teleport(nextWorld, entity.getPosX(), 450, entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
                ((ServerPlayerEntity) entity).connection.sendPacket(new SPlayerAbilitiesPacket(entity.abilities));

                for (EffectInstance effectinstance : entity.getActivePotionEffects()) {
                    ((ServerPlayerEntity) entity).connection.sendPacket(new SPlayEntityEffectPacket(entity.getEntityId(), effectinstance));
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

    public static boolean RocketCheckOr(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity.CustomEntity || entity instanceof RocketTier3Entity.CustomEntity) {
            return true;
        }
        return false;
    }

    public static boolean AllVehiclesOr(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity.CustomEntity || entity instanceof RocketTier3Entity.CustomEntity || entity instanceof LanderEntity.CustomEntity || entity instanceof RoverEntity.CustomEntity) {
            return true;
        }
        return false;
    }

    public static void RocketSounds(World world, BlockPos pos) {
        if (!world.isRemote()) {
            world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boss_tools:rocketfly")), SoundCategory.NEUTRAL,3,1);
        } else {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boss_tools:rocketfly")), SoundCategory.NEUTRAL, 3, 1, false);
        }
    }

    public static void StopRocketSounds(ServerPlayerEntity sourceentity) {
        SoundCategory soundCategory = SoundCategory.NEUTRAL;
        SStopSoundPacket sstopsoundpacket = new SStopSoundPacket(new ResourceLocation("boss_tools:rocketfly"), soundCategory);

        if (sourceentity instanceof ServerPlayerEntity) {
            sourceentity.connection.sendPacket(sstopsoundpacket);
        }
    }
    
}
