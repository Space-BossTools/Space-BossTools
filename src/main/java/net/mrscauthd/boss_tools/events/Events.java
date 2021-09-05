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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.item.RoverItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;


@Mod.EventBusSubscriber
public class Events {
    public static double counter = 1;
    public static boolean check = false;
    //Player Tick
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerEntity entity = event.player;
            IWorld world = entity.world;
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            //Gravity Methode Call
            Gravity.Gravity(entity, "player", (World) world);

            //Drop Off Hand Item
            Methodes.DropRocket(entity);

            //Player orbit Fall Teleport
            RegistryKey<World> world2 = ((World) world).getDimensionKey();

            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:overworld_orbit"))) {
                ResourceLocation planet = new ResourceLocation("overworld");
                Methodes.PlayerFallToPlanet(entity, planet);
            }

            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon_orbit"))) {
                ResourceLocation planet = new ResourceLocation("boss_tools:moon");
                Methodes.PlayerFallToPlanet(entity, planet);
            }

            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars_orbit"))) {
                ResourceLocation planet = new ResourceLocation("boss_tools:mars");
                Methodes.PlayerFallToPlanet(entity, planet);
            }

            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury_orbit"))) {
                ResourceLocation planet = new ResourceLocation("boss_tools:mercury");
                Methodes.PlayerFallToPlanet(entity, planet);
            }

            if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus_orbit"))) {
                ResourceLocation planet = new ResourceLocation("boss_tools:venus");
                Methodes.PlayerFallToPlanet(entity, planet);
            }

            //Lander Warning Overlay Tick
                if (check == false) {
                    counter = counter - 0.025;
                    if (counter <= 0.2) {
                        check = true;
                    }
                }
                if (check == true) {
                    counter = counter + 0.025;
                    if (counter >= 1.2) {
                        check = false;
                    }
                }
            //Other Player Ticks
        }
    }

    //OnEntityTick Event
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;

        //Entity Oxygen //Todo rework this with the New Oxygen System
        Methodes.EntityOxygen(entity,world);

        //Gravity Methode Call
        Gravity.Gravity(entity, "living", world);

        //Venus Rain
        Methodes.VenusRain(entity, new ResourceLocation("boss_tools:venus"));

    }

    //Camera Event
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
        if (event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketTier1Entity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketTier2Entity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketTier3Entity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof LanderEntity.CustomEntity) {
            if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_FRONT)) {
                event.getInfo().movePosition(-event.getInfo().calcCameraDistance(8d), 0d, 0);
            }
            if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_BACK)) {
                event.getInfo().movePosition(-event.getInfo().calcCameraDistance(8d), 0d, 0);
            }
        }
    }

    //Render Player Event
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().getRidingEntity() instanceof LanderEntity.CustomEntity) {
            event.setCanceled(true);
        }
    }

    //Obfuscate Rotation Event
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupPlayerAngles(PlayerModelEvent.SetupAngles.Post event) {
        PlayerEntity player = event.getPlayer();
        PlayerModel model = event.getModelPlayer();
        //Player Rocket Sit Rotations
        if (Methodes.RocketCheckOr(player.getRidingEntity()) == true) {
            model.bipedRightLeg.rotationPointY = (float) Math.toRadians(485F);
            model.bipedLeftLeg.rotationPointY = (float) Math.toRadians(485F);
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(3F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(3F);
            // Arms
            model.bipedRightArm.rotationPointX = (float) Math.toRadians(-250F);// -200
            model.bipedLeftArm.rotationPointX = (float) Math.toRadians(250F);
            model.bipedLeftArm.rotateAngleX = -0.07f;
            model.bipedRightArm.rotateAngleX = -0.07f;
        }

        //Player Hold Vehicles Rotation
        if (Methodes.RocketCheckOr(player.getRidingEntity()) == false) {
            Item item1 = player.getHeldItemMainhand().getItem();
            Item item2 = player.getHeldItemOffhand().getItem();
            if (item1 == Tier1RocketItemItem.block
                    || item1 == Tier2RocketItemItem.block
                    || item1 == Tier3RocketItemItem.block
                    || item1 == RoverItemItem.block
                    //Off Hand
                    || item2 == Tier1RocketItemItem.block
                    || item2 == Tier2RocketItemItem.block
                    || item2 == Tier3RocketItemItem.block
                    || item2 == RoverItemItem.block) {
                model.bipedRightArm.rotateAngleX = 10;
                model.bipedLeftArm.rotateAngleX = 10;
                model.bipedLeftArm.rotateAngleZ = 0;
                model.bipedRightArm.rotateAngleZ = 0;
                model.bipedRightArm.rotateAngleY =  0;
                model.bipedLeftArm.rotateAngleY = 0;
            }
        }
    }

    //Obfuscate Item Render Event
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void ItemRender(RenderItemEvent.Held event) {
        Entity player = event.getEntity();
        if (Methodes.RocketCheckOr(player.getRidingEntity()) == true) {
            event.setCanceled(true);
        }
    }

    //TODO:Change Dimension Id in Teleport Code
    //World Tick Event
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

            Boolean armorCheck = Methodes.Nethrite_Space_Suit_Check(entity);

            if (armorCheck == true) {
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
            if (Methodes.AllVehiclesOr(entity) == true) {
                event.setCanceled(true);
            }
        }
    }
    //Other Events
}
