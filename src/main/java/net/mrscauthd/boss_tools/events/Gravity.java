package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Gravity {
    public static void Gravity(LivingEntity entity, String type, World world) {
        RegistryKey<World> dim = world.getDimensionKey();

        double moon = entity.getMotion().getY() / 0.98 + 0.08 - 0.03;
        double mars = entity.getMotion().getY() / 0.98 + 0.08 - 0.04;
        double mercury = entity.getMotion().getY() / 0.98 + 0.08 - 0.03;
        double venus = entity.getMotion().getY() / 0.98 + 0.08 - 0.04;
        double orbit = entity.getMotion().getY() / 0.98 + 0.08 - 0.02;

        //Planets
        if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))) {

            if (type == "player" && GravityCheck((PlayerEntity) entity) == true) {
                entity.setMotion(entity.getMotion().getX(), moon, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }

            if (type == "living" && GravityCheckLiving(entity))  {
                entity.setMotion(entity.getMotion().getX(), moon, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }

        }

        if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))) {
            if (type == "player" && GravityCheck((PlayerEntity) entity) == true) {
                entity.setMotion(entity.getMotion().getX(), mars, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2f;
                }
            }

            if (type == "living" && GravityCheckLiving(entity))  {
                entity.setMotion(entity.getMotion().getX(), mars, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2f;
                }
            }

        }

        if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))) {
            if (type == "player" && GravityCheck((PlayerEntity) entity) == true) {
                entity.setMotion(entity.getMotion().getX(), mercury, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }

            if (type == "living" && GravityCheckLiving(entity))  {
                entity.setMotion(entity.getMotion().getX(), mercury, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }

        }

        if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))) {
            if (type == "player" && GravityCheck((PlayerEntity) entity) == true) {
                entity.setMotion(entity.getMotion().getX(), venus, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2f;
                }
            }

            if (type == "living" && GravityCheckLiving(entity))  {
                entity.setMotion(entity.getMotion().getX(), venus, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2f;
                }
            }

        }

        //Orbits
        if (Methodes.isOrbitWorld(world)) {
            if (type == "player" && GravityCheck((PlayerEntity) entity) == true) {
                entity.setMotion(entity.getMotion().getX(), orbit, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }

            if (type == "living" && GravityCheckLiving(entity))  {
                entity.setMotion(entity.getMotion().getX(), orbit, entity.getMotion().getZ());

                if (entity.getMotion().getY() < -0.1) {
                    entity.fallDistance = (float) entity.getMotion().getY() * -2.5f;
                }
            }
        }
    }

    /**Player Entity (Example: Player)*/
    public static boolean GravityCheck(PlayerEntity player) {
        if (!player.abilities.isFlying && !player.isElytraFlying() && !player.isInWater() && !player.isInLava() && !player.hasNoGravity()) {
            return true;
        }

        return false;
    }

    /**Living Entity (Example: Sheep,Pig,Cow...)*/
    public static boolean GravityCheckLiving(LivingEntity entity) {
        if (!entity.isElytraFlying() && !entity.isInWater() && !entity.isInLava() && !entity.hasNoGravity() && !(entity instanceof PlayerEntity) && !Methodes.AllVehiclesOr(entity)) {
            return true;
        }

        return false;
    }

}