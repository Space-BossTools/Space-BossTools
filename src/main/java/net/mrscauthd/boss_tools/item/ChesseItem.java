
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.Food;

@BossToolsModElements.ModElement.Tag
public class ChesseItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:chesse")
	public static final Item block = null;
	public ChesseItem(BossToolsModElements instance) {
		super(instance, 9);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new FoodItemCustom());
	}
	public static class FoodItemCustom extends Item {
		public FoodItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroup.tab).maxStackSize(64).rarity(Rarity.COMMON)
					.food((new Food.Builder()).hunger(4).saturation(3f).build()));
			setRegistryName("chesse");
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.EAT;
		}
	}
}
