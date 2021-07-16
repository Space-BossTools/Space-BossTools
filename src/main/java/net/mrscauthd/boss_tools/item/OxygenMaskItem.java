
package net.mrscauthd.boss_tools.item;

import net.minecraft.item.*;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;

@BossToolsModElements.ModElement.Tag
public class OxygenMaskItem extends BossToolsModElements.ModElement {
    @ObjectHolder("boss_tools:oxygen_mask")
    public static final Item block = null;
    public OxygenMaskItem(BossToolsModElements instance) {
        super(instance, 11);
    }

    @Override
    public void initElements() {
        elements.items.add(() -> new ItemCustom());
    }
    public static class ItemCustom extends Item {
        public ItemCustom() {
            super(new Item.Properties().group(BossToolsItemGroup.tab).maxDamage(1300).rarity(Rarity.COMMON));
            DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
            setRegistryName("oxygen_mask");
        }

        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            return repair.getItem() == IronplateItem.block;
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstack);
            ItemStack itemstack1 = playerIn.getItemStackFromSlot(equipmentslottype);
            if (itemstack1.isEmpty()) {
                playerIn.setItemStackToSlot(equipmentslottype, itemstack.copy());
                itemstack.setCount(0);
                return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
            } else {
                return ActionResult.resultFail(itemstack);
            }
        }

        @Nullable
        @Override
        public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
            return EquipmentSlotType.HEAD;
        }

        @Override
        public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
            return 1F;
        }
    }
}
