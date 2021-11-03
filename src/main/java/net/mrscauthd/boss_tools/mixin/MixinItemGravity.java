package net.mrscauthd.boss_tools.mixin;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.forgeevents.ItemSpaceGravityEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemGravity {
    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/item/ItemEntity;tick()V")
    private void tick(CallbackInfo info) {
        ItemEntity w = (ItemEntity) ((Object) this);
        
        if (!GravityCheckItem(w)) {
        	return;
        }
		else if (!Methodes.isSpaceWorld(w.world)) {
			return;
		}

        RegistryKey<World> dim = w.world.getDimensionKey();
		double divisor = 0.98D;
		double offset = 0.08D;

        //Planets
		if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))) {
			offset -= 0.05D;
        }
        else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))) {
			offset -= 0.06D;
        }
        else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))) {
			offset -= 0.05D;
        }
        else if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))) {
			offset -= 0.06D;
        }
        //Orbits
        else if (Methodes.isOrbitWorld(w.world)) {
			offset -= 0.05D;
        }

		ItemSpaceGravityEvent e = new ItemSpaceGravityEvent(w, divisor, offset);

		if (!MinecraftForge.EVENT_BUS.post(e)) {
	        Vector3d motion = w.getMotion();
			double motionY = (motion.getY() / e.getDivisor()) + e.getOffset();
			w.setMotion(motion.getX(), motionY, motion.getZ());
		}
    }

    private static boolean GravityCheckItem(ItemEntity entity) {
        if (!entity.isInWater() && !entity.isInLava() && !entity.hasNoGravity()) {
            return true;
        }

        return false;
    }

}