package net.mrscauthd.boss_tools.mixin;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.events.Methodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemGravity {
    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/item/ItemEntity;tick()V")
    private void tick(CallbackInfo info) {
        ItemEntity w = (ItemEntity) ((Object) this);

        RegistryKey<World> dim = w.world.getDimensionKey();

        //Planets
        if (GravityCheckItem(w)) {
            if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))) {
                w.setMotion(w.getMotion().getX(), w.getMotion().getY() / 0.98 + 0.08 - 0.05, w.getMotion().getZ());
            }

            if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))) {
                w.setMotion(w.getMotion().getX(), w.getMotion().getY() / 0.98 + 0.08 - 0.06, w.getMotion().getZ());
            }

            if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))) {
                w.setMotion(w.getMotion().getX(), w.getMotion().getY() / 0.98 + 0.08 - 0.05, w.getMotion().getZ());
            }

            if (dim == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))) {
                w.setMotion(w.getMotion().getX(), w.getMotion().getY() / 0.98 + 0.08 - 0.06, w.getMotion().getZ());
            }

            //Orbits
            if (Methodes.isOrbitWorld(w.world)) {
                w.setMotion(w.getMotion().getX(), w.getMotion().getY() / 0.98 + 0.08 - 0.05, w.getMotion().getZ());
            }
        }

    }

    private static boolean GravityCheckItem(ItemEntity entity) {
        if (!entity.isInWater() && !entity.isInLava() && !entity.hasNoGravity()) {
            return true;
        }

        return false;
    }

}