package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSpaceGravityEvent;

public class Gravity {
	public static void Gravity(LivingEntity entity, GravityType type, World world) {
		if (!GravityCheck(entity, type)) {
			return;
		}
		else if (!Methodes.isSpaceWorld(world)) {
			return;
		}
		
		RegistryKey<World> dim = world.getDimensionKey();
		double divisor = 0.98D;
		double offset = 0.08D;
		double fallDistanceMultiplier = 1.0D;

		// Planets
		if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))) {
			offset -= 0.03D;
			fallDistanceMultiplier = 2.5F;
		}
		else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))) {
			offset -= 0.04D;
			fallDistanceMultiplier = 2.0D;
		}
		else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))) {
			offset -= 0.03D;
			fallDistanceMultiplier = 2.5D;
		}
		else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))) {
			offset -= 0.04D;
			fallDistanceMultiplier = 2.0D;
		}
		// Orbits
		else if (Methodes.isOrbitWorld(world)) {
			offset -= 0.02D;
			fallDistanceMultiplier = 2.5D;
		}

		LivingSpaceGravityEvent e = new LivingSpaceGravityEvent(entity, divisor, offset, fallDistanceMultiplier);

		if (!MinecraftForge.EVENT_BUS.post(e)) {
			Vector3d motion = entity.getMotion();
			double motionY = (motion.getY() / e.getDivisor()) + e.getOffset();
			entity.setMotion(motion.getX(), motionY, motion.getZ());

			if (motionY < -0.1) {
				entity.fallDistance = -(float) (motionY * e.getFallDistanceMultiplier());
			}
		}
	}

	public enum GravityType {
		PLAYER,LIVING
	}

	/** Check Entity with type */
	public static boolean GravityCheck(LivingEntity entity, GravityType type) {
		if (type == GravityType.PLAYER && GravityCheck((PlayerEntity) entity)) {
			return true;
		}
		else if (type == GravityType.LIVING && GravityCheckLiving(entity)) {
			return true;
		}
		
		return false;
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