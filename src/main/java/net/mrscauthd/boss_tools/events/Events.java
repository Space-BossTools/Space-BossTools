package net.mrscauthd.boss_tools.events;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.*;

@Mod.EventBusSubscriber(modid = "boss_tools")
public class Events {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerEntity player = event.player;
            World world = player.world;

            //Lander Teleport System
            if (player.getRidingEntity() instanceof LanderEntity) {
                Methodes.landerTeleportOrbit(player, world);
            }

            //Planet Gui Open
            Methodes.openPlanetGui(player);

            //Oxygen System
            OxygenSystem.OxygenSystem(player);

            //Gravity Methode Call
            Gravity.Gravity(player, Gravity.GravityType.PLAYER, world);

            //Drop Off Hand Item
            Methodes.DropRocket(player);

            //Player orbit Fall Teleport
            if (player.getPosY() < 1 && !(player.getRidingEntity() instanceof LanderEntity)) {
                Methodes.playerFalltoPlanet(world, player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;

        Methodes.EntityOxygen(entity,world);

        //Gravity Methode Call
        Gravity.Gravity(entity, Gravity.GravityType.LIVING, world);

        //Venus Rain
        Methodes.VenusRain(entity, new ResourceLocation("boss_tools:venus"));

        //Venus Fire
        Methodes.VenusFire(entity, new ResourceLocation("boss_tools:venus"), new ResourceLocation("boss_tools:mercury"));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
        Entity ridding = event.getInfo().getRenderViewEntity().getRidingEntity();

        if (Methodes.isRocket(ridding) || ridding instanceof LanderEntity) {
            PointOfView pointOfView = Minecraft.getInstance().gameSettings.getPointOfView();

            if (pointOfView.equals(PointOfView.THIRD_PERSON_FRONT) || pointOfView.equals(PointOfView.THIRD_PERSON_BACK)) {
                event.getInfo().movePosition(-event.getInfo().calcCameraDistance(9d), 0d, 0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().getRidingEntity() instanceof LanderEntity) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupPlayerAngles(PlayerModelEvent.SetupAngles.Post event) {
        PlayerEntity player = event.getPlayer();
        PlayerModel model = event.getModelPlayer();
        //Player Rocket Sit Rotations
        if (Methodes.isRocket(player.getRidingEntity())) {
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(3F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(3F);
            // Arms
            model.bipedLeftArm.rotateAngleX = -0.07f;
            model.bipedRightArm.rotateAngleX = -0.07f;
        }

        //Player Hold Vehicles Rotation
        if (!Methodes.isRocket(player.getRidingEntity())) {
            Item item1 = player.getHeldItemMainhand().getItem();
            Item item2 = player.getHeldItemOffhand().getItem();
            if (item1 == ModInnet.TIER_1_ROCKET_ITEM.get()
                    || item1 == ModInnet.TIER_2_ROCKET_ITEM.get()
                    || item1 == ModInnet.TIER_3_ROCKET_ITEM.get()
                    || item1 == ModInnet.ROVER_ITEM.get()
                    //Off Hand
                    || item2 == ModInnet.TIER_1_ROCKET_ITEM.get()
                    || item2 == ModInnet.TIER_2_ROCKET_ITEM.get()
                    || item2 == ModInnet.TIER_3_ROCKET_ITEM.get()
                    || item2 == ModInnet.ROVER_ITEM.get()) {
                model.bipedRightArm.rotateAngleX = 10;
                model.bipedLeftArm.rotateAngleX = 10;
                model.bipedLeftArm.rotateAngleZ = 0;
                model.bipedRightArm.rotateAngleZ = 0;
                model.bipedRightArm.rotateAngleY =  0;
                model.bipedLeftArm.rotateAngleY = 0;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void ItemRender(RenderItemEvent.Held event) {
        Entity player = event.getEntity();
        if (Methodes.isRocket(player.getRidingEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            World world = event.world;
            RegistryKey<World> world2 = world.getDimensionKey();
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon_orbit"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars_orbit"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury_orbit"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus_orbit"))
             || world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:overworld_orbit"))) {
                world.thunderingStrength = 0;
                world.rainingStrength = 0;
            }
            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))) {
                world.thunderingStrength = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) event.getEntity();

            if (Methodes.nethriteSpaceSuitCheck(entity)) {
                if (event.getSource().isFireDamage()) {
                    entity.forceFireTicks(0);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void FishingBobberTick(ProjectileImpactEvent.FishingBobber event) {
        if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
            if (Methodes.AllVehiclesOr(entity)) {
                event.setCanceled(true);
            }

        }

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void SpaceSounds(PlaySoundEvent event) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.world != null && Minecraft.getInstance().currentScreen == null && Methodes.isSpaceWorld(Minecraft.getInstance().player.world)) {
            event.setResultSound(new SpaceSoundSystem(event.getSound()));
        }

    }
}
