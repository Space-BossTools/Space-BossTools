package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;

public class OxygenSystem {

    public static void OxygenSystem(LivingEntity entity) {
        World world = entity.getEntityWorld();
        if (Config.PlayerOxygenSystem && Methodes.isSpaceWorld(world)) {

            if (entity.getAir() < 1) {
                Methodes.OxygenDamage(entity);
            }

            if (entity instanceof PlayerEntity) {

                if (Methodes.Space_Suit_Check_Both(entity)) {

                    ItemStack itemstack = entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 2));
                    IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

                    if (oxygenStorage.getOxygenStored() == 0) {
                        entity.setAir(-4);
                    }

                    if (oxygenStorage.getOxygenStored() > 0 || entity.isPotionActive(ModInnet.OXYGEN_EFFECT.get())) {
                        entity.setAir(300);
                    }

                }

                if (!Methodes.Space_Suit_Check_Both(entity)) {
                    entity.setAir(-4);
                }
            }

        }
    }
}
