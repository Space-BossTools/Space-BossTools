
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsBasicsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

@BossToolsModElements.ModElement.Tag
public class OxygenTankItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:oxygen_tank")
	public static final Item block = null;
	public OxygenTankItem(BossToolsModElements instance) {
		super(instance, 30);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(SpaceBosstoolsBasicsItemGroup.tab).maxStackSize(1).rarity(Rarity.COMMON));
			setRegistryName("oxygen_tank");
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
