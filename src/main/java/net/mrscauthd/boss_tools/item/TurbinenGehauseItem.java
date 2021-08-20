
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

@BossToolsModElements.ModElement.Tag
public class TurbinenGehauseItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:engine_casing")
	public static final Item block = null;
	public TurbinenGehauseItem(BossToolsModElements instance) {
		super(instance, 33);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroups.tab_basics).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("engine_casing");
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
