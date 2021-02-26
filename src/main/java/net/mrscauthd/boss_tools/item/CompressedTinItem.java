
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsMaterialsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

@BossToolsModElements.ModElement.Tag
public class CompressedTinItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:compressed_desh")
	public static final Item block = null;
	public CompressedTinItem(BossToolsModElements instance) {
		super(instance, 22);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(SpaceBosstoolsMaterialsItemGroup.tab).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("compressed_desh");
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
