package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

public class BarrelItem extends Item {
	public BarrelItem() {
		super(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(8).rarity(Rarity.COMMON));
	}
		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
	}
}
