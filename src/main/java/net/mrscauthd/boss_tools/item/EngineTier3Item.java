
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsBasicsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

@BossToolsModElements.ModElement.Tag
public class EngineTier3Item extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:diamond_engine")
	public static final Item block = null;
	public EngineTier3Item(BossToolsModElements instance) {
		super(instance, 41);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(SpaceBosstoolsBasicsItemGroup.tab).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("diamond_engine");
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
