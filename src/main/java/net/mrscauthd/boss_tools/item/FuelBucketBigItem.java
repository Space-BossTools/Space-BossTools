
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

@BossToolsModElements.ModElement.Tag
public class FuelBucketBigItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:barrel_of_fuel")
	public static final Item block = null;
	public FuelBucketBigItem(BossToolsModElements instance) {
		super(instance, 8);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroup.tab).maxStackSize(1).rarity(Rarity.COMMON));
			setRegistryName("barrel_of_fuel");
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
}
