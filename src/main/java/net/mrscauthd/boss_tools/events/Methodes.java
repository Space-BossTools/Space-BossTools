package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetVenusRainEvent;
import net.mrscauthd.boss_tools.item.RoverItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;

import java.util.ArrayList;
import java.util.List;

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

    public static boolean Nethrite_Space_Suit_Check(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.NETHERITE_OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.NETHERITE_SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.NETHERITE_SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.NETHERITE_SPACE_BOOTS.get());

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean Space_Suit_Check(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.SPACE_BOOTS.get());

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean Space_Suit_Check_Both(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.SPACE_BOOTS.get());

        Boolean item3_2 = checkArmor(entity, 3, ModInnet.NETHERITE_OXYGEN_MASK.get());
        Boolean item2_2 = checkArmor(entity, 2, ModInnet.NETHERITE_SPACE_SUIT.get());
        Boolean item1_2 = checkArmor(entity, 1, ModInnet.NETHERITE_SPACE_PANTS.get());
        Boolean item0_2 = checkArmor(entity, 0, ModInnet.NETHERITE_SPACE_BOOTS.get());

        Boolean check3 = false;
        Boolean check2 = false;
        Boolean check1 = false;
        Boolean check0 = false;

        if (item3 || item3_2) {
            check3 = true;
        }
        if (item2 || item2_2) {
            check2 = true;
        }
        if (item1 || item1_2) {
            check1 = true;
        }
        if (item0 || item0_2) {
            check0 = true;
        }

        if (check0 && check1 && check2 && check3) {
            return true;
        }

        return false;
    }

    public static boolean checkArmor(LivingEntity entity,int number, Item item) {
        if (entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, number)).getItem() == item) {
            return true;
        }
        return false;
    }

    public static boolean isSpaceWorld(World world) {
        if (Methodes.isWorld(world, new ResourceLocation("boss_tools:moon"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:moon_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mars"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mars_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mercury"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mercury_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:venus"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:venus_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:overworld_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean isOrbitWorld(World world) {
        if (Methodes.isWorld(world, new ResourceLocation("boss_tools:overworld_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:moon_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mars_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:mercury_orbit"))
                || Methodes.isWorld(world, new ResourceLocation("boss_tools:venus_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean isWorld(World world, ResourceLocation loc) {
        RegistryKey<World> world2 = world.getDimensionKey();
        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, loc)) {
            return true;
        }
        return false;
    }

    public static void OxygenDamage(LivingEntity entity) {
        entity.attackEntityFrom(ModInnet.DAMAGE_SOURCE_OXYGEN, 1.0F);
    }

    public static boolean isInRocket(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity || entity instanceof RocketTier3Entity) {
            return true;
        }
        return false;
    }

    public static boolean AllVehiclesOr(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity || entity instanceof RocketTier3Entity || entity instanceof LanderEntity.CustomEntity || entity instanceof RoverEntity.CustomEntity) {
            return true;
        }
        return false;
    }

    public static void RocketSounds(Entity entity, World world) {
        world.playMovingSound(null, entity, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boss_tools:rocket_fly")), SoundCategory.NEUTRAL,1,1);
    }

    public static void DropRocket(PlayerEntity player) {
        Item item1 = player.getHeldItemMainhand().getItem();
        Item item2 = player.getHeldItemOffhand().getItem();

        List<Item> items = new ArrayList<Item>();

        items.add(Tier1RocketItemItem.block);
        items.add(Tier2RocketItemItem.block);
        items.add(Tier3RocketItemItem.block);
        items.add(RoverItemItem.block);

        if (items.contains(item1) && items.contains(item2)) {

            ItemEntity spawn = new ItemEntity(player.world, player.getPosX(),player.getPosY(),player.getPosZ(), new ItemStack(item2));
            spawn.setPickupDelay(0);
            player.world.addEntity(spawn);

            player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capability -> {
                capability.extractItem(40, 1, false); //40 is offhand
            });

        }

    }

    /**If a entity should not get Fire add it to the Tag "venus_fire"*/
    public static void VenusFire(LivingEntity entity, ResourceLocation planet1, ResourceLocation planet2) {

        RegistryKey<World> key = entity.world.getDimensionKey();

        if (key == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet1) || key == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet2)) {
            if (!Methodes.Nethrite_Space_Suit_Check(entity)) {
                if (!MinecraftForge.EVENT_BUS.post(new LivingSetFireInHotPlanetEvent(entity))) {
                    if (!tagCheck(entity, "boss_tools:entities/venus_fire")) {

                        entity.setFire(10);
                    }
                }
            }
        }
    }

    /**If a entity should not get Damage add it to the Tag "venus_rain", and if you has a Entity like a car return the damage to false*/
    public static void VenusRain(LivingEntity entity, ResourceLocation planet) {
        if (entity.world.getDimensionKey() == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet)) {
            if (entity.world.getWorldInfo().isRaining() && entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(entity.getPosX()), (int) Math.floor(entity.getPosZ())) <= Math.floor(entity.getPosY()) + 1) {
                if (!tagCheck(entity,"boss_tools:entities/venus_rain")) {
                    if (!MinecraftForge.EVENT_BUS.post(new LivingSetVenusRainEvent(entity))) {

                        entity.attackEntityFrom(ModInnet.DAMAGE_SOURCE_ACID_RAIN, 1);
                    }
                }
            }
        }

    }

    /**IF a entity should get oxygen damage add it in the tag "oxygen" (don't add the Player, he have a own oxygen system)*/
    public static void EntityOxygen(LivingEntity entity, World world) {
        if (Config.EntityOxygenSystem && Methodes.isSpaceWorld(world) && tagCheck(entity,"boss_tools:entities/oxygen")) {

            if (!entity.isPotionActive(ModInnet.OXYGEN_EFFECT.get())) {

                entity.getPersistentData().putDouble("boss_tools:oxygen_tick", entity.getPersistentData().getDouble("boss_tools:oxygen_tick") + 1);

                if (entity.getPersistentData().getDouble("boss_tools:oxygen_tick") > 15) {

                    if(!world.isRemote) {
                        Methodes.OxygenDamage(entity);
                    }

                    entity.getPersistentData().putDouble("boss_tools:oxygen_tick", 0);
                }
            }
        }
    }

    public static void vehicleRotation(LivingEntity vehicle, float roation) {
        vehicle.rotationYaw = vehicle.rotationYaw + roation;
        vehicle.setRenderYawOffset(vehicle.rotationYaw);
        vehicle.prevRotationYaw = vehicle.rotationYaw;
        vehicle.prevRenderYawOffset = vehicle.rotationYaw;
    }

    public static void noFuelMessage(PlayerEntity player) {
        if (!player.world.isRemote()) {
            player.sendStatusMessage(new StringTextComponent("\u00A7cNO FUEL! \u00A77Fill the Rocket with \u00A7cFuel\u00A77. (\u00A76Sneak and Right Click\u00A77)"), false);
        }
    }

    public static boolean tagCheck(Entity entity, String tag) {
        if (EntityTypeTags.getCollection().getTagByID(new ResourceLocation((tag).toLowerCase(java.util.Locale.ENGLISH))).contains(entity.getType())) {
            return true;
        }
        return false;
    }

}
